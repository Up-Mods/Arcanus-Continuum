package dev.cammiescorner.arcanus.entity.magic;

import dev.cammiescorner.arcanus.api.entity.Targetable;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class TemporalDilationField extends Entity implements Targetable {
	private static final EntityDataAccessor<Integer> TRUE_AGE = SynchedEntityData.defineId(TemporalDilationField.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> MAX_AGE = SynchedEntityData.defineId(TemporalDilationField.class, EntityDataSerializers.INT);

	public TemporalDilationField(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public void tick() {
		super.tick();

		if(getAge() > getMaxAge() && level() instanceof ServerLevel serverLevel)
			kill(serverLevel);

		incrementAge();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(TRUE_AGE, 0);
		builder.define(MAX_AGE, 100);
	}

	@Override
	public boolean canBeHitByProjectile() {
		return false;
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		entityData.set(TRUE_AGE, input.getIntOr("TrueAge", 0));
		entityData.set(MAX_AGE, input.getIntOr("MaxAge", 0));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		output.putInt("TrueAge", getAge());
		output.putInt("MaxAge", getMaxAge());
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		return false;
	}

	public int getAge() {
		return entityData.get(TRUE_AGE);
	}

	public void incrementAge() {
		entityData.set(TRUE_AGE, getAge() + 1);
	}

	public int getMaxAge() {
		return entityData.get(MAX_AGE);
	}

	public void extendMaxAge(int extendAgeBy) {
		entityData.set(MAX_AGE, entityData.get(MAX_AGE) + extendAgeBy);
	}
}
