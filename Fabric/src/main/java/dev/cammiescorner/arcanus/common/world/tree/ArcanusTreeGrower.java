package dev.cammiescorner.arcanus.common.world.tree;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ArcanusTreeGrower {
	public static final TreeGrower EBONY_TREE_GROWER = new TreeGrower(Arcanus.id("ebony").toString(),
		Optional.empty(), Optional.of(ArcanusFeatures.CONFIGURED_EBONY_TREE), Optional.empty()
	);
}
