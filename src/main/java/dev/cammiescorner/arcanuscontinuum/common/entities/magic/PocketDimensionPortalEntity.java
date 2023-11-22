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
import java.util.random.RandomGenerator;

public class PocketDimensionPortalEntity extends Entity {
	private static final TrackedData<Integer> TRUE_AGE = DataTracker.registerData(PocketDimensionPortalEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private final RandomGenerator rand = RandomGenerator.getDefault();
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
			Box box = new Box(0, 0, 0, 0, 0, 0).expand((4 + pullStrength) * ageMultiplier);
			double boxRadius = box.getXLength() / 2;
			double boxRadiusSq = boxRadius * boxRadius;

			getWorld().getEntitiesByClass(Entity.class, box.offset(getPos()), entity -> entity.isAlive() && !entity.isSpectator() && !(entity instanceof PlayerEntity player && player.isCreative())).forEach(entity -> {
				double distanceSq = getPos().squaredDistanceTo(entity.getPos());

				if(!(entity instanceof PocketDimensionPortalEntity) && distanceSq <= boxRadiusSq) {
					Vec3d direction = getPos().subtract(entity.getPos()).normalize();
					double inverseSq = 1 / distanceSq;

					entity.addVelocity(direction.multiply(inverseSq));
					entity.velocityModified = true;
				}
			});

			for(int i = 0; i < boxRadius * 2; ++i) {
				double particleX = getPos().getX() + (rand.nextDouble(-boxRadius, boxRadius + 1));
				double particleY = getPos().getY();
				double particleZ = getPos().getZ() + (rand.nextDouble(-boxRadius, boxRadius + 1));
				Vec3d particlePos = new Vec3d(particleX, particleY, particleZ);
				Vec3d particleVelocity = particlePos.subtract(getPos());

				if(particlePos.squaredDistanceTo(getPos()) <= boxRadiusSq)
					getWorld().addParticle(ParticleTypes.PORTAL, particleX, particleY, particleZ, particleVelocity.getX(), particleVelocity.getY(), particleVelocity.getZ());
			}
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
		if(getWorld().getServer() != null)
			for(ServerWorld serverWorld : getWorld().getServer().getWorlds())
				if(serverWorld.getEntity(casterId) instanceof PlayerEntity caster)
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
