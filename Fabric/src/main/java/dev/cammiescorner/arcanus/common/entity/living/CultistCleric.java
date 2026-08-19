package dev.cammiescorner.arcanus.common.entity.living;

import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class CultistCleric extends Cultist {
	public CultistCleric(EntityType<? extends Mob> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @org.jspecify.annotations.Nullable SpawnGroupData groupData) {
		populateDefaultEquipmentSlots(level.getRandom(), difficulty);
		return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		setItemSlot(EquipmentSlot.HEAD, ArcanusItems.CULTIST_CLERIC_HOOD.get().getDefaultInstance());
		setItemSlot(EquipmentSlot.CHEST, ArcanusItems.CULTIST_CLERIC_ROBES.get().getDefaultInstance());
		setItemSlot(EquipmentSlot.LEGS, ArcanusItems.CULTIST_CLERIC_PANTS.get().getDefaultInstance());
		setItemSlot(EquipmentSlot.FEET, ArcanusItems.CULTIST_CLERIC_BOOTS.get().getDefaultInstance());
		setItemSlot(EquipmentSlot.MAINHAND, ArcanusItems.STAFF.get().getDefaultInstance());
	}
}
