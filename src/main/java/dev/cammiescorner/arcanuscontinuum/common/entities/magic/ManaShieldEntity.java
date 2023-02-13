package dev.cammiescorner.arcanuscontinuum.common.entities.magic;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ManaShieldEntity extends Entity {
	private static final TrackedData<Integer> MAX_AGE = DataTracker.registerData(ManaShieldEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> TRUE_AGE = DataTracker.registerData(ManaShieldEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static final ThreadLocal<Entity> COLLIDING_ENTITY = new ThreadLocal<>();

	public ManaShieldEntity(EntityType<? extends Entity> entityType, World world) {
		super(entityType, world);
		setNoGravity(true);
	}

	@Override
	public void tick() {
		if(world.getOtherEntities(this, getBoundingBox(), entity -> entity instanceof LivingEntity && entity.isAlive()).isEmpty() && getTrueAge() + 20 < getMaxAge())
			dataTracker.set(MAX_AGE, getTrueAge() + 20);

		if(getTrueAge() >= getMaxAge())
			kill();

		super.tick();
		dataTracker.set(TRUE_AGE, getTrueAge() + 1);
	}

	@Override
	protected void initDataTracker() {
		dataTracker.startTracking(MAX_AGE, 0);
		dataTracker.startTracking(TRUE_AGE, 0);
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound tag) {
		dataTracker.set(MAX_AGE, tag.getInt("MaxAge"));
		dataTracker.set(TRUE_AGE, tag.getInt("TrueAge"));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound tag) {
		tag.putInt("MaxAge", getMaxAge());
		tag.putInt("TrueAge", getTrueAge());
	}

	@Override
	public boolean isCollidable() {
		if(COLLIDING_ENTITY.get() == null)
			return true;

		return getTrueAge() + 20 < getMaxAge() && !COLLIDING_ENTITY.get().getBoundingBox().intersects(getBoundingBox());
	}

	@Override
	public boolean collides() {
		return !isRemoved();
	}

	public int getMaxAge() {
		return dataTracker.get(MAX_AGE);
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

	public void setProperties(Vec3d pos, int colour, int maxAge) {
		setPosition(pos);
		setColour(colour);
		dataTracker.set(MAX_AGE, maxAge);
	}
}
