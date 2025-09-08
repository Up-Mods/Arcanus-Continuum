package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.block.AlembicBlock;
import dev.cammiescorner.arcanus.common.block.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ArcanusBlocks {
	public static final RegistryHandler<Block> BLOCKS = RegistryHandler.create(Registries.BLOCK, Arcanus.MOD_ID);

	public static final RegistrySupplier<Block> EMYRWOOD_LOG = BLOCKS.register("emyrwood_log", () -> Blocks.log(MapColor.TERRACOTTA_GREEN, MapColor.COLOR_GREEN));
	public static final RegistrySupplier<Block> EMYRWOOD_WOOD = BLOCKS.register("emyrwood_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS).strength(2f).sound(SoundType.WOOD).ignitedByLava()));
	public static final RegistrySupplier<Block> STRIPPED_EMYRWOOD_LOG = BLOCKS.register("stripped_emyrwood_log", () -> Blocks.log(MapColor.TERRACOTTA_GREEN, MapColor.COLOR_GREEN));
	public static final RegistrySupplier<Block> STRIPPED_EMYRWOOD_WOOD = BLOCKS.register("stripped_emyrwood_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS).strength(2f).sound(SoundType.WOOD).ignitedByLava()));
	public static final RegistrySupplier<Block> EMYRWOOD_PLANKS = BLOCKS.register("emyrwood_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS).mapColor(MapColor.COLOR_GREEN)));
	public static final RegistrySupplier<Block> EMYRWOOD_STAIRS = BLOCKS.register("emyrwood_stairs", () -> Blocks.stair(EMYRWOOD_PLANKS.get()));
	public static final RegistrySupplier<Block> EMYRWOOD_SLAB = BLOCKS.register("emyrwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS).strength(2f, 3f).sound(SoundType.WOOD).ignitedByLava()));
	public static final RegistrySupplier<Block> ARCANE_WORKBENCH = BLOCKS.register("arcane_workbench", ArcaneWorkbenchBlock::new);
	public static final RegistrySupplier<Block> ARCANE_PLINTH = BLOCKS.register("arcane_plinth", () -> new ArcanePlinthBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).noOcclusion().pushReaction(PushReaction.BLOCK)));
	public static final RegistrySupplier<Block> PEDESTAL = BLOCKS.register("pedestal", () -> new PedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).noOcclusion().pushReaction(PushReaction.BLOCK)));
	public static final RegistrySupplier<Block> CRUCIBLE = BLOCKS.register("crucible", CrucibleBlock::new);
	public static final RegistrySupplier<Block> ATHANOR = BLOCKS.register("athanor", AthanorBlock::new);
	public static final RegistrySupplier<Block> ALEMBIC = BLOCKS.register("alembic", AlembicBlock::new);
	public static final RegistrySupplier<Block> CENTRIFUGE = BLOCKS.register("centrifuge", CentifugeBlock::new);
	public static final RegistrySupplier<Block> VORTEXER = BLOCKS.register("vortexer", VortexerBlock::new);
	public static final RegistrySupplier<Block> ARCANA_PIPE = BLOCKS.register("arcana_pipe", ArcanaPipeBlock::new);
	public static final RegistrySupplier<Block> ARCANA_PUMP = BLOCKS.register("arcana_pump", ArcanaPumpBlock::new);
	public static final RegistrySupplier<Block> WARDED_JAR = BLOCKS.register("warded_jar", WardedJarBlock::new);
	public static final RegistrySupplier<Block> MANA_BEAN = BLOCKS.register("mana_bean", ManaBeanBlock::new);
	public static final RegistrySupplier<Block> DUMMY_BOOKSHELF = BLOCKS.register("dummy_bookshelf", DummyBookshelfBlock::new);
	public static final RegistrySupplier<Block> MAGIC_BLOCK = BLOCKS.register("magic_block", MagicBlock::new);
	public static final RegistrySupplier<Block> MAGIC_DOOR = BLOCKS.register("magic_door", MagicDoorBlock::new);
	public static final RegistrySupplier<Block> SPATIAL_RIFT_WALL = BLOCKS.register("spatial_rift_wall", SpatialRiftWallBlock::new);
	public static final RegistrySupplier<Block> SPATIAL_RIFT_EXIT_EDGE = BLOCKS.register("spatial_rift_exit_edge", SpatialRiftExitEdgeBlock::new);
	public static final RegistrySupplier<Block> SPATIAL_RIFT_EXIT = BLOCKS.register("spatial_rift_exit", SpatialRiftExitBlock::new);
}
