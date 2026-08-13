package dev.cammiescorner.arcanus.common.world.tree;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class ArcanusTreeGrower {
	public static final TreeGrower EBONY_TREE_GROWER = new TreeGrower(Arcanus.id("ebony").toString(),
		Optional.empty(), ArcanusConfiguredFeatures.EBONY_TREE.<ConfiguredFeature<?, ?>>holder().unwrapKey(), Optional.empty()
	);
}
