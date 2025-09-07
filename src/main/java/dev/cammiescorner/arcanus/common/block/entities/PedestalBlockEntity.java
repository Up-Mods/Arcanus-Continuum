package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalBlockEntity extends AbstractPedestalBlockEntity {
	public PedestalBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.PEDESTAL.get(), pos, blockState);
	}
}
