package dev.cammiescorner.arcanus.block.entities;

import dev.cammiescorner.arcanus.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SpatialRiftWallBlockEntity extends AbstractMagicBlockEntity {
	public SpatialRiftWallBlockEntity(BlockPos pos, BlockState state) {
		super(ArcanusBlockEntities.SPATIAL_RIFT_WALL.get(), pos, state);
	}
}
