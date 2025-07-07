package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class JarBlockEntity extends BlockEntity {
	public JarBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.JAR.get(), pos, blockState);
	}
}
