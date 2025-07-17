package dev.cammiescorner.arcanus.common.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class WizardRobesItem extends WizardArmorItem {
	public WizardRobesItem(Holder<ArmorMaterial> armorMaterial, Type equipmentSlot) {
		super(armorMaterial, equipmentSlot);
	}

	public int getColor(ItemStack stack) {
		return DyedItemColor.getOrDefault(stack, 0xFF52392A);
	}
}
