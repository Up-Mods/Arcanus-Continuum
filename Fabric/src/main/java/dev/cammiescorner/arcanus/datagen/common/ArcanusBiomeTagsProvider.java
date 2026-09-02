package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.data.ArcanusBiomes;
import dev.cammiescorner.arcanus.data.ArcanusTags;
import dev.upcraft.sparkweave.api.datagen.provider.common.SparkweaveTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class ArcanusBiomeTagsProvider extends SparkweaveTagsProvider<Biome> {

	public ArcanusBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Registries.BIOME, Arcanus.MOD_ID, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		tag(ArcanusTags.Biomes.C_HAS_VILLAGE, "Has Village")
			.addExistingTag(BiomeTags.HAS_VILLAGE_DESERT)
			.addExistingTag(BiomeTags.HAS_VILLAGE_PLAINS)
			.addExistingTag(BiomeTags.HAS_VILLAGE_SAVANNA)
			.addExistingTag(BiomeTags.HAS_VILLAGE_SNOWY)
			.addExistingTag(BiomeTags.HAS_VILLAGE_TAIGA);

		tag(ArcanusTags.Biomes.HAS_WIZARD_TOWER, "Has Wizard Tower")
			.addTag(ArcanusTags.Biomes.C_HAS_VILLAGE)
			.addExistingTag(BiomeTags.HAS_PILLAGER_OUTPOST)
			.addExistingTag(BiomeTags.HAS_SWAMP_HUT)
			.addExistingTag(BiomeTags.HAS_WOODLAND_MANSION)
			.addExistingTag(BiomeTags.IS_HILL)
			.addExistingTag(BiomeTags.IS_MOUNTAIN)
			.addExistingTag(BiomeTags.IS_TAIGA)
			.addExistingTag(BiomeTags.IS_JUNGLE)
			.addExistingTag(BiomeTags.IS_FOREST)
			.addExistingTag(BiomeTags.IS_SAVANNA)
			.add(Biomes.MUSHROOM_FIELDS)
			.add(Biomes.ICE_SPIKES)
			.add(Biomes.SUNFLOWER_PLAINS)
			.add(Biomes.MANGROVE_SWAMP);

		existingTag(BiomeTags.WITHOUT_PATROL_SPAWNS)
			.add(ArcanusBiomes.POCKET_DIMENSION);

		existingTag(BiomeTags.WITHOUT_ZOMBIE_SIEGES)
			.add(ArcanusBiomes.POCKET_DIMENSION);

		existingTag(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS)
			.add(ArcanusBiomes.POCKET_DIMENSION);

		tag(ArcanusTags.Biomes.IS_POCKET_DIMENSION, "Is Pocket Dimension")
			.add(ArcanusBiomes.POCKET_DIMENSION);

		existingTag(BiomeTags.MINESHAFT_BLOCKING)
			.add(ArcanusBiomes.POCKET_DIMENSION);

		tag(ArcanusTags.Biomes.CAN_SPAWN_RED_BEANZ, "Can spawn Red Mana Beans")
			.addOptionalTag(BiomeTags.IS_BADLANDS)
			.add(Biomes.DESERT);

		tag(ArcanusTags.Biomes.CAN_SPAWN_BLUE_BEANZ, "Can spawn Blue Mana Beans")
			.addOptionalTag(BiomeTags.IS_OCEAN);

		tag(ArcanusTags.Biomes.CAN_SPAWN_BLACK_BEANZ, "Can spawn Black Mana Beans")
			.add(Biomes.DEEP_DARK);

		tag(ArcanusTags.Biomes.CAN_SPAWN_WHITE_BEANZ, "Can spawn White Mana Beans")
			.add(Biomes.STONY_PEAKS)
			.add(Biomes.FROZEN_PEAKS);

		tag(ArcanusTags.Biomes.CAN_SPAWN_GREEN_BEANZ, "Can spawn Green Mana Beans")
			.add(Biomes.LUSH_CAVES);

		tag(ArcanusTags.Biomes.SUITABLE_FOR_EBONY, "Suitable for Ebony Trees")
			.addTag(BiomeTags.IS_SAVANNA)
			.add(Biomes.SPARSE_JUNGLE);
	}
}
