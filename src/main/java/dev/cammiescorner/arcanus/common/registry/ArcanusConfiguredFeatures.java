package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class ArcanusConfiguredFeatures {
	public static final RegistryHandler<ConfiguredFeature<?, ?>> FEATURES = RegistryHandler.create(Registries.CONFIGURED_FEATURE, Arcanus.MOD_ID);

	public static final RegistrySupplier<ConfiguredFeature<?, ?>> EBONY_TREE = FEATURES.register("ebony_tree", () ->
		new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(ArcanusBlocks.EBONY_LOG.get()),
			new StraightTrunkPlacer(5, 6, 3),
			BlockStateProvider.simple(ArcanusBlocks.EBONY_LEAVES.get()),
			new BlobFoliagePlacer(ConstantInt.of(4), ConstantInt.of(1), 3),
			new TwoLayersFeatureSize(1, 0, 2)
		).build())
	);
}
