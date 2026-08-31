package dev.cammiescorner.arcanus.entity.magic;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanus.util.ArcanusHelper;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

// TODO give hit sound
public class StockpileOrb extends ThrowableProjectile implements Targetable {
	private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(StockpileOrb.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(StockpileOrb.class, EntityDataSerializers.INT);
	private final List<SpellEffect> effects = new ArrayList<>();
	private final List<SpellGroup> groups = new ArrayList<>();
	private UUID casterId = Util.NIL_UUID;
	private UUID targetId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private int groupIndex = 0;
	private double potency = 1f;
	private boolean boundToTarget = true;

	public StockpileOrb(EntityType<? extends ThrowableProjectile> variant, Level world) {
		super(variant, world);
		noPhysics = true;
		setNoGravity(true);
	}

	@Override
	public boolean canFreeze() {
		return super.canFreeze();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(OWNER_ID, -1);
		builder.define(TARGET_ID, -1);
	}

	@Override
	public void tick() {
		if(level() instanceof ServerLevel serverLevel && (getCaster() == null || getTarget() == null)) {
			kill(serverLevel);
			return;
		}

		if(!level().isClientSide()) {
			if(entityData.get(TARGET_ID) == -1 && getTarget() != null)
				entityData.set(TARGET_ID, getTarget().getId());
		}

		if(isBoundToTarget()) {
			int orbCount = ArcanusComponents.stockpileOrbCount(getTarget());
			int orbIndex = ArcanusComponents.stockpileOrbIndex(getTarget(), this) + 1;
			double angle = Math.toRadians(360d / orbCount * orbIndex);
			double cosYaw = Math.cos(Math.toRadians(-getTarget().yBodyRot));
			double sinYaw = Math.sin(Math.toRadians(-getTarget().yBodyRot));
			double radius = getTarget().getBbHeight() / 1.5;
			double rotXZ = Math.sin(level().getGameTime() * 0.1 + angle) * radius;
			double rotY = Math.cos(level().getGameTime() * 0.1 + angle) * radius;
			Vec3 bodyYaw = new Vec3(sinYaw, 1, cosYaw);
			Vec3 offset = new Vec3(sinYaw, 0, cosYaw).scale(-0.75);
			Vec3 imInSpainWithoutTheA = bodyYaw.multiply(rotXZ, rotY, rotXZ).yRot((float) Math.toRadians(90));
			Vec3 targetPos = getTarget().position().add(0, radius, 0).add(imInSpainWithoutTheA).add(offset);
			Vec3 direction = targetPos.subtract(position());
			move(MoverType.SELF, direction);
		}
		else {
			noPhysics = false;
			setNoGravity(false);
		}

		super.tick();
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if(!isBoundToTarget() && level() instanceof ServerLevel serverLevel) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(getCaster(), this, level(), entityHitResult, effects, stack, potency);

			SpellShape.castNext(getCaster(), position(), entityHitResult.getEntity(), (ServerLevel) level(), stack, groups, groupIndex, potency);

			kill(serverLevel);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		if(!isBoundToTarget() && level() instanceof ServerLevel serverLevel) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(getCaster(), this, level(), blockHitResult, effects, stack, potency);

			SpellShape.castNext(getCaster(), position(), this, (ServerLevel) level(), stack, groups, groupIndex, potency);

			super.onHitBlock(blockHitResult);
			kill(serverLevel);
		}
	}

	@Override
	public boolean isAttackable() {
		return true;
	}

	@Override
	public float getPickRadius() {
		return isBoundToTarget() ? 0f : 0.75f;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		Vec3 dir = getTarget().getEyePosition().subtract(position()).normalize();
		float pitch = (float) Math.toDegrees(Math.asin(-dir.y()));
		float yaw = (float) Math.toDegrees(-Math.atan2(dir.x(), dir.z()));

		setBoundToTarget(false);
		shootFromRotation(getTarget(), pitch, yaw, 0f, 3f, 1f);
		ArcanusComponents.removeStockpileOrbFromEntity(getTarget(), getUUID());

		return true;
	}

	@Override
	public void kill(final ServerLevel serverLevel) {
		if(getTarget() != null)
			ArcanusComponents.removeStockpileOrbFromEntity(getTarget(), getUUID());

		super.kill(serverLevel);
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		effects.clear();
		groups.clear();

		casterId = input.read("CasterId", UUIDUtil.CODEC).orElse(Util.NIL_UUID);
		stack = input.read("ItemStack", ItemStack.CODEC).orElse(ItemStack.EMPTY);
		groupIndex = input.getIntOr("GroupIndex", 0);
		potency = input.getDoubleOr("Potency", 0);
		boundToTarget = input.getBooleanOr("BoundToTarget", false);

		for(String s : input.read("Effects", Codec.STRING.listOf()).orElse(List.of())) {
			if(ArcanusSpellComponents.REGISTRY.getValue(Identifier.parse(s)) instanceof SpellEffect spellEffect)
				effects.add(spellEffect);
		}

		groups.addAll(input.read("SpellGroups", SpellGroup.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);

		List<String> stringEffects = new ArrayList<>();

		output.store("CasterId", UUIDUtil.CODEC, casterId);
		output.store("ItemStack", ItemStack.CODEC, stack);
		output.putInt("GroupIndex", groupIndex);
		output.putDouble("Potency", potency);
		output.putBoolean("BoundToTarget", boundToTarget);

		for(SpellEffect effect : effects)
			stringEffects.add(ArcanusSpellComponents.REGISTRY.getKey(effect).toString());

		output.store("Effects", Codec.STRING.listOf(), stringEffects);
		output.store("SpellGroups", SpellGroup.CODEC.listOf(), groups);
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double sqDistance) {
		return sqDistance <= 64 * 64;
	}

	public LivingEntity getCaster() {
		if(level() instanceof ServerLevel serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;
		else if(level().isClientSide() && level().getEntity(entityData.get(OWNER_ID)) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public LivingEntity getTarget() {
		if(level() instanceof ServerLevel serverWorld && serverWorld.getEntity(targetId) instanceof LivingEntity target)
			return target;
		else if(level().isClientSide() && level().getEntity(entityData.get(TARGET_ID)) instanceof LivingEntity target)
			return target;

		return null;
	}

	public boolean isBoundToTarget() {
		return boundToTarget;
	}

	public void setBoundToTarget(boolean boundToTarget) {
		this.boundToTarget = boundToTarget;
	}

	public void setProperties(@Nullable LivingEntity caster, LivingEntity target, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> groups, int groupIndex, double potency) {
		this.effects.clear();
		this.groups.clear();
		this.effects.addAll(effects);
		this.groups.addAll(groups);

		if(caster != null) {
			this.casterId = caster.getUUID();
			this.entityData.set(OWNER_ID, caster.getId());
			ArcanusHelper.copyMagicColor(this, caster);
		}

		ArcanusComponents.addStockpileOrbToEntity(target, getUUID());
		setBoundToTarget(true);

		this.targetId = target.getUUID();
		this.entityData.set(TARGET_ID, target.getId());
		this.stack = stack;
		this.groupIndex = groupIndex;
		this.potency = potency;
	}
}
