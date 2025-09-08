package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.block.entities.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ArcanusBlockEntities {
	public static final RegistryHandler<BlockEntityType<?>> BLOCK_ENTITIES = RegistryHandler.create(Registries.BLOCK_ENTITY_TYPE, Arcanus.MOD_ID);

	public static final RegistrySupplier<BlockEntityType<ArcaneWorkbenchBlockEntity>> ARCANE_WORKBENCH = BLOCK_ENTITIES.register("arcane_workbench", () -> BlockEntityType.Builder.of(ArcaneWorkbenchBlockEntity::new, ArcanusBlocks.ARCANE_WORKBENCH.get()).build());
	public static final RegistrySupplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL = BLOCK_ENTITIES.register("pedestal", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, ArcanusBlocks.PEDESTAL.get()).build());
	public static final RegistrySupplier<BlockEntityType<ArcanePlinthBlockEntity>> ARCANE_PLINTH = BLOCK_ENTITIES.register("arcane_plinth", () -> BlockEntityType.Builder.of(ArcanePlinthBlockEntity::new, ArcanusBlocks.ARCANE_PLINTH.get()).build());
	public static final RegistrySupplier<BlockEntityType<CrucibleBlockEntity>> CRUCIBLE = BLOCK_ENTITIES.register("crucible", () -> BlockEntityType.Builder.of(CrucibleBlockEntity::new, ArcanusBlocks.CRUCIBLE.get()).build());
	public static final RegistrySupplier<BlockEntityType<AthanorBlockEntity>> ATHANOR = BLOCK_ENTITIES.register("athanor", () -> BlockEntityType.Builder.of(AthanorBlockEntity::new, ArcanusBlocks.ATHANOR.get()).build());
	public static final RegistrySupplier<BlockEntityType<AlembicBlockEntity>> ALEMBIC = BLOCK_ENTITIES.register("alembic", () -> BlockEntityType.Builder.of(AlembicBlockEntity::new, ArcanusBlocks.ALEMBIC.get()).build());
	public static final RegistrySupplier<BlockEntityType<VortexerBlockEntity>> VORTEXER = BLOCK_ENTITIES.register("vortexer", () -> BlockEntityType.Builder.of(VortexerBlockEntity::new, ArcanusBlocks.VORTEXER.get()).build());
	public static final RegistrySupplier<BlockEntityType<CentrifugeBlockEntity>> CENTRIFUGE = BLOCK_ENTITIES.register("centrifuge", () -> BlockEntityType.Builder.of(CentrifugeBlockEntity::new, ArcanusBlocks.CENTRIFUGE.get()).build());
	public static final RegistrySupplier<BlockEntityType<WardedJarBlockEntity>> WARDED_JAR = BLOCK_ENTITIES.register("warded_jar", () -> BlockEntityType.Builder.of(WardedJarBlockEntity::new, ArcanusBlocks.WARDED_JAR.get()).build());
	public static final RegistrySupplier<BlockEntityType<ManaBeanBlockEntity>> MANA_BEAN = BLOCK_ENTITIES.register("mana_bean", () -> BlockEntityType.Builder.of(ManaBeanBlockEntity::new, ArcanusBlocks.MANA_BEAN.get()).build());
	public static final RegistrySupplier<BlockEntityType<DummyBookshelfBlockEntity>> DUMMY_BOOKSHELF = BLOCK_ENTITIES.register("dummy_bookshelf", () -> BlockEntityType.Builder.of(DummyBookshelfBlockEntity::new, ArcanusBlocks.DUMMY_BOOKSHELF.get()).build());
	public static final RegistrySupplier<BlockEntityType<MagicBlockEntity>> MAGIC_BLOCK = BLOCK_ENTITIES.register("magic_block", () -> BlockEntityType.Builder.of(MagicBlockEntity::new, ArcanusBlocks.MAGIC_BLOCK.get()).build());
	public static final RegistrySupplier<BlockEntityType<MagicDoorBlockEntity>> MAGIC_DOOR = BLOCK_ENTITIES.register("magic_door", () -> BlockEntityType.Builder.of(MagicDoorBlockEntity::new, ArcanusBlocks.MAGIC_DOOR.get()).build());
	public static final RegistrySupplier<BlockEntityType<SpatialRiftExitBlockEntity>> SPATIAL_RIFT_EXIT = BLOCK_ENTITIES.register("spatial_rift_exit", () -> BlockEntityType.Builder.of(SpatialRiftExitBlockEntity::new, ArcanusBlocks.SPATIAL_RIFT_EXIT.get()).build());
	public static final RegistrySupplier<BlockEntityType<SpatialRiftWallBlockEntity>> SPATIAL_RIFT_WALL = BLOCK_ENTITIES.register("spatial_rift_wall", () -> BlockEntityType.Builder.of(SpatialRiftWallBlockEntity::new, ArcanusBlocks.SPATIAL_RIFT_WALL.get(), ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get()).build());
}
