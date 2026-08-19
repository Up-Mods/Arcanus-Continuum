package dev.cammiescorner.arcanus.common.entity.magic;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class Beam extends Entity implements Targetable {
	private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(Beam.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> MAX_AGE = SynchedEntityData.defineId(Beam.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_ON_ENTITY = SynchedEntityData.defineId(Beam.class, EntityDataSerializers.BOOLEAN);
	private final List<SpellEffect> effects = new ArrayList<>();
	private final List<SpellGroup> groups = new ArrayList<>();
	private UUID casterId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private int groupIndex = 0;
	private double potency = 1f;

	public Beam(EntityType<?> variant, Level world) {
		super(variant, world);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(OWNER_ID, -1);
		builder.define(MAX_AGE, 40);
		builder.define(IS_ON_ENTITY, false);
	}

	@Override
	public void tick() {
		if(tickCount >= entityData.get(MAX_AGE) || (getCaster() == null || distanceToSqr(getCaster()) > 273 || !getCaster().isAlive()) || (entityData.get(IS_ON_ENTITY) ? getVehicle() == null : level().isEmptyBlock(blockPosition()))) {
			if(level() instanceof ServerLevel serverLevel)
				kill(serverLevel);

			return;
		}

		if(!level().isClientSide() && entityData.get(OWNER_ID) == -1 && getCaster() != null)
			entityData.set(OWNER_ID, getCaster().getId());

		super.tick();
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		return false;
	}

	@Override
	public void kill(final ServerLevel serverLevel) {
		if(!level().isClientSide() && getCaster() != null) {
			if(distanceToSqr(getCaster()) <= 273) {
				HitResult target = entityData.get(IS_ON_ENTITY) && getVehicle() != null ? new EntityHitResult(getVehicle()) : new BlockHitResult(position(), Direction.UP, blockPosition(), true);

				for(SpellEffect effect : new HashSet<>(effects))
					effect.effect(getCaster(), this, level(), target, effects, stack, potency + 0.15);

				if(target instanceof EntityHitResult entityHit)
					SpellShape.castNext(getCaster(), target.getLocation(), entityHit.getEntity(), (ServerLevel) level(), stack, groups, groupIndex, potency);
				else
					SpellShape.castNext(getCaster(), target.getLocation(), this, (ServerLevel) level(), stack, groups, groupIndex, potency);
			}
		}

		super.kill(serverLevel);
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		effects.clear();
		groups.clear();

		entityData.set(OWNER_ID, input.getIntOr("OwnerId", 0));
		entityData.set(MAX_AGE, input.getIntOr("MaxAge", 0));
		entityData.set(IS_ON_ENTITY, input.getBooleanOr("IsOnBoolean", false));
		casterId = input.read("CasterId", UUIDUtil.CODEC).orElse(Util.NIL_UUID);
		stack = input.read("ItemStack", ItemStack.CODEC).orElse(ItemStack.EMPTY);
		groupIndex = input.getIntOr("GroupIndex", 0);
		potency = input.getDoubleOr("Potency", 0);

		for(String s : input.read("Effects", Codec.STRING.listOf()).orElse(List.of())) {
			if(ArcanusSpellComponents.REGISTRY.getValue(Identifier.parse(s)) instanceof SpellEffect spellEffect)
				effects.add(spellEffect);
		}

		groups.addAll(input.read("SpellGroups", SpellGroup.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		List<String> stringEffects = new ArrayList<>();

		output.putInt("OwnerId", entityData.get(OWNER_ID));
		output.putInt("MaxAge", entityData.get(MAX_AGE));
		output.putBoolean("IsOnBoolean", entityData.get(IS_ON_ENTITY));
		output.store("CasterId", UUIDUtil.CODEC, casterId);
		output.store("ItemStack", ItemStack.CODEC, stack);
		output.putInt("GroupIndex", groupIndex);
		output.putDouble("Potency", potency);

		for(SpellEffect effect : effects)
			stringEffects.add(ArcanusSpellComponents.REGISTRY.getKey(effect).toString());

		output.store("Effects", Codec.STRING.listOf(), stringEffects);
		output.store("SpellGroups", SpellGroup.CODEC.listOf(), groups);
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double sqDistance) {
		return sqDistance <= 64 * 64;
	}

	public Vec3 getBeamPos(float tickDelta) {
		return getVehicle() != null ? getVehicle().getPosition(tickDelta).add(0, getVehicle().getBbHeight() / 2, 0) : getPosition(tickDelta);
	}

	public float getBeamProgress(float tickDelta) {
		return (tickCount + tickDelta) / (float) entityData.get(MAX_AGE);
	}

	public LivingEntity getCaster() {
		if(level() instanceof ServerLevel serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;
		else if(level().isClientSide() && level().getEntity(entityData.get(OWNER_ID)) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public void setProperties(@Nullable LivingEntity caster, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> groups, int groupIndex, int maxAge, double potency, boolean isOnEntity) {
		this.effects.clear();
		this.groups.clear();
		this.effects.addAll(effects);
		this.groups.addAll(groups);

		if(caster != null) {
			casterId = caster.getUUID();
			entityData.set(OWNER_ID, caster.getId());
			ArcanusHelper.copyMagicColor(this, caster);
		}

		this.stack = stack;
		this.groupIndex = groupIndex;
		entityData.set(MAX_AGE, maxAge);
		this.potency = potency;
		entityData.set(IS_ON_ENTITY, isOnEntity);
	}
}
