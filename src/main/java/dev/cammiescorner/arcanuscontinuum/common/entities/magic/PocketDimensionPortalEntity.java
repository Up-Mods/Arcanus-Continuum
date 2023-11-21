package dev.cammiescorner.arcanuscontinuum.common.entities.magic;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

public class PocketDimensionPortalEntity extends Entity {
	private static final TrackedData<Integer> TRUE_AGE = DataTracker.registerData(PocketDimensionPortalEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private UUID casterId = Util.NIL_UUID;
	private double pullStrength;

	public PocketDimensionPortalEntity(EntityType<?> variant, World world) {
		super(variant, world);
	}

	@Override
	public void tick() {
		if(!getWorld().isClient() && (getCaster() == null || !getCaster().isAlive()) || getTrueAge() > 720) {
			kill();
			return;
		}

		if(getTrueAge() <= 700) {
			if(getTrueAge() > 100) {
				getWorld().getEntitiesByClass(Entity.class, getBoundingBox(), entity -> entity.canUsePortals() && !entity.isSpectator()).forEach(entity -> {
					if(!(entity instanceof PocketDimensionPortalEntity))
						ArcanusComponents.teleportToPocketDimension(getWorld().getProperties(), getCaster(), entity);
				});
			}

			float ageMultiplier = Math.min(100, getTrueAge()) / 100f;
			Box box = new Box(-4 * ageMultiplier, -4 * ageMultiplier, -4 * ageMultiplier, 4 * ageMultiplier, 4 * ageMultiplier, 4 * ageMultiplier).expand(pullStrength * ageMultiplier);

			getWorld().getEntitiesByClass(Entity.class, box.offset(getPos()), entity -> entity.isAlive() && !entity.isSpectator()).forEach(entity -> {
				double distanceSq = getPos().squaredDistanceTo(entity.getPos());

				if(!(entity instanceof PocketDimensionPortalEntity) && distanceSq <= box.getXLength() * box.getXLength()) {
					Vec3d direction = getPos().subtract(entity.getPos()).normalize();
					double inverseSq = 1 / distanceSq;

					entity.addVelocity(direction.multiply(inverseSq * pullStrength));
					entity.velocityModified = true;

					double particleX = getPos().getX() + 5;
					double particleY = getPos().getY();
					double particleZ = getPos().getZ();
					double particleVelX = 1 / ((getX() - particleX) * (getX() - particleX));
					double particleVelY = 1 / ((getY() - particleY) * (getY() - particleY));
					double particleVelZ = 1 / ((getZ() - particleZ) * (getZ() - particleZ));

					getWorld().addParticle(ParticleTypes.CLOUD, particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
				}
			});
		}

		super.tick();
		dataTracker.set(TRUE_AGE, getTrueAge() + 1);
	}

	@Override
	protected void initDataTracker() {
		dataTracker.startTracking(TRUE_AGE, 0);
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound tag) {
		casterId = tag.getUuid("CasterId");
		pullStrength = tag.getDouble("PullStrength");
		dataTracker.set(TRUE_AGE, tag.getInt("TrueAge"));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound tag) {
		tag.putUuid("CasterId", casterId);
		tag.putDouble("PullStrength", pullStrength);
		tag.putInt("TrueAge", dataTracker.get(TRUE_AGE));
	}

	private PlayerEntity getCaster() {
		if(getWorld() instanceof ServerWorld serverWorld && serverWorld.getEntity(casterId) instanceof PlayerEntity caster)
			return caster;

		return null;
	}

	public int getTrueAge() {
		return dataTracker.get(TRUE_AGE);
	}

	public int getColour() {
		return ArcanusComponents.getColour(this);
	}

	public void setColour(int colour) {
		ArcanusComponents.setColour(this, colour);
	}

	public void setProperties(UUID casterId, Vec3d pos, double pullStrength, int colour) {
		setPosition(pos);
		this.casterId = casterId;
		this.pullStrength = pullStrength;
		ArcanusComponents.setColour(this, colour);
	}
}
