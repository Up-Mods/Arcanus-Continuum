package dev.cammiescorner.arcanus.common.creative_tabs;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.upcraft.sparkweave.api.item.CreativeTabHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

public class OccultismCreativeTab {
	public static final ResourceKey<CreativeModeTab> RESOURCE_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Arcanus.id("03_occultism_tab"));

	public static CreativeModeTab buildTab() {
		return CreativeTabHelper.newBuilder(RESOURCE_KEY).icon(ArcanusItems.CULTIST_CLERIC_HOOD.get()::getDefaultInstance).displayItems((params, output) -> {
			output.accept(ArcanusItems.CULTIST_CLERIC_HOOD.get());
			output.accept(ArcanusItems.CULTIST_CLERIC_ROBES.get());
			output.accept(ArcanusItems.CULTIST_CLERIC_PANTS.get());
			output.accept(ArcanusItems.CULTIST_CLERIC_BOOTS.get());
			output.accept(ArcanusItems.CULTIST_KNIGHT_HELMET.get());
			output.accept(ArcanusItems.CULTIST_KNIGHT_CHESTPLATE.get());
			output.accept(ArcanusItems.CULTIST_KNIGHT_LEGGINGS.get());
			output.accept(ArcanusItems.CULTIST_KNIGHT_BOOTS.get());

			output.accept(ArcanusItems.CULTIST_CLERIC_SPAWN_EGG.get());
			output.accept(ArcanusItems.CULTIST_KNIGHT_SPAWN_EGG.get());
		}).build();
	}
}
