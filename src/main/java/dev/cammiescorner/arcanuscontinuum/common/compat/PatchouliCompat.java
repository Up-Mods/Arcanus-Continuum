package dev.cammiescorner.arcanuscontinuum.common.compat;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class PatchouliCompat {

	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(ArcanusItems.ITEM_GROUP).register(entries -> {
			entries.addBefore(ArcanusBlocks.MAGIC_DOOR, ArcanusItems.COMPENDIUM_ARCANUS);
		});
	}
}
