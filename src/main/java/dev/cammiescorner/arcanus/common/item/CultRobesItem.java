package dev.cammiescorner.arcanus.common.item;

import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class CultRobesItem extends ArmorItem {
	public CultRobesItem(Holder<ArmorMaterial> holder, ArmorItem.Type type) {
		super(holder, type, type == Type.HELMET ? new Item.Properties().stacksTo(1).component(ArcanusDataComponents.HOOD_DOWN.get(), false) : new Properties().stacksTo(1));
	}
}
