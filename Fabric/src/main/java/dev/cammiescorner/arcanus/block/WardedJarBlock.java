package dev.cammiescorner.arcanus.block;

import com.teamresourceful.resourcefulconfig.client.components.options.types.color.HsbColor;
import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.block.entities.WardedJarBlockEntity;
import dev.cammiescorner.arcanus.data_component.ArcanaStack;
import dev.cammiescorner.arcanus.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class WardedJarBlock extends Block implements BlockItemProvider, EntityBlock, SimpleWaterloggedBlock {
	private static final VoxelShape SHAPE = Shapes.or(
		Shapes.box(0.1875,  0,      0.1875,  0.8125,  0.0625, 0.8125), // bottom
		Shapes.box(0.1875,  0.0625, 0.1875,  0.8125,  0.6875, 0.25),   // front
		Shapes.box(0.1875,  0.0625, 0.1875,  0.25,    0.6875, 0.8125), // right side
		Shapes.box(0.75,    0.0625, 0.1875,  0.8125,  0.6875, 0.8125), // left side
		Shapes.box(0.1875,  0.0625, 0.75,    0.8125,  0.6875, 0.8125), // back
		Shapes.box(0.1875,  0.6875, 0.1875,  0.8125,  0.75,   0.8125), // top
		Shapes.box(0.28125, 0.75,   0.28125, 0.71875, 0.875,  0.71875) // lid
	);
	private static final VoxelShape PIPE = Shapes.box(0.4375, 0.5, 0.4375, 0.5625, 1, 0.5625);
	public static final VoxelShape INSIDE = Shapes.box(0.25, 0.0625, 0.25, 0.75, 0.6875, 0.75);
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty CONNECTED_TO_PIPE = BooleanProperty.create("connected_to_pipe");
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 8);

	public WardedJarBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(CONNECTED_TO_PIPE, false).setValue(WATERLOGGED, false).setValue(LEVEL, 0));
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if(player.getAttributeValue(Attributes.SCALE) <= 0.25 && player.getItemInHand(hand).isEmpty()) {
			Vec3 vec3 = pos.getBottomCenter();

			if(INSIDE.bounds().inflate(0.001).move(pos).contains(hitResult.getLocation())) {
				player.teleportTo(vec3.x, vec3.y + 1, vec3.z);
				return InteractionResult.SUCCESS_SERVER;
			}

			if(player.isCrouching()) {
				player.teleportTo(vec3.x, vec3.y + 0.0625, vec3.z);
				return InteractionResult.SUCCESS_SERVER;
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if(!level.isClientSide() && level.getBlockEntity(pos) instanceof WardedJarBlockEntity jar) {
			ArcanaStack arcanaStack = stack.getOrDefault(ArcanusDataComponents.ARCANA_STACK.get(), ArcanaStack.EMPTY);

			jar.setArcanaStack(arcanaStack, 0);
//			level.setBlockAndUpdate(pos, state.setValue(LEVEL, (int) Math.ceil(arcanaStack.amount() / 8f)));
		}
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if(!level.isClientSide() && level.getBlockEntity(pos) instanceof WardedJarBlockEntity jar && jar.getArcanaStack(0).amount() > 0 && jar.getArcanaStack(0).arcana() != ArcanusArcana.NIL.get()) {
			ItemStack stack = new ItemStack(ArcanusBlocks.WARDED_JAR.get());

			stack.applyComponents(jar.collectComponents());

			ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);

			itemEntity.setDefaultPickUpDelay();
			level.addFreshEntity(itemEntity);
		}

		return super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
		ItemStack stack = new ItemStack(ArcanusBlocks.WARDED_JAR.get());

		if(level.getBlockEntity(pos) instanceof WardedJarBlockEntity wardedJar)
			stack.set(ArcanusDataComponents.ARCANA_STACK.get(), wardedJar.getArcanaStack(0));

		return stack;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
			.setValue(FACING, context.getHorizontalDirection().getOpposite())
			.setValue(CONNECTED_TO_PIPE, context.getLevel().getBlockState(context.getClickedPos().above()).getBlock() instanceof ArcanaPipeBlock)
			.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	protected void updateIndirectNeighbourShapes(BlockState state, LevelAccessor level, BlockPos pos, @UpdateFlags int updateFlags, int updateLimit) {
		if(state.getValue(WATERLOGGED))
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

		super.updateIndirectNeighbourShapes(state, level, pos, updateFlags, updateLimit);
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		if(directionToNeighbour == Direction.UP)
			return state.setValue(CONNECTED_TO_PIPE, level.getBlockState(neighbourPos).getBlock() instanceof ArcanaPipeBlock);

		return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state) {
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
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(CONNECTED_TO_PIPE) ? Shapes.or(SHAPE, PIPE) : SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, LEVEL, CONNECTED_TO_PIPE, WATERLOGGED);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new WardedJarBlockEntity(pos, state);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return (level1, blockPos, blockState, blockEntity) -> {
			if(level.getGameTime() % 10 == 0 && blockEntity instanceof WardedJarBlockEntity wardedJar && !wardedJar.getArcanaStack(0).isEmpty())
				ArcanusHelper.findAndTransferArcana(level, blockPos, wardedJar.getArcanaStack(0), 1);
		};
	}

	@Override
	public Item createItem(Item.Properties properties) {
		return new BlockItem(this, properties) {
			@Override
			public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
				ArcanaStack data = itemStack.get(ArcanusDataComponents.ARCANA_STACK.get());

				if(data != null) {
					Arcana arcana = data.arcana();

					if(arcana != null && arcana != ArcanusArcana.NIL.get()) {
						Color color = arcana.color();
						HsbColor hsb = HsbColor.fromRgb(color.asIntARGB());

						if(hsb.brightness() < 0.4f)
							hsb = HsbColor.of(hsb.hue(), hsb.saturation(), 0.4f, hsb.alpha());

						builder.accept(Component.literal(String.format(data.amount() < 1.0D ? "%.0f" : "%.1f", data.amount()))
							.append(" ")
							.append(arcana.getName())
							.withColor(hsb.toRgba())
						);
					}
				}
			}
		};
	}
}
