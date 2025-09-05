package dev.cammiescorner.arcanus.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArcanaPumpBlock extends AbstractPipeBlock {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
	public VoxelShape SHAPE_X = Shapes.or(
		Shapes.box(0, 0.375, 0.375, 1, 0.625, 0.625),
		Shapes.box(0.25, 0.3125, 0.3125, 0.75, 0.6875, 0.6875),
		Shapes.box(0.4375, 0.4375, 0.25, 0.5625, 0.5625, 0.75),
		Shapes.box(0.4375, 0.25, 0.4375, 0.5625, 0.75, 0.5625)
	);
	public static final VoxelShape SHAPE_Y = Shapes.or(
		Shapes.box(0.375, 0, 0.375, 0.625, 1, 0.625),
		Shapes.box(0.3125, 0.25, 0.3125, 0.6875, 0.75, 0.6875),
		Shapes.box(0.25, 0.4375, 0.4375, 0.75, 0.5625, 0.5625),
		Shapes.box(0.4375, 0.4375, 0.25, 0.5625, 0.5625, 0.75)
	);
	public static final VoxelShape SHAPE_Z = Shapes.or(
		Shapes.box(0.375, 0.375, 0, 0.625, 0.625, 1),
		Shapes.box(0.3125, 0.3125, 0.25, 0.6875, 0.6875, 0.75),
		Shapes.box(0.25, 0.4375, 0.4375, 0.75, 0.5625, 0.5625),
		Shapes.box(0.4375, 0.25, 0.4375, 0.5625, 0.75, 0.5625)
	);

	public ArcanaPumpBlock() {
		super();
		registerDefaultState(getStateDefinition().any().setValue(AXIS, Direction.Axis.X));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return switch(state.getValue(AXIS)) {
			case X -> SHAPE_X;
			case Y -> SHAPE_Y;
			case Z -> SHAPE_Z;
		};
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context)
			.setValue(AXIS, context.getNearestLookingDirection().getAxis());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AXIS);
	}
}
