package dev.cammiescorner.arcanus.block;

import com.google.common.collect.ImmutableMap;
import dev.cammiescorner.arcanus.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.util.ArcanaContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class ArcanaPipeBlock extends AbstractPipeBlock {
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

	public ArcanaPipeBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(
			getStateDefinition()
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
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND && player.isCrouching() && stack.isEmpty()) {
			Direction hitFace = hitResult.getDirection();
			Vec3 hitPoint = hitResult.getLocation();
			Vec3 multiplyBy = new Vec3(0.001, 0.001, 0.001);

			for(Direction direction : Direction.values()) {
				if(EXTENSION_SHAPES.get(direction).bounds().move(pos).expandTowards(Vec3.atCenterOf(hitFace.getUnitVec3i()).multiply(multiplyBy)).contains(hitPoint)) {
					BlockPos neighborPos = pos.relative(direction);
					BlockState neighborState = level.getBlockState(neighborPos);
					BooleanProperty connectionProperty = CONNECTION_BY_DIRECTION.get(direction);
					BooleanProperty neighborProperty = CONNECTION_BY_DIRECTION.get(direction.getOpposite());

					if(state.getValue(connectionProperty))
						level.setBlockAndUpdate(pos, state.setValue(connectionProperty, false).setValue(EXTENSION_BY_DIRECTION.get(direction), false));
					if(neighborState.is(ArcanusBlocks.ARCANA_PIPE.get()) && neighborState.getValue(neighborProperty))
						level.setBlockAndUpdate(neighborPos, neighborState.setValue(neighborProperty, false).setValue(EXTENSION_BY_DIRECTION.get(direction.getOpposite()), false));

					return InteractionResult.SUCCESS;
				}
			}

			if(SHAPE.bounds().move(pos).expandTowards(Vec3.atCenterOf(hitFace.getUnitVec3i()).multiply(multiplyBy)).contains(hitPoint)) {
				BlockPos neighborPos = pos.relative(hitFace);
				BlockState neighborState = level.getBlockState(neighborPos);
				BooleanProperty connectionProperty = CONNECTION_BY_DIRECTION.get(hitFace);
				BooleanProperty neighborProperty = CONNECTION_BY_DIRECTION.get(hitFace.getOpposite());

				if(!state.getValue(connectionProperty))
					level.setBlockAndUpdate(pos, state.setValue(connectionProperty, true).setValue(EXTENSION_BY_DIRECTION.get(hitFace), true));
				if(neighborState.is(ArcanusBlocks.ARCANA_PIPE.get()) && !neighborState.getValue(neighborProperty))
					level.setBlockAndUpdate(neighborPos, neighborState.setValue(neighborProperty, true).setValue(EXTENSION_BY_DIRECTION.get(hitFace.getOpposite()), true));

				return InteractionResult.SUCCESS;
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		VoxelShape shape = SHAPE;

		for(Direction value : Direction.values()) {
			if(state.getValue(EXTENSION_BY_DIRECTION.get(value)) && state.getValue(CONNECTION_BY_DIRECTION.get(value)))
				shape = Shapes.or(shape, EXTENSION_SHAPES.get(value));
		}

		return shape;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);

		return state
			.setValue(UP, shouldConnect(context.getLevel(), Direction.UP, state, context.getClickedPos().relative(Direction.UP)))
			.setValue(DOWN, shouldConnect(context.getLevel(), Direction.DOWN, state, context.getClickedPos().relative(Direction.DOWN)))
			.setValue(NORTH, shouldConnect(context.getLevel(), Direction.NORTH, state, context.getClickedPos().relative(Direction.NORTH)))
			.setValue(EAST, shouldConnect(context.getLevel(), Direction.EAST, state, context.getClickedPos().relative(Direction.EAST)))
			.setValue(SOUTH, shouldConnect(context.getLevel(), Direction.SOUTH, state, context.getClickedPos().relative(Direction.SOUTH)))
			.setValue(WEST, shouldConnect(context.getLevel(), Direction.WEST, state, context.getClickedPos().relative(Direction.WEST)))
			.setValue(CONNECTS_UP, true)
			.setValue(CONNECTS_DOWN, true)
			.setValue(CONNECTS_NORTH, true)
			.setValue(CONNECTS_EAST, true)
			.setValue(CONNECTS_SOUTH, true)
			.setValue(CONNECTS_WEST, true);
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random).setValue(EXTENSION_BY_DIRECTION.get(directionToNeighbour), shouldConnect(level, directionToNeighbour, state, neighbourPos));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, CONNECTS_UP, CONNECTS_DOWN, CONNECTS_NORTH, CONNECTS_EAST, CONNECTS_SOUTH, CONNECTS_WEST);
	}

	public boolean shouldConnect(LevelReader level, Direction direction, BlockState state, BlockPos neighborPos) {
		BlockState neighborState = level.getBlockState(neighborPos);
		boolean connects = state.getValue(CONNECTION_BY_DIRECTION.get(direction));

		return connects && (level.getBlockEntity(neighborPos) instanceof ArcanaContainer container && container.connectsToDirection(direction.getOpposite())
			|| ((neighborState.is(ArcanusBlocks.ARCANA_PIPE.get()) && neighborState.getValue(CONNECTION_BY_DIRECTION.get(direction.getOpposite()))))
			|| neighborState.is(ArcanusBlocks.ARCANA_PUMP.get()) && neighborState.getValue(ArcanaPumpBlock.AXIS).test(direction));
	}
}
