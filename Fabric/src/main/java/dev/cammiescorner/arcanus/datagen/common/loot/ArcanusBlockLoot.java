package dev.cammiescorner.arcanus.datagen.common.loot;

import dev.cammiescorner.arcanus.registry.ArcanusBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

import java.util.Set;

public class ArcanusBlockLoot extends BlockLootSubProvider {
	public ArcanusBlockLoot(Set<Item> explosionResistant, FeatureFlagSet enabledFeatures, HolderLookup.Provider registries) {
		super(explosionResistant, enabledFeatures, registries);
	}

	@Override
	public void generate() {
		dropSelf(Blocks.CHISELED_BOOKSHELF);
		dropSelf(ArcanusBlocks.ARCANE_WORKBENCH.get());
		createDoorTable(ArcanusBlocks.MAGIC_DOOR.get());
	}
}
