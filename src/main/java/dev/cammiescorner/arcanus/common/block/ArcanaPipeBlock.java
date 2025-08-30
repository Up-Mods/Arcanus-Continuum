package dev.cammiescorner.arcanus.common.block;

import com.google.common.collect.ImmutableMap;
import dev.cammiescorner.arcanus.common.util.ArcanaMachine;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ArcanaPipeBlock extends Block implements BlockItemProvider, SimpleWaterloggedBlock {
	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = ImmutableMap.of(
		Direction.UP, UP,
		Direction.DOWN, DOWN,
		Direction.NORTH, NORTH,
		Direction.EAST, EAST,
		Direction.SOUTH, SOUTH,
		Direction.WEST, WEST
	);
	public static final VoxelShape SHAPE = Shapes.box(0.40625, 0.40625, 0.40625, 0.59375, 0.59375, 0.59375);
	public static final Map<Direction, VoxelShape> EXTENSION_SHAPES = ImmutableMap.of(
		Direction.UP, Shapes.box(0.4375, 0.5, 0.4375, 0.5625, 1, 0.5625),
		Direction.DOWN, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.5, 0.5625),
		Direction.NORTH, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.5),
		Direction.EAST, Shapes.box(0.5, 0.4375, 0.4375, 1, 0.5625, 0.5625),
		Direction.SOUTH, Shapes.box(0.4375, 0.4375, 0.5, 0.5625, 0.5625, 1),
		Direction.WEST, Shapes.box(0, 0.4375, 0.4375, 0.5, 0.5625, 0.5625)
	);

	public ArcanaPipeBlock() {
		super(Properties.of().noOcclusion().isSuffocating((blockState, blockGetter, blockPos) -> false).dynamicShape());
		this.registerDefaultState(
			this.stateDefinition
				.any()
				.setValue(UP, false)
				.setValue(DOWN, false)
				.setValue(NORTH, false)
				.setValue(EAST, false)
				.setValue(SOUTH, false)
				.setValue(WEST, false)
				.setValue(WATERLOGGED, false)
		);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		VoxelShape shape = SHAPE;

		for(Direction value : Direction.values())
			if(state.getValue(PROPERTY_BY_DIRECTION.get(value)))
				shape = Shapes.or(shape, EXTENSION_SHAPES.get(value));

		return shape;
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();

		return defaultBlockState()
			.setValue(UP, shouldConnect(level, Direction.UP, pos.above()))
			.setValue(DOWN, shouldConnect(level, Direction.DOWN, pos.below()))
			.setValue(NORTH, shouldConnect(level, Direction.NORTH, pos.north()))
			.setValue(EAST, shouldConnect(level, Direction.EAST, pos.east()))
			.setValue(SOUTH, shouldConnect(level, Direction.SOUTH, pos.south()))
			.setValue(WEST, shouldConnect(level, Direction.WEST, pos.west()))
			.setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if(state.getValue(WATERLOGGED))
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

		return state.setValue(PROPERTY_BY_DIRECTION.get(direction), shouldConnect(level, direction, neighborPos));
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return !state.getValue(WATERLOGGED);
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
		return false;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
	}

	public boolean shouldConnect(LevelAccessor level, Direction direction, BlockPos pos) {
		BlockState state = level.getBlockState(pos);

		return state.getBlock() instanceof ArcanaPipeBlock || (level.getBlockEntity(pos) instanceof ArcanaMachine machine && machine.connectsToDirection(direction.getOpposite()));
	}
}
