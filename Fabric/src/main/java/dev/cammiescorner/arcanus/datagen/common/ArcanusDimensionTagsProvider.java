package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.data.ArcanusDimensionTypes;
import dev.cammiescorner.arcanus.data.ArcanusTags;
import dev.upcraft.sparkweave.api.datagen.provider.common.SparkweaveTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.concurrent.CompletableFuture;

public class ArcanusDimensionTagsProvider extends SparkweaveTagsProvider<DimensionType> {

	public ArcanusDimensionTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Registries.DIMENSION_TYPE, Arcanus.MOD_ID, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		tag(ArcanusTags.Dimensions.WARDING_NOT_ALLOWED, "Warding not allowed")
			.add(ArcanusDimensionTypes.POCKET_DIMENSION);
	}
}
