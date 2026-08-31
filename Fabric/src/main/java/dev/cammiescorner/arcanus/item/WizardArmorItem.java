package dev.cammiescorner.arcanus.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;


public class WizardArmorItem extends Item {

	public WizardArmorItem(Item.Properties properties) {
		super(properties);

		// TODO cauldron interactions in sparkweave
//		CauldronInteractions.WATER.map().put(this, CauldronInteraction.DYED_ITEM);
	}
}
