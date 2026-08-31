package dev.cammiescorner.arcanus.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.block.*;
import dev.cammiescorner.arcanus.world.tree.ArcanusTreeGrowers;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import dev.upcraft.sparkweave.api.registry.block.BlockRegistryHandler;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ArcanusBlocks {
	public static final BlockRegistryHandler BLOCKS = RegistryHandler.blocks(Arcanus.MOD_ID);

	public static final RegistrySupplier<Block> EBONY_LOG = BLOCKS.register("ebony_log", RotatedPillarBlock::new, Blocks.logProperties(MapColor.TERRACOTTA_BLACK, MapColor.COLOR_BLACK, SoundType.WOOD));
	public static final RegistrySupplier<Block> EBONY_WOOD = BLOCKS.register("ebony_wood", RotatedPillarBlock:: new, Blocks.logProperties(MapColor.TERRACOTTA_BLACK, MapColor.COLOR_BLACK, SoundType.WOOD));
	public static final RegistrySupplier<Block> EBONY_LEAVES = BLOCKS.register("ebony_leaves", properties -> new TintedParticleLeavesBlock(0.01f, properties), Blocks.leavesProperties(SoundType.GRASS));
	public static final RegistrySupplier<Block> EBONY_SAPLING = BLOCKS.register("ebony_sapling", properties -> new SaplingBlock(ArcanusTreeGrowers.EBONY_TREE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING));
	public static final RegistrySupplier<Block> STRIPPED_EBONY_LOG = BLOCKS.register("stripped_ebony_log", RotatedPillarBlock::new, Blocks.logProperties(MapColor.TERRACOTTA_BLACK, MapColor.COLOR_BLACK, SoundType.WOOD));
	public static final RegistrySupplier<Block> STRIPPED_EBONY_WOOD = BLOCKS.register("stripped_ebony_wood", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).strength(2f).sound(SoundType.WOOD).ignitedByLava());
	public static final RegistrySupplier<Block> EBONY_PLANKS = BLOCKS.register("ebony_planks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_BLACK));
	public static final RegistrySupplier<Block> EBONY_STAIRS = BLOCKS.register("ebony_stairs", properties -> new StairBlock(EBONY_PLANKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofFullCopy(EBONY_PLANKS.get()));
	public static final RegistrySupplier<Block> EBONY_SLAB = BLOCKS.register("ebony_slab", SlabBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).strength(2f, 3f).sound(SoundType.WOOD).ignitedByLava());
	public static final RegistrySupplier<Block> ARCANE_WORKBENCH = BLOCKS.register("arcane_workbench", ArcaneWorkbenchBlock::new, BlockBehaviour.Properties.of().mapColor(DyeColor.RED).strength(2f, 3f).lightLevel(value -> 12).noOcclusion().randomTicks());
	public static final RegistrySupplier<Block> ARCANE_PLINTH = BLOCKS.register("arcane_plinth", ArcanePlinthBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).noOcclusion().pushReaction(PushReaction.BLOCK));
	public static final RegistrySupplier<Block> PEDESTAL = BLOCKS.register("pedestal", PedestalBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).noOcclusion().pushReaction(PushReaction.BLOCK));
	public static final RegistrySupplier<Block> CRUCIBLE = BLOCKS.register("crucible", CrucibleBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
	public static final RegistrySupplier<Block> ATHANOR = BLOCKS.register("athanor", AthanorBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
	public static final RegistrySupplier<Block> ALEMBIC = BLOCKS.register("alembic", AlembicBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
	public static final RegistrySupplier<Block> CENTRIFUGE = BLOCKS.register("centrifuge", CentrifugeBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
	public static final RegistrySupplier<Block> VORTEXER = BLOCKS.register("vortexer", VortexerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
	public static final RegistrySupplier<Block> ARCANA_PIPE = BLOCKS.register("arcana_pipe", ArcanaPipeBlock::new, BlockBehaviour.Properties::of);
	public static final RegistrySupplier<Block> ARCANA_PUMP = BLOCKS.register("arcana_pump", ArcanaPumpBlock::new, BlockBehaviour.Properties::of);
	public static final RegistrySupplier<Block> WARDED_JAR = BLOCKS.register("warded_jar", WardedJarBlock::new, BlockBehaviour.Properties.of().noOcclusion());
	public static final RegistrySupplier<Block> MANA_BEAN = BLOCKS.register("mana_bean", ManaBeanBlock::new, BlockBehaviour.Properties.of().sound(SoundType.GRASS).mapColor(MapColor.PLANT).dynamicShape().pushReaction(PushReaction.DESTROY).randomTicks().isRedstoneConductor(Blocks::never));
	public static final RegistrySupplier<Block> DUMMY_BOOKSHELF = BLOCKS.register("dummy_bookshelf", DummyBookshelfBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_BOOKSHELF).noLootTable());
	public static final RegistrySupplier<Block> MAGIC_BLOCK = BLOCKS.register("magic_block", MagicBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN).sound(SoundType.GLASS).lightLevel(_ -> 12).noOcclusion().isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never));
	public static final RegistrySupplier<Block> MAGIC_DOOR = BLOCKS.register("magic_door", properties -> new MagicDoorBlock(BlockSetType.OAK, properties), BlockBehaviour.Properties.of().strength(2f, 3f).sound(SoundType.WOOD).pushReaction(PushReaction.IGNORE).noOcclusion());
	public static final RegistrySupplier<Block> SPATIAL_RIFT_WALL = BLOCKS.register("spatial_rift_wall", SpatialRiftWallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).sound(SoundType.GLASS).lightLevel(_ -> 12).noOcclusion().isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never));
	public static final RegistrySupplier<Block> SPATIAL_RIFT_EXIT_EDGE = BLOCKS.register("spatial_rift_exit_edge", SpatialRiftExitEdgeBlock::new, BlockBehaviour.Properties.ofFullCopy(ArcanusBlocks.SPATIAL_RIFT_WALL.get()).sound(SoundType.STONE).lightLevel(_ -> 9));
	public static final RegistrySupplier<Block> SPATIAL_RIFT_EXIT = BLOCKS.register("spatial_rift_exit", SpatialRiftExitBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(ArcanusBlocks.SPATIAL_RIFT_WALL.get()).sound(SoundType.STONE).lightLevel(_ -> 7));
}
