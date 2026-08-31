package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.data.ArcanusBiomes;
import dev.cammiescorner.arcanus.data.ArcanusDimensionTypes;
import dev.cammiescorner.arcanus.data.ArcanusDimensions;
import dev.upcraft.sparkweave.api.datagen.provider.common.dynamic.SparkweaveDynamicRegistryEntryProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

import java.util.List;
import java.util.Optional;

public class ArcanusDimensionProvider extends SparkweaveDynamicRegistryEntryProvider {
	@Override
	public void generate(RegistrySetBuilder builder) {
		builder.add(Registries.DIMENSION_TYPE, ctx -> {
			var timelines = ctx.lookup(Registries.TIMELINE);
			var universalTimelines = timelines.getOrThrow(TimelineTags.UNIVERSAL);

			ctx.register(ArcanusDimensionTypes.POCKET_DIMENSION, new DimensionType(
				true,
				false,
				true,
				false,
				1.0D,
				-256,
				512,
				512,
				BlockTags.INFINIBURN_OVERWORLD,
				15.0F,
				new DimensionType.MonsterSettings(ConstantInt.of(0), 0),
				DimensionType.Skybox.NONE,
				CardinalLighting.Type.DEFAULT,
				EnvironmentAttributeMap.builder()
					.set(EnvironmentAttributes.SKY_COLOR, 0x000000)
					.set(EnvironmentAttributes.FOG_COLOR, 0xC0D8fF)
					.set(EnvironmentAttributes.WATER_FOG_COLOR, 0x050533)
					.set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
					.set(EnvironmentAttributes.BED_RULE, BedRule.EXPLODES)
					.build(),
				universalTimelines,
				Optional.empty()
				));
		});
		builder.add(Registries.LEVEL_STEM, context -> {
			var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
			var biomes = context.lookup(Registries.BIOME);
			context.register(Registries.levelToLevelStem(ArcanusDimensions.POCKET_DIMENSION), new LevelStem(
				dimensionTypes.getOrThrow(ArcanusDimensionTypes.POCKET_DIMENSION),
				new FlatLevelSource(
					new FlatLevelGeneratorSettings(
						Optional.empty(),
						biomes.getOrThrow(ArcanusBiomes.POCKET_DIMENSION),
						List.of()
					)
				)
			));
		});
	}

	@Override
	public String getName() {
		return "Dimensions";
	}
}
