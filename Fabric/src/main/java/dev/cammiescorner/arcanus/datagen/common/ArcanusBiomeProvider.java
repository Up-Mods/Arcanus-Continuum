package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.data.ArcanusBiomes;
import dev.upcraft.sparkweave.api.datagen.provider.SparkweaveDynamicRegistryEntryProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class ArcanusBiomeProvider extends SparkweaveDynamicRegistryEntryProvider {
	@Override
	public void generate(RegistrySetBuilder builder) {
		builder.add(Registries.BIOME, context -> {
			var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
			var worldCarvers = context.lookup(Registries.CONFIGURED_CARVER);

			context.register(ArcanusBiomes.POCKET_DIMENSION, new Biome.BiomeBuilder()
				.hasPrecipitation(false)
				.temperature(0.5f)
				.downfall(0.5f)
				.specialEffects(new BiomeSpecialEffects.Builder().waterColor(0x3f76E4).waterFogColor(0x050533).fogColor(0xC0D8fF).skyColor(0x000000).build())
				.mobSpawnSettings(new MobSpawnSettings.Builder().build())
				.generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).build()).build());
		});
	}

	@Override
	public String getName() {
		return Arcanus.MOD_ID;
	}
}
