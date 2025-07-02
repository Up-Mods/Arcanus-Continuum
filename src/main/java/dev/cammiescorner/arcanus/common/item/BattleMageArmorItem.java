package dev.cammiescorner.arcanus.common.item;

import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper;

import java.util.Locale;
import java.util.function.Supplier;

public class BattleMageArmorItem extends WizardArmorItem {
	public BattleMageArmorItem(Holder<ArmorMaterial> armorMaterial, Type equipmentSlot, double manaRegen, double magicResist, double spellPotency, double manaCostMultiplier, double spellCoolDown) {
		super(armorMaterial, equipmentSlot, manaRegen, magicResist, spellPotency, manaCostMultiplier, spellCoolDown);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);

		if(world instanceof ServerLevel serverWorld && entity instanceof LivingEntity living && living.getItemBySlot(getEquipmentSlot()).equals(stack) && !isWaxed(stack)) {
			int randomTickSpeed = serverWorld.getGameRules().getInt(GameRules.RULE_RANDOMTICKING);
			int oxidation = getOxidation(stack).ordinal();

			if(serverWorld.random.nextFloat() < 0.00001f * randomTickSpeed && oxidation < WeatheringCopper.WeatherState.values().length - 1)
				setOxidation(stack, WeatheringCopper.WeatherState.values()[oxidation + 1]);
		}
	}

	@Override
	public String getDescriptionId(ItemStack stack) {
		String s = "_";

		if(isWaxed(stack))
			s = "_waxed_";

		return super.getDescriptionId(stack) + s + getOxidation(stack).name().toLowerCase(Locale.ROOT);
	}

	public static boolean isWaxed(ItemStack stack) {
		return stack.getOrDefault(ArcanusDataComponents.WAXED.get(), false);
	}

	public static void setWaxed(ItemStack stack, boolean waxed) {
		stack.set(ArcanusDataComponents.WAXED.get(), waxed);
	}

	public static WeatheringCopper.WeatherState getOxidation(ItemStack stack) {
		return stack.getOrDefault(ArcanusDataComponents.WEATHER_STATE.get(), WeatheringCopper.WeatherState.UNAFFECTED);
	}

	public static void setOxidation(ItemStack stack, WeatheringCopper.WeatherState oxidizationLevel) {
		stack.set(ArcanusDataComponents.WEATHER_STATE.get(), oxidizationLevel);
	}

	public static ItemStack getStack(Supplier<? extends ItemLike> itemProvider, WeatheringCopper.WeatherState oxidizationLevel, boolean waxed) {
		var stack = new ItemStack(itemProvider.get());

		setWaxed(stack, waxed);
		setOxidation(stack, oxidizationLevel);

		return stack;
	}
}
