package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.data.ArcanusBiomes;
import dev.upcraft.sparkweave.api.datagen.provider.common.dynamic.SparkweaveBiomeProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ArcanusBiomeProvider extends SparkweaveBiomeProvider {
	@Override
	protected void generateBiomes(Context ctx, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		ctx.register(ArcanusBiomes.POCKET_DIMENSION, new Biome.BiomeBuilder()
				.hasPrecipitation(false)
				.temperature(0.5f)
				.downfall(0.5f)
				.specialEffects(new BiomeSpecialEffects.Builder()
					.waterColor(0x3f76E4)
					.build()
				)
				.mobSpawnSettings(new MobSpawnSettings.Builder().build())
				.generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).build())
				.build(),
			"Pocket Dimension"
		);
	}
}
