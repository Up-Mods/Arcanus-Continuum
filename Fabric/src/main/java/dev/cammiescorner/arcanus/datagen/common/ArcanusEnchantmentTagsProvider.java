package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.upcraft.sparkweave.api.datagen.provider.common.SparkweaveTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.concurrent.CompletableFuture;

public class ArcanusEnchantmentTagsProvider extends SparkweaveTagsProvider<Enchantment> {

	public ArcanusEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Registries.ENCHANTMENT, Arcanus.MOD_ID, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		// FIXME enchantment tags datagen
//		getOrCreateTagBuilder(ArcanusTags.Enchantments.MANA_POOL_COMPATIBLE_WITH)
//			.add(Enchantments.UNBREAKING)
//			.add(Enchantments.MENDING);
	}
}
