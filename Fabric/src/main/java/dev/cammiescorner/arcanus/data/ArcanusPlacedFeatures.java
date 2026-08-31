package dev.cammiescorner.arcanus.data;

import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ArcanusPlacedFeatures {
	public static final ResourceKey<PlacedFeature> PLACED_EBONY_TREE = ResourceKey.create(Registries.PLACED_FEATURE, Arcanus.id("ebony_tree"));
}
