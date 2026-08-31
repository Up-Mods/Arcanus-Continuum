package dev.cammiescorner.arcanus.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class ArcanistRobesItem extends WizardArmorItem {

	public ArcanistRobesItem(Item.Properties properties) {
		super(properties, armorMaterial, armorType);
	}

	public static int getColor(ItemStack stack) {
		return DyedItemColor.getOrDefault(stack, 0xFF52392A);
	}
}
