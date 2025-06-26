package dev.cammiescorner.arcanus.common.items;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class WizardRobesItem extends WizardArmorItem {
	public WizardRobesItem(Holder<ArmorMaterial> armorMaterial, Type equipmentSlot, double manaRegen, double magicResist, double spellPotency, double manaCostMultiplier, double spellCoolDown) {
		super(armorMaterial, equipmentSlot, manaRegen, magicResist, spellPotency, manaCostMultiplier, spellCoolDown);
	}

	public int getColor(ItemStack stack) {
		return DyedItemColor.getOrDefault(stack, 0xff52392a);
	}
}
