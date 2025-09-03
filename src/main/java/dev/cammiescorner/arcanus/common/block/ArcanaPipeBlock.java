package dev.cammiescorner.arcanus.common.block;

import com.google.common.collect.ImmutableMap;
import dev.cammiescorner.arcanus.common.util.ArcanaContainer;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.phys.BlockHitResult;
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
	public static final BooleanProperty CONNECTS_UP = BooleanProperty.create("connects_up");
	public static final BooleanProperty CONNECTS_DOWN = BooleanProperty.create("connects_down");
	public static final BooleanProperty CONNECTS_NORTH = BooleanProperty.create("connects_north");
	public static final BooleanProperty CONNECTS_EAST = BooleanProperty.create("connects_east");
	public static final BooleanProperty CONNECTS_SOUTH = BooleanProperty.create("connects_south");
	public static final BooleanProperty CONNECTS_WEST = BooleanProperty.create("connects_west");
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final Map<Direction, BooleanProperty> EXTENSION_BY_DIRECTION = ImmutableMap.of(
		Direction.UP, UP,
		Direction.DOWN, DOWN,
		Direction.NORTH, NORTH,
		Direction.EAST, EAST,
		Direction.SOUTH, SOUTH,
		Direction.WEST, WEST
	);
	public static final Map<Direction, BooleanProperty> CONNECTION_BY_DIRECTION = ImmutableMap.of(
		Direction.UP, CONNECTS_UP,
		Direction.DOWN, CONNECTS_DOWN,
		Direction.NORTH, CONNECTS_NORTH,
		Direction.EAST, CONNECTS_EAST,
		Direction.SOUTH, CONNECTS_SOUTH,
		Direction.WEST, CONNECTS_WEST
	);
	public static final VoxelShape SHAPE = Shapes.box(0.40625, 0.40625, 0.40625, 0.59375, 0.59375, 0.59375);
	public static final Map<Direction, VoxelShape> EXTENSION_SHAPES = ImmutableMap.of(
		Direction.UP, Shapes.box(0.4375, 0.595, 0.4375, 0.5625, 1, 0.5625),
		Direction.DOWN, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.405, 0.5625),
		Direction.NORTH, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.405),
		Direction.EAST, Shapes.box(0.595, 0.4375, 0.4375, 1, 0.5625, 0.5625),
		Direction.SOUTH, Shapes.box(0.4375, 0.4375, 0.595, 0.5625, 0.5625, 1),
		Direction.WEST, Shapes.box(0, 0.4375, 0.4375, 0.405, 0.5625, 0.5625)
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
				.setValue(CONNECTS_UP, true)
				.setValue(CONNECTS_DOWN, true)
				.setValue(CONNECTS_NORTH, true)
				.setValue(CONNECTS_EAST, true)
				.setValue(CONNECTS_SOUTH, true)
				.setValue(CONNECTS_WEST, true)
				.setValue(WATERLOGGED, false)
		);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND && player.isCrouching() && stack.isEmpty()) {
			for(Direction direction : Direction.values()) {
				// TODO half the faces dont intersect with the hit result, but inflating the aabb by any amount
				//  results in the thing affecting the opposite extension on half of the middle's faces...
				if(EXTENSION_SHAPES.get(direction).bounds().move(pos).contains(hitResult.getLocation())) {
					BooleanProperty connectionProperty = CONNECTION_BY_DIRECTION.get(direction);

					level.setBlockAndUpdate(pos, state.setValue(connectionProperty, !state.getValue(connectionProperty)));

					return ItemInteractionResult.SUCCESS;
				}
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		VoxelShape shape = SHAPE;

		Map<Direction, VoxelShape> EXTENSION_SHAPES = ImmutableMap.of(
			Direction.UP, Shapes.box(0.4375, 0.595, 0.4375, 0.5625, 1, 0.5625),
			Direction.DOWN, Shapes.box(0.4375, 0, 0.4375, 0.5625, 0.405, 0.5625),
			Direction.NORTH, Shapes.box(0.4375, 0.4375, 0, 0.5625, 0.5625, 0.405),
			Direction.EAST, Shapes.box(0.595, 0.4375, 0.4375, 1, 0.5625, 0.5625),
			Direction.SOUTH, Shapes.box(0.4375, 0.4375, 0.595, 0.5625, 0.5625, 1),
			Direction.WEST, Shapes.box(0, 0.4375, 0.4375, 0.405, 0.5625, 0.5625)
		);

		for(Direction value : Direction.values())
			if(state.getValue(EXTENSION_BY_DIRECTION.get(value)))
				shape = Shapes.or(shape, EXTENSION_SHAPES.get(value));

		return shape;
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();

		return defaultBlockState()
			.setValue(UP, false)
			.setValue(DOWN, false)
			.setValue(NORTH, false)
			.setValue(EAST, false)
			.setValue(SOUTH, false)
			.setValue(WEST, false)
			.setValue(CONNECTS_UP, true)
			.setValue(CONNECTS_DOWN, true)
			.setValue(CONNECTS_NORTH, true)
			.setValue(CONNECTS_EAST, true)
			.setValue(CONNECTS_SOUTH, true)
			.setValue(CONNECTS_WEST, true)
			.setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if(state.getValue(WATERLOGGED))
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

		return state.setValue(EXTENSION_BY_DIRECTION.get(direction), shouldConnect(level, direction, pos, neighborPos));
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
		builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, CONNECTS_UP, CONNECTS_DOWN, CONNECTS_NORTH, CONNECTS_EAST, CONNECTS_SOUTH, CONNECTS_WEST, WATERLOGGED);
	}

	public boolean shouldConnect(LevelAccessor level, Direction direction, BlockPos pos, BlockPos neighborPos) {
		BlockState state = level.getBlockState(pos);
		BlockState neighborState = level.getBlockState(neighborPos);

		return (neighborState.getBlock() instanceof ArcanaPipeBlock && neighborState.getValue(CONNECTION_BY_DIRECTION.get(direction.getOpposite())) == state.getValue(CONNECTION_BY_DIRECTION.get(direction))) ||
			(level.getBlockEntity(neighborPos) instanceof ArcanaContainer machine && machine.connectsToDirection(direction.getOpposite()));
	}
}
