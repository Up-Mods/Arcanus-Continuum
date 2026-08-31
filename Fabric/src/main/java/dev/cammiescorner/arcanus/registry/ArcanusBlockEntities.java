package dev.cammiescorner.arcanus.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.block.entities.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import dev.upcraft.sparkweave.api.registry.block.BlockEntityRegistryHandler;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public class ArcanusBlockEntities {
	public static final BlockEntityRegistryHandler BLOCK_ENTITIES = RegistryHandler.blockEntities(Arcanus.MOD_ID);

	public static final RegistrySupplier<BlockEntityType<ArcaneWorkbenchBlockEntity>> ARCANE_WORKBENCH = BLOCK_ENTITIES.register("arcane_workbench", ArcaneWorkbenchBlockEntity::new, Set.of(ArcanusBlocks.ARCANE_WORKBENCH));
	public static final RegistrySupplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL = BLOCK_ENTITIES.register("pedestal", PedestalBlockEntity::new, Set.of(ArcanusBlocks.PEDESTAL));
	public static final RegistrySupplier<BlockEntityType<ArcanePlinthBlockEntity>> ARCANE_PLINTH = BLOCK_ENTITIES.register("arcane_plinth", ArcanePlinthBlockEntity::new, Set.of(ArcanusBlocks.ARCANE_PLINTH));
	public static final RegistrySupplier<BlockEntityType<CrucibleBlockEntity>> CRUCIBLE = BLOCK_ENTITIES.register("crucible", CrucibleBlockEntity::new, Set.of(ArcanusBlocks.CRUCIBLE));
	public static final RegistrySupplier<BlockEntityType<AthanorBlockEntity>> ATHANOR = BLOCK_ENTITIES.register("athanor", AthanorBlockEntity::new, Set.of(ArcanusBlocks.ATHANOR));
	public static final RegistrySupplier<BlockEntityType<AlembicBlockEntity>> ALEMBIC = BLOCK_ENTITIES.register("alembic", AlembicBlockEntity::new, Set.of(ArcanusBlocks.ALEMBIC));
	public static final RegistrySupplier<BlockEntityType<VortexerBlockEntity>> VORTEXER = BLOCK_ENTITIES.register("vortexer", VortexerBlockEntity::new, Set.of(ArcanusBlocks.VORTEXER));
	public static final RegistrySupplier<BlockEntityType<CentrifugeBlockEntity>> CENTRIFUGE = BLOCK_ENTITIES.register("centrifuge", CentrifugeBlockEntity::new, Set.of(ArcanusBlocks.CENTRIFUGE));
	public static final RegistrySupplier<BlockEntityType<WardedJarBlockEntity>> WARDED_JAR = BLOCK_ENTITIES.register("warded_jar", WardedJarBlockEntity::new, Set.of(ArcanusBlocks.WARDED_JAR));
	public static final RegistrySupplier<BlockEntityType<ManaBeanBlockEntity>> MANA_BEAN = BLOCK_ENTITIES.register("mana_bean", ManaBeanBlockEntity::new, Set.of(ArcanusBlocks.MANA_BEAN));
	public static final RegistrySupplier<BlockEntityType<DummyBookshelfBlockEntity>> DUMMY_BOOKSHELF = BLOCK_ENTITIES.register("dummy_bookshelf", DummyBookshelfBlockEntity::new, Set.of(ArcanusBlocks.DUMMY_BOOKSHELF));
	public static final RegistrySupplier<BlockEntityType<MagicBlockEntity>> MAGIC_BLOCK = BLOCK_ENTITIES.register("magic_block", MagicBlockEntity::new, Set.of(ArcanusBlocks.MAGIC_BLOCK));
	public static final RegistrySupplier<BlockEntityType<MagicDoorBlockEntity>> MAGIC_DOOR = BLOCK_ENTITIES.register("magic_door", MagicDoorBlockEntity::new, Set.of(ArcanusBlocks.MAGIC_DOOR));
	public static final RegistrySupplier<BlockEntityType<SpatialRiftExitBlockEntity>> SPATIAL_RIFT_EXIT = BLOCK_ENTITIES.register("spatial_rift_exit", SpatialRiftExitBlockEntity::new, Set.of(ArcanusBlocks.SPATIAL_RIFT_EXIT));
	public static final RegistrySupplier<BlockEntityType<SpatialRiftWallBlockEntity>> SPATIAL_RIFT_WALL = BLOCK_ENTITIES.register("spatial_rift_wall", SpatialRiftWallBlockEntity::new, Set.of(ArcanusBlocks.SPATIAL_RIFT_WALL, ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE));
}
