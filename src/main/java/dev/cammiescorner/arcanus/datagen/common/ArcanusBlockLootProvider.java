package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ArcanusBlockLootProvider extends FabricBlockLootTableProvider {
	public ArcanusBlockLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		dropSelf(Blocks.CHISELED_BOOKSHELF);
		dropSelf(ArcanusBlocks.ARCANE_WORKBENCH.get());
		createDoorTable(ArcanusBlocks.MAGIC_DOOR.get());
	}
}
