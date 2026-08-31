package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.data.ArcanusTags;
import dev.cammiescorner.arcanus.data.ConventionalTags;
import dev.cammiescorner.arcanus.registry.ArcanusBlocks;
import dev.upcraft.sparkweave.api.datagen.provider.common.SparkweaveBlockTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ArcanusBlockTagsProvider extends SparkweaveBlockTagProvider {

	public ArcanusBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Arcanus.MOD_ID, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		existingTag(BlockTags.MINEABLE_WITH_AXE)
			.add(net.minecraft.world.level.block.Blocks.CHISELED_BOOKSHELF)
			.add(ArcanusBlocks.ARCANE_WORKBENCH.get())
			.add(ArcanusBlocks.MAGIC_DOOR.get());

		existingTag(BlockTags.WITHER_IMMUNE)
			.add(ArcanusBlocks.SPATIAL_RIFT_WALL.get())
			.add(ArcanusBlocks.SPATIAL_RIFT_EXIT.get())
			.add(ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get());

		existingTag(BlockTags.DRAGON_IMMUNE)
			.add(ArcanusBlocks.SPATIAL_RIFT_WALL.get())
			.add(ArcanusBlocks.SPATIAL_RIFT_EXIT.get())
			.add(ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get());

		// Ender Dragon cannot destroy blocks with this tag, but can fly straight through.
		existingTag(BlockTags.DRAGON_TRANSPARENT)
			.add(ArcanusBlocks.MAGIC_BLOCK.get());

		// Blocks in this tag do not let fluids or honey drip through.
		existingTag(BlockTags.IMPERMEABLE)
			.add(ArcanusBlocks.MAGIC_BLOCK.get())
			.add(ArcanusBlocks.SPATIAL_RIFT_WALL.get())
			.add(ArcanusBlocks.SPATIAL_RIFT_EXIT.get())
			.add(ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get());

		existingTag(ConventionalTags.Blocks.MOVEMENT_RESTRICTED)
			.add(ArcanusBlocks.MAGIC_BLOCK.get())
			.add(ArcanusBlocks.SPATIAL_RIFT_WALL.get())
			.add(ArcanusBlocks.SPATIAL_RIFT_EXIT.get())
			.add(ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get());

		tag(ArcanusTags.Blocks.WARDING_NOT_ALLOWED, "Warding not allowed")
			.add(net.minecraft.world.level.block.Blocks.PISTON_HEAD)
			.add(net.minecraft.world.level.block.Blocks.MOVING_PISTON)
			.add(ArcanusBlocks.MAGIC_BLOCK.get());
	}
}
