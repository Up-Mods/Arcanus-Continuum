package dev.cammiescorner.arcanuscontinuum.common.items;

import net.minecraft.block.Oxidizable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.Locale;

public class BattleMageArmorItem extends WizardArmorItem {
	public BattleMageArmorItem(ArmorMaterial armorMaterial, ArmorSlot equipmentSlot, double manaRegen, double magicResist, double spellPotency, double manaCostMultiplier, double spellCoolDown) {
		super(armorMaterial, equipmentSlot, manaRegen, magicResist, spellPotency, manaCostMultiplier, spellCoolDown);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);

		if(world instanceof ServerWorld serverWorld && entity instanceof LivingEntity living && living.getEquippedStack(getPreferredSlot()).equals(stack) && !isWaxed(stack)) {
			int randomTickSpeed = serverWorld.getGameRules().getInt(GameRules.RANDOM_TICK_SPEED);
			int oxidation = getOxidation(stack).ordinal();
			NbtCompound nbt = stack.getOrCreateSubNbt(ItemStack.DISPLAY_KEY);

			if(serverWorld.random.nextFloat() < 0.00001 * randomTickSpeed && oxidation < Oxidizable.OxidizationLevel.values().length - 1)
				nbt.putInt("oxidation", oxidation + 1);
		}
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		String s = "_";

		if(isWaxed(stack))
			s = "_waxed_";

		return super.getTranslationKey(stack) + s + getOxidation(stack).name().toLowerCase(Locale.ROOT);
	}

	@Override
	public int getColor(ItemStack stack) {
		NbtCompound tag = stack.getSubNbt(ItemStack.DAMAGE_KEY);
		return tag != null && tag.contains(ItemStack.COLOR_KEY, NbtElement.NUMBER_TYPE) ? tag.getInt(ItemStack.COLOR_KEY) : 0xb38ef3;
	}

	public static boolean isWaxed(ItemStack stack) {
		NbtCompound tag = stack.getSubNbt(ItemStack.DISPLAY_KEY);
		return tag != null && tag.contains("waxed") && tag.getBoolean("waxed");
	}

	public static void setWaxed(ItemStack stack, boolean waxed) {
		NbtCompound tag = stack.getOrCreateSubNbt(ItemStack.DISPLAY_KEY);

		if(tag != null)
			tag.putBoolean("waxed", waxed);
	}

	public static Oxidizable.OxidizationLevel getOxidation(ItemStack stack) {
		NbtCompound tag = stack.getSubNbt(ItemStack.DISPLAY_KEY);
		return tag != null && tag.contains("oxidation", NbtElement.NUMBER_TYPE) ? Oxidizable.OxidizationLevel.values()[tag.getInt("oxidation")] : Oxidizable.OxidizationLevel.UNAFFECTED;
	}

	public static void setOxidation(ItemStack stack, Oxidizable.OxidizationLevel oxidizationLevel) {
		NbtCompound tag = stack.getSubNbt(ItemStack.DISPLAY_KEY);
		if(tag != null)
			tag.putInt("oxidation", oxidizationLevel.ordinal());
	}
}
