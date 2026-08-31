package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.upcraft.sparkweave.api.datagen.provider.common.SparkweaveTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class ArcanusDamageTagsProvider extends SparkweaveTagsProvider<DamageType> {

	public ArcanusDamageTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Registries.DAMAGE_TYPE, Arcanus.MOD_ID, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		// FIXME damage type tags datagen
//		getOrCreateTagBuilder(DamageTypeTags.AVOIDS_GUARDIAN_THORNS)
//			.add(ArcanusDamageTypes.MAGIC)
//			.add(ArcanusDamageTypes.MAGIC_PROJECTILE);
//
//		getOrCreateTagBuilder(DamageTypeTags.IS_PROJECTILE)
//			.add(ArcanusDamageTypes.MAGIC_PROJECTILE);
//
//		getOrCreateTagBuilder(DamageTypeTags.WITCH_RESISTANT_TO)
//			.add(ArcanusDamageTypes.MAGIC)
//			.add(ArcanusDamageTypes.MAGIC_PROJECTILE);
	}
}
