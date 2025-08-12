package dev.cammiescorner.arcanus.common.entity.living;

import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class CultistCleric extends Cultist {
	private ArcanaType arcanaType;

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
		arcanaType = ArcanaType.values()[random.nextInt(ArcanaType.values().length)];

		ItemStack headStack = switch(arcanaType) {
			case IGNIS -> new ItemStack(ArcanusItems.RED_CULT_HOOD.get());
			case TERRA -> new ItemStack(ArcanusItems.GREEN_CULT_HOOD.get());
			case AQUA -> new ItemStack(ArcanusItems.BLUE_CULT_HOOD.get());
			case AER -> new ItemStack(ArcanusItems.WHITE_CULT_HOOD.get());
			case AETHER -> new ItemStack(ArcanusItems.BLACK_CULT_HOOD.get());
		};
		ItemStack chestStack = switch(arcanaType) {
			case IGNIS -> new ItemStack(ArcanusItems.RED_CULT_ROBES.get());
			case TERRA -> new ItemStack(ArcanusItems.GREEN_CULT_ROBES.get());
			case AQUA -> new ItemStack(ArcanusItems.BLUE_CULT_ROBES.get());
			case AER -> new ItemStack(ArcanusItems.WHITE_CULT_ROBES.get());
			case AETHER -> new ItemStack(ArcanusItems.BLACK_CULT_ROBES.get());
		};
		ItemStack legsStack = switch(arcanaType) {
			case IGNIS -> new ItemStack(ArcanusItems.RED_CULT_PANTS.get());
			case TERRA -> new ItemStack(ArcanusItems.GREEN_CULT_PANTS.get());
			case AQUA -> new ItemStack(ArcanusItems.BLUE_CULT_PANTS.get());
			case AER -> new ItemStack(ArcanusItems.WHITE_CULT_PANTS.get());
			case AETHER -> new ItemStack(ArcanusItems.BLACK_CULT_PANTS.get());
		};
		ItemStack bootsStack = switch(arcanaType) {
			case IGNIS -> new ItemStack(ArcanusItems.RED_CULT_BOOTS.get());
			case TERRA -> new ItemStack(ArcanusItems.GREEN_CULT_BOOTS.get());
			case AQUA -> new ItemStack(ArcanusItems.BLUE_CULT_BOOTS.get());
			case AER -> new ItemStack(ArcanusItems.WHITE_CULT_BOOTS.get());
			case AETHER -> new ItemStack(ArcanusItems.BLACK_CULT_BOOTS.get());
		};

		setItemSlot(EquipmentSlot.HEAD, headStack);
		setItemSlot(EquipmentSlot.CHEST, chestStack);
		setItemSlot(EquipmentSlot.LEGS, legsStack);
		setItemSlot(EquipmentSlot.FEET, bootsStack);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ArcanusItems.STAFF.get()));
	}
}
