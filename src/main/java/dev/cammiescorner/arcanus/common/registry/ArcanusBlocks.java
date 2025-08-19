package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.block.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

public class ArcanusBlocks {
	public static final RegistryHandler<Block> BLOCKS = RegistryHandler.create(Registries.BLOCK, Arcanus.MOD_ID);

	public static final RegistrySupplier<Block> ARCANE_WORKBENCH = BLOCKS.register("arcane_workbench", ArcaneWorkbenchBlock::new);
	public static final RegistrySupplier<Block> ARCANE_PLINTH = BLOCKS.register("arcane_plinth", () -> new ArcanePlinthBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).noOcclusion().pushReaction(PushReaction.BLOCK)));
	public static final RegistrySupplier<Block> PEDESTAL = BLOCKS.register("pedestal", () -> new PedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).noOcclusion().pushReaction(PushReaction.BLOCK)));
	public static final RegistrySupplier<Block> JAR = BLOCKS.register("jar", JarBlock::new);
	public static final RegistrySupplier<Block> IGNIS_FRUIT = BLOCKS.register("ignis_fruit", () -> new ArcanaFruitBlock(ArcanusArcana.IGNIS));
	public static final RegistrySupplier<Block> TERRA_FRUIT = BLOCKS.register("terra_fruit", () -> new ArcanaFruitBlock(ArcanusArcana.TERRA));
	public static final RegistrySupplier<Block> AQUA_FRUIT = BLOCKS.register("aqua_fruit", () -> new ArcanaFruitBlock(ArcanusArcana.AQUA));
	public static final RegistrySupplier<Block> AER_FRUIT = BLOCKS.register("aer_fruit", () -> new ArcanaFruitBlock(ArcanusArcana.AER));
	public static final RegistrySupplier<Block> AETHER_FRUIT = BLOCKS.register("aether_fruit", () -> new ArcanaFruitBlock(ArcanusArcana.AETHER));
	public static final RegistrySupplier<Block> DUMMY_BOOKSHELF = BLOCKS.register("dummy_bookshelf", DummyBookshelfBlock::new);
	public static final RegistrySupplier<Block> MAGIC_BLOCK = BLOCKS.register("magic_block", MagicBlock::new);
	public static final RegistrySupplier<Block> MAGIC_DOOR = BLOCKS.register("magic_door", MagicDoorBlock::new);
	public static final RegistrySupplier<Block> SPATIAL_RIFT_WALL = BLOCKS.register("spatial_rift_wall", SpatialRiftWallBlock::new);
	public static final RegistrySupplier<Block> SPATIAL_RIFT_EXIT_EDGE = BLOCKS.register("spatial_rift_exit_edge", SpatialRiftExitEdgeBlock::new);
	public static final RegistrySupplier<Block> SPATIAL_RIFT_EXIT = BLOCKS.register("spatial_rift_exit", SpatialRiftExitBlock::new);
}
