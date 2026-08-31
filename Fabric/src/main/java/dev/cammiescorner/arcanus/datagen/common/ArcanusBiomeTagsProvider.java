package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.upcraft.sparkweave.api.datagen.provider.common.SparkweaveTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class ArcanusBiomeTagsProvider extends SparkweaveTagsProvider<Biome> {

	public ArcanusBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Registries.BIOME, Arcanus.MOD_ID, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		//FIXME biome tags datagen
//		getOrCreateTagBuilder(ArcanusBiomeTags.C_HAS_VILLAGE, "Has Village")
//			.add(BiomeTags.HAS_VILLAGE_DESERT)
//			.forceAddTag(BiomeTags.HAS_VILLAGE_PLAINS)
//			.forceAddTag(BiomeTags.HAS_VILLAGE_SAVANNA)
//			.forceAddTag(BiomeTags.HAS_VILLAGE_SNOWY)
//			.forceAddTag(BiomeTags.HAS_VILLAGE_TAIGA);
//
//		getOrCreateTagBuilder(ArcanusBiomeTags.HAS_WIZARD_TOWER)
//			.addOptionalTag(ArcanusBiomeTags.C_HAS_VILLAGE)
//			.forceAddTag(BiomeTags.HAS_PILLAGER_OUTPOST)
//			.forceAddTag(BiomeTags.HAS_SWAMP_HUT)
//			.forceAddTag(BiomeTags.HAS_WOODLAND_MANSION)
//			.forceAddTag(BiomeTags.IS_HILL)
//			.forceAddTag(BiomeTags.IS_MOUNTAIN)
//			.forceAddTag(BiomeTags.IS_TAIGA)
//			.forceAddTag(BiomeTags.IS_JUNGLE)
//			.forceAddTag(BiomeTags.IS_FOREST)
//			.forceAddTag(BiomeTags.IS_SAVANNA)
//			.add(Biomes.MUSHROOM_FIELDS)
//			.add(Biomes.ICE_SPIKES)
//			.add(Biomes.SUNFLOWER_PLAINS)
//			.add(Biomes.MANGROVE_SWAMP);
//
//		getOrCreateTagBuilder(BiomeTags.WITHOUT_PATROL_SPAWNS)
//			.add(ArcanusBiomes.POCKET_DIMENSION);
//
//		getOrCreateTagBuilder(BiomeTags.WITHOUT_ZOMBIE_SIEGES)
//			.add(ArcanusBiomes.POCKET_DIMENSION);
//
//		getOrCreateTagBuilder(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS)
//			.add(ArcanusBiomes.POCKET_DIMENSION);
//
//		getOrCreateTagBuilder(ArcanusBiomeTags.IS_POCKET_DIMENSION)
//			.add(ArcanusBiomes.POCKET_DIMENSION);
//
//		getOrCreateTagBuilder(BiomeTags.MINESHAFT_BLOCKING)
//			.add(ArcanusBiomes.POCKET_DIMENSION);
//
//		getOrCreateTagBuilder(ArcanusBiomeTags.CAN_SPAWN_RED_BEANZ)
//			.addOptionalTag(BiomeTags.IS_BADLANDS)
//			.add(Biomes.DESERT);
//
//		getOrCreateTagBuilder(ArcanusBiomeTags.CAN_SPAWN_BLUE_BEANZ)
//			.addOptionalTag(BiomeTags.IS_OCEAN);
//
//		getOrCreateTagBuilder(ArcanusBiomeTags.CAN_SPAWN_BLACK_BEANZ)
//			.add(Biomes.DEEP_DARK);
//
//		getOrCreateTagBuilder(ArcanusBiomeTags.CAN_SPAWN_WHITE_BEANZ)
//			.add(Biomes.STONY_PEAKS)
//			.add(Biomes.FROZEN_PEAKS);
//
//		getOrCreateTagBuilder(ArcanusBiomeTags.CAN_SPAWN_GREEN_BEANZ)
//			.add(Biomes.LUSH_CAVES);
//
//		getOrCreateTagBuilder(ArcanusBiomeTags.SUITABLE_FOR_EBONY)
//			.addTag(BiomeTags.IS_SAVANNA)
//			.add(Biomes.SPARSE_JUNGLE);
	}
}
