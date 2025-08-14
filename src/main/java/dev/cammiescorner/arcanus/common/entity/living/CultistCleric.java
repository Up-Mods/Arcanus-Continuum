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
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		populateDefaultEquipmentSlots(level.getRandom(), difficulty);
		return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		setItemSlot(EquipmentSlot.HEAD, ArcanusItems.CULTIST_HOOD.get().getDefaultInstance());
		setItemSlot(EquipmentSlot.CHEST, ArcanusItems.CULTIST_ROBES.get().getDefaultInstance());
		setItemSlot(EquipmentSlot.LEGS, ArcanusItems.CULTIST_PANTS.get().getDefaultInstance());
		setItemSlot(EquipmentSlot.FEET, ArcanusItems.CULTIST_BOOTS.get().getDefaultInstance());
		setItemSlot(EquipmentSlot.MAINHAND, ArcanusItems.STAFF.get().getDefaultInstance());
	}
}
