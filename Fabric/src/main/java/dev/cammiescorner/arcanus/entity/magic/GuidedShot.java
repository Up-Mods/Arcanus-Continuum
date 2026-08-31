package dev.cammiescorner.arcanus.entity.magic;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanus.util.ArcanusHelper;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

// TODO give hit sound
public class GuidedShot extends ThrowableItemProjectile implements Targetable {
	private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(GuidedShot.class, EntityDataSerializers.INT);
	private List<SpellEffect> effects = new ArrayList<>();
	private List<SpellGroup> spellGroups = new ArrayList<>();
	private ItemStack stack = ItemStack.EMPTY;
	private UUID casterId = Util.NIL_UUID;
	private int groupIndex;
	private double potency;

	public GuidedShot(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
		super(entityType, level);
		setNoGravity(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(OWNER_ID, -1);
	}

	@Override
	public void tick() {
		if(getCaster() == null && level() instanceof ServerLevel serverLevel) {
			kill(serverLevel);
			return;
		}

		HitResult result = ArcanusHelper.raycast(getCaster(), distanceTo(getCaster()) * 1.5, true, false);
		Vec3 targetPos = result.getLocation();

		// TODO make it turn smoothly rather than instantly, and add config values for turning speed and projectile speed
		setDeltaMovement(targetPos.subtract(position()).normalize().scale(0.5));
		super.tick();
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if(level() instanceof ServerLevel serverLevel) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(getCaster(), this, level(), result, effects, stack, potency);

			SpellShape.castNext(getCaster(), position(), result.getEntity(), (ServerLevel) level(), stack, spellGroups, groupIndex, potency);

			kill(serverLevel);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		if(level() instanceof ServerLevel serverLevel) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(getCaster(), this, level(), result, effects, stack, potency);

			SpellShape.castNext(getCaster(), position(), this, (ServerLevel) level(), stack, spellGroups, groupIndex, potency);

			super.onHitBlock(result);
			kill(serverLevel);
		}
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		effects.clear();
		spellGroups.clear();

		casterId = input.read("CasterId", UUIDUtil.CODEC).orElse(Util.NIL_UUID);
		stack = input.read("ItemStack", ItemStack.CODEC).orElse(ItemStack.EMPTY);
		groupIndex = input.getIntOr("GroupIndex", 0);
		potency = input.getDoubleOr("Potency", 0);

		for(String s : input.read("Effects", Codec.STRING.listOf()).orElse(List.of())) {
			if(ArcanusSpellComponents.REGISTRY.getValue(Identifier.parse(s)) instanceof SpellEffect spellEffect)
				effects.add(spellEffect);
		}

		spellGroups.addAll(input.read("SpellGroups", SpellGroup.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		List<String> stringEffects = new ArrayList<>();

		output.store("CasterId", UUIDUtil.CODEC, casterId);
		output.store("ItemStack", ItemStack.CODEC, stack);
		output.putInt("GroupIndex", groupIndex);
		output.putDouble("Potency", potency);

		for(SpellEffect effect : effects)
			stringEffects.add(ArcanusSpellComponents.REGISTRY.getKey(effect).toString());

		output.store("Effects", Codec.STRING.listOf(), stringEffects);
		output.store("SpellGroups", SpellGroup.CODEC.listOf(), spellGroups);
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected Item getDefaultItem() {
		return Items.AIR;
	}

	public LivingEntity getCaster() {
		if(level() instanceof ServerLevel serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;
		else if(level().isClientSide() && level().getEntity(entityData.get(OWNER_ID)) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public void setProperties(@Nullable LivingEntity caster, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> groups, int groupIndex, double potency) {
		this.effects.clear();
		this.spellGroups.clear();
		this.effects.addAll(effects);
		this.spellGroups.addAll(groups);

		if(caster != null) {
			this.casterId = caster.getUUID();
			this.entityData.set(OWNER_ID, caster.getId());
			ArcanusHelper.copyMagicColor(this, caster);
		}

		this.stack = stack;
		this.groupIndex = groupIndex;
		this.potency = potency;
	}
}
