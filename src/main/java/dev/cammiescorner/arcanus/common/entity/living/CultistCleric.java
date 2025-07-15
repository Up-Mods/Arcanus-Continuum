package dev.cammiescorner.arcanus.common.entity.living;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class CultistCleric extends Cultist {
	private ManaType manaType;

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
		manaType = ManaType.values()[random.nextInt(ManaType.values().length)];

		ItemStack headStack = switch(manaType) {
			case RED -> new ItemStack(ArcanusItems.RED_CULT_HOOD.get());
			case GREEN -> new ItemStack(ArcanusItems.GREEN_CULT_HOOD.get());
			case BLUE -> new ItemStack(ArcanusItems.BLUE_CULT_HOOD.get());
			case WHITE -> new ItemStack(ArcanusItems.WHITE_CULT_HOOD.get());
			case BLACK -> new ItemStack(ArcanusItems.BLACK_CULT_HOOD.get());
		};
		ItemStack chestStack = switch(manaType) {
			case RED -> new ItemStack(ArcanusItems.RED_CULT_ROBES.get());
			case GREEN -> new ItemStack(ArcanusItems.GREEN_CULT_ROBES.get());
			case BLUE -> new ItemStack(ArcanusItems.BLUE_CULT_ROBES.get());
			case WHITE -> new ItemStack(ArcanusItems.WHITE_CULT_ROBES.get());
			case BLACK -> new ItemStack(ArcanusItems.BLACK_CULT_ROBES.get());
		};
		ItemStack legsStack = switch(manaType) {
			case RED -> new ItemStack(ArcanusItems.RED_CULT_PANTS.get());
			case GREEN -> new ItemStack(ArcanusItems.GREEN_CULT_PANTS.get());
			case BLUE -> new ItemStack(ArcanusItems.BLUE_CULT_PANTS.get());
			case WHITE -> new ItemStack(ArcanusItems.WHITE_CULT_PANTS.get());
			case BLACK -> new ItemStack(ArcanusItems.BLACK_CULT_PANTS.get());
		};
		ItemStack bootsStack = switch(manaType) {
			case RED -> new ItemStack(ArcanusItems.RED_CULT_BOOTS.get());
			case GREEN -> new ItemStack(ArcanusItems.GREEN_CULT_BOOTS.get());
			case BLUE -> new ItemStack(ArcanusItems.BLUE_CULT_BOOTS.get());
			case WHITE -> new ItemStack(ArcanusItems.WHITE_CULT_BOOTS.get());
			case BLACK -> new ItemStack(ArcanusItems.BLACK_CULT_BOOTS.get());
		};

		setItemSlot(EquipmentSlot.HEAD, headStack);
		setItemSlot(EquipmentSlot.CHEST, chestStack);
		setItemSlot(EquipmentSlot.LEGS, legsStack);
		setItemSlot(EquipmentSlot.FEET, bootsStack);
	}
}
