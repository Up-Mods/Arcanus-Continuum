package dev.cammiescorner.arcanus.data;

import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ArcanusConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_EBONY_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, Arcanus.id("ebony_tree"));
}
