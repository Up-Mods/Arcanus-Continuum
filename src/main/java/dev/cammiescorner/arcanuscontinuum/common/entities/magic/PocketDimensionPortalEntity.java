package dev.cammiescorner.arcanuscontinuum.common.entities.magic;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
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

		if(getTrueAge() > 100 && getTrueAge() <= 700) {
			getWorld().getEntitiesByClass(PlayerEntity.class, getBoundingBox(), Entity::canUsePortals).forEach(entity -> {
				ArcanusComponents.teleportPlayerToPocket(getWorld().getProperties(), getCaster(), entity);
			});
		}

		float ageMultiplier = Math.min(100, getTrueAge()) / 100f;

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
