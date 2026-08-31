package dev.cammiescorner.arcanus.block.entities;

import dev.cammiescorner.arcanus.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SpatialRiftExitBlockEntity extends AbstractMagicBlockEntity {
	public SpatialRiftExitBlockEntity(BlockPos pos, BlockState state) {
		super(ArcanusBlockEntities.SPATIAL_RIFT_EXIT.get(), pos, state);
	}
}
