package dev.cammiescorner.arcanus.common.block;

import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArcanePlinthBlock extends Block implements BlockItemProvider {
	private static final VoxelShape SHAPE = Shapes.or(
		Shapes.box(0.0625, 0,    0.0625, 0.9375, 0.25, 0.9375),
		Shapes.box(0.125,  0.25, 0.125,  0.875,  0.5,  0.875)
	);

	public ArcanePlinthBlock() {
		super(Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).noOcclusion().pushReaction(PushReaction.BLOCK));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
