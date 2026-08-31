package dev.cammiescorner.arcanus.world.tree;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.data.ArcanusConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ArcanusTreeGrowers {
	public static final TreeGrower EBONY_TREE = new TreeGrower(Arcanus.id("ebony").toString(),
		Optional.empty(), Optional.of(ArcanusConfiguredFeatures.CONFIGURED_EBONY_TREE), Optional.empty()
	);
}
