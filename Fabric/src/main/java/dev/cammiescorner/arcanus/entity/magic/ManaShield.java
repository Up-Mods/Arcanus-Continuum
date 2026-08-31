package dev.cammiescorner.arcanus.entity.magic;

import dev.cammiescorner.arcanus.api.entity.Targetable;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ManaShield extends Entity implements Targetable {
	private static final EntityDataAccessor<Integer> MAX_AGE = SynchedEntityData.defineId(ManaShield.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TRUE_AGE = SynchedEntityData.defineId(ManaShield.class, EntityDataSerializers.INT);
	public static final ThreadLocal<Entity> COLLIDING_ENTITY = new ThreadLocal<>();
	public UUID ownerId = Util.NIL_UUID;

	public ManaShield(EntityType<? extends Entity> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		if(level() instanceof ServerLevel serverLevel) {
			if((getCaster() == null || !getCaster().isAlive())) {
				kill(serverLevel);
				return;
			}

			List<ManaShield> list = level().getEntitiesOfClass(ManaShield.class, getBoundingBox(), EntitySelector.ENTITY_STILL_ALIVE);

			if(!list.isEmpty()) {
				list.sort(Comparator.comparingInt(ManaShield::getTrueAge).reversed());
				int i = serverLevel.getGameRules().get(GameRules.MAX_ENTITY_CRAMMING);

				if(i > 0 && list.size() > i - 1) {
					int j = 0;

					for(ManaShield ignored : list)
						++j;

					if(j > i - 1) {
						kill(serverLevel);
						return;
					}
				}
			}

			if(level().getEntities(this, getBoundingBox(), entity -> entity instanceof LivingEntity && entity.isAlive()).isEmpty() && getTrueAge() + 20 < getMaxAge())
				entityData.set(MAX_AGE, getTrueAge() + 20);

			if(getTrueAge() >= getMaxAge()) {
				kill(serverLevel);
				return;
			}
		}

		super.tick();
		entityData.set(TRUE_AGE, getTrueAge() + 1);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(MAX_AGE, 0);
		builder.define(TRUE_AGE, 0);
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		entityData.set(MAX_AGE, input.getIntOr("MaxAge", 0));
		entityData.set(TRUE_AGE, input.getIntOr("TrueAge", 0));
		ownerId = input.read("OwnerId", UUIDUtil.CODEC).orElse(Util.NIL_UUID);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		output.putInt("MaxAge", entityData.get(MAX_AGE));
		output.putInt("TrueAge", entityData.get(TRUE_AGE));
		output.store("OwnerId", UUIDUtil.CODEC, ownerId);
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		return false;
	}

	@Override
	public boolean canBeCollidedWith(@Nullable Entity other) {
		if(COLLIDING_ENTITY.get() == null)
			return true;

		return getTrueAge() + 20 < getMaxAge() && !COLLIDING_ENTITY.get().getBoundingBox().intersects(getBoundingBox());
	}

	@Override
	public boolean isPickable() {
		return !isRemoved();
	}

	private LivingEntity getCaster() {
		if(level() instanceof ServerLevel serverWorld && serverWorld.getEntity(ownerId) instanceof LivingEntity caster)
			return caster;
		return null;
	}

	public int getMaxAge() {
		return entityData.get(MAX_AGE);
	}

	public void setMaxAge(int maxAge) {
		entityData.set(MAX_AGE, maxAge);
	}

	public int getTrueAge() {
		return entityData.get(TRUE_AGE);
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setProperties(UUID ownerId, Vec3 pos, int maxAge) {
		this.setPos(pos);
		this.setMaxAge(maxAge);
		this.ownerId = ownerId;
	}
}
