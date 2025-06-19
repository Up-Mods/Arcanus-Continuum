package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.blocks.entities.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ArcanusBlockEntities {
	public static final RegistryHandler<BlockEntityType<?>> BLOCK_ENTITIES = RegistryHandler.create(Registries.BLOCK_ENTITY_TYPE, Arcanus.MOD_ID);

	public static final RegistrySupplier<BlockEntityType<ArcaneWorkbenchBlockEntity>> ARCANE_WORKBENCH = BLOCK_ENTITIES.register("arcane_workbench", () -> BlockEntityType.Builder.of(ArcaneWorkbenchBlockEntity::new, ArcanusBlocks.ARCANE_WORKBENCH.get()).build());
	public static final RegistrySupplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL = BLOCK_ENTITIES.register("pedestal", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, ArcanusBlocks.PEDESTAL.get()).build());
	public static final RegistrySupplier<BlockEntityType<DummyBookshelfBlockEntity>> DUMMY_BOOKSHELF = BLOCK_ENTITIES.register("dummy_bookshelf", () -> BlockEntityType.Builder.of(DummyBookshelfBlockEntity::new, ArcanusBlocks.DUMMY_BOOKSHELF.get()).build());
	public static final RegistrySupplier<BlockEntityType<MagicBlockEntity>> MAGIC_BLOCK = BLOCK_ENTITIES.register("magic_block", () -> BlockEntityType.Builder.of(MagicBlockEntity::new, ArcanusBlocks.MAGIC_BLOCK.get()).build());
	public static final RegistrySupplier<BlockEntityType<MagicDoorBlockEntity>> MAGIC_DOOR = BLOCK_ENTITIES.register("magic_door", () -> BlockEntityType.Builder.of(MagicDoorBlockEntity::new, ArcanusBlocks.MAGIC_DOOR.get()).build());
	public static final RegistrySupplier<BlockEntityType<SpatialRiftExitBlockEntity>> SPATIAL_RIFT_EXIT = BLOCK_ENTITIES.register("spatial_rift_exit", () -> BlockEntityType.Builder.of(SpatialRiftExitBlockEntity::new, ArcanusBlocks.SPATIAL_RIFT_EXIT.get()).build());
	public static final RegistrySupplier<BlockEntityType<SpatialRiftWallBlockEntity>> SPATIAL_RIFT_WALL = BLOCK_ENTITIES.register("spatial_rift_wall", () -> BlockEntityType.Builder.of(SpatialRiftWallBlockEntity::new, ArcanusBlocks.SPATIAL_RIFT_WALL.get(), ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get()).build());
}
