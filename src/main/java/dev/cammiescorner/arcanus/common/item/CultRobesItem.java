package dev.cammiescorner.arcanus.common.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class CultRobesItem extends ArmorItem {
	public CultRobesItem(Holder<ArmorMaterial> holder, ArmorItem.Type type) {
		super(holder, type,  new Item.Properties().stacksTo(1));
	}
}
