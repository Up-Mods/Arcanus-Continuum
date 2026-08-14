package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ArcanusFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_EBONY_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, Arcanus.id("ebony_tree"));
	public static final ResourceKey<PlacedFeature> PLACED_EBONY_TREE = ResourceKey.create(Registries.PLACED_FEATURE, Arcanus.id("ebony_tree"));
}
