package dev.cammiescorner.arcanus.common.entity.magic;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.common.component.entity.PocketDimensionPortalComponent;
import dev.cammiescorner.arcanus.common.component.level.PocketDimensionComponent;
import dev.cammiescorner.arcanus.common.data.ArcanusEntityTags;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class PocketDimensionPortal extends Entity implements Targetable {
	private static final EntityDataAccessor<Integer> TRUE_AGE = SynchedEntityData.defineId(PocketDimensionPortal.class, EntityDataSerializers.INT);
	private UUID casterId = Util.NIL_UUID;
	private double pullStrength;

	public PocketDimensionPortal(EntityType<?> variant, Level world) {
		super(variant, world);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(TRUE_AGE, 0);
	}

	@Override
	public void tick() {
		var caster = getCaster();

		if(level() instanceof ServerLevel serverLevel) {
			if((caster == null || !caster.isAlive()) || getTrueAge() > ArcanusConfig.UtilityEffects.SpatialRiftEffectProperties.baseLifeSpan + 20) {
				kill(serverLevel);
				return;
			}

			if(getTrueAge() <= ArcanusConfig.UtilityEffects.SpatialRiftEffectProperties.baseLifeSpan) {
				AABB box = new AABB(0, 0, 0, 0, 0, 0).inflate(4 + pullStrength).move(position());
				double boxRadius = box.getXsize() / 2;
				double boxRadiusSq = boxRadius * boxRadius;

				if(caster instanceof ServerPlayer serverCaster) {
					if(getTrueAge() > ArcanusConfig.UtilityEffects.SpatialRiftEffectProperties.portalGrowTime) {
						Level destination = serverLevel.getServer().getLevel(level().dimension() != PocketDimensionPortalComponent.POCKET_DIMENSION_WORLD_KEY ? PocketDimensionPortalComponent.POCKET_DIMENSION_WORLD_KEY : level().dimension());

						level().getEntities(this, getBoundingBox(), entity -> canTeleportSafely(entity, destination) && !ArcanusComponents.hasPortalCoolDown(entity)).forEach(entity -> {
							if(destination.dimension() == PocketDimensionPortalComponent.POCKET_DIMENSION_WORLD_KEY)
								PocketDimensionComponent.get(serverLevel.getServer()).teleportToPocketDimension(serverCaster.getGameProfile(), entity);
							else
								PocketDimensionComponent.get(level()).teleportOutOfPocketDimension(entity);
						});

						if(ArcanusConfig.UtilityEffects.SpatialRiftEffectProperties.canSuckEntitiesIn) {
							level().getEntities(this, box, entity -> canTeleportSafely(entity, destination) && !ArcanusComponents.hasPortalCoolDown(entity)).forEach(entity -> {
								double distanceSq = position().distanceToSqr(entity.position());

								if(distanceSq <= boxRadiusSq && distanceSq != 0) {
									Vec3 direction = position().subtract(entity.position()).normalize();
									double inverseSq = 1 / distanceSq;

									entity.addDeltaMovement(direction.scale(inverseSq));
									entity.hurtMarked = true;
								}
							});
						}
					}
				}
				else {
					for(int i = 0; i < boxRadius * 2; ++i) {
						double particleX = position().x() + random.nextGaussian() * boxRadius;
						double particleY = position().y();
						double particleZ = position().z() + random.nextGaussian() * boxRadius;
						Vec3 particlePos = new Vec3(particleX, particleY, particleZ);
						Vec3 particleVelocity = particlePos.subtract(position());

						if(particlePos.distanceToSqr(position()) <= boxRadiusSq)
							level().addParticle(ParticleTypes.PORTAL, particleX, particleY, particleZ, particleVelocity.x(), particleVelocity.y(), particleVelocity.z());
					}
				}
			}
		}

		super.tick();
		entityData.set(TRUE_AGE, getTrueAge() + 1);
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		casterId = input.read("CasterId", UUIDUtil.CODEC).orElse(Util.NIL_UUID);
		pullStrength = input.getDoubleOr("PullStrength", 0);
		entityData.set(TRUE_AGE, input.getIntOr("TrueAge", 0));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		output.store("CasterId", UUIDUtil.CODEC, casterId);
		output.putDouble("PullStrength", pullStrength);
		output.putInt("TrueAge", entityData.get(TRUE_AGE));
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		return false;
	}

	private Player getCaster() {
		if(level() instanceof ServerLevel serverLevel)
			for(ServerLevel serverWorld : serverLevel.getServer().getAllLevels())
				if(serverWorld.getEntity(casterId) instanceof Player caster)
					return caster;

		return null;
	}

	public int getTrueAge() {
		return entityData.get(TRUE_AGE);
	}

	public void setProperties(UUID casterId, Vec3 pos, double pullStrength) {
		setPos(pos);
		this.casterId = casterId;
		this.pullStrength = pullStrength;
	}

	private static boolean canTeleportSafely(Entity entity, Level destination) {
		if(entity.isSpectator() || !entity.isAlive() || /*!entity.canChangeDimensions(entity.level(), destination) ||*/ (entity instanceof Player player && player instanceof FakePlayer))
			return false;

		return !entity.is(ArcanusEntityTags.SPATIAL_RIFT_IMMUNE);
	}
}
