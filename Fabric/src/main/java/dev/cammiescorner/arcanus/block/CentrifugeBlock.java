package dev.cammiescorner.arcanus.block;

import dev.cammiescorner.arcanus.block.entities.CentrifugeBlockEntity;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CentrifugeBlock extends Block implements EntityBlock, BlockItemProvider {

	public CentrifugeBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CentrifugeBlockEntity(pos, state);
	}
}
