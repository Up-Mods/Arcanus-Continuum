package dev.cammiescorner.arcanus.datagen.common.loot;

import dev.cammiescorner.arcanus.registry.ArcanusBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Blocks;

import java.util.Set;

public class ArcanusBlockLoot extends BlockLootSubProvider {
	public ArcanusBlockLoot(HolderLookup.Provider registries) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
	}

	@Override
	public void generate() {
		dropSelf(Blocks.CHISELED_BOOKSHELF);
		dropSelf(ArcanusBlocks.ARCANE_WORKBENCH.get());
		createDoorTable(ArcanusBlocks.MAGIC_DOOR.get());
	}
}
