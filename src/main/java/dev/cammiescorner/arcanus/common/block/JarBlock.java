package dev.cammiescorner.arcanus.common.block;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.common.block.entities.JarBlockEntity;
import dev.cammiescorner.arcanus.common.data_component.ArcanaStorage;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
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

import java.util.List;

public class JarBlock extends Block implements BlockItemProvider, EntityBlock, SimpleWaterloggedBlock {
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
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty CONNECTED_TO_PIPE = BooleanProperty.create("connected_to_pipe");
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 8);

	public JarBlock() {
		super(Properties.of().noOcclusion());
		registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(CONNECTED_TO_PIPE, false).setValue(WATERLOGGED, false).setValue(LEVEL, 0));
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if(player.getAttributeValue(Attributes.SCALE) <= 0.25 && player.getItemInHand(hand).isEmpty()) {
			Vec3 vec3 = pos.getBottomCenter();

			if(INSIDE.bounds().inflate(0.001).move(pos).contains(hitResult.getLocation())) {
				player.teleportTo(vec3.x, vec3.y + 1, vec3.z);
				return ItemInteractionResult.sidedSuccess(level.isClientSide());
			}

			if(player.isCrouching()) {
				player.teleportTo(vec3.x, vec3.y + 0.0625, vec3.z);
				return ItemInteractionResult.sidedSuccess(level.isClientSide());
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(level, pos, state, placer, stack);

		if(!level.isClientSide() && level.getBlockEntity(pos) instanceof JarBlockEntity jar) {
			ArcanaStorage storage = stack.getOrDefault(ArcanusDataComponents.ARCANA_STORAGE.get(), new ArcanaStorage(ArcanusArcana.NIL.get(), 0));

			jar.setArcana(storage.arcana());
			jar.setArcanaAmount(storage.amount());
		}
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if(!level.isClientSide() && level.getBlockEntity(pos) instanceof JarBlockEntity jar && jar.getArcanaAmount() > 0 && jar.getArcana() != ArcanusArcana.NIL.get()) {
			ItemStack stack = new ItemStack(ArcanusBlocks.JAR.get());

			stack.applyComponents(jar.collectComponents());

			ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);

			itemEntity.setDefaultPickUpDelay();
			level.addFreshEntity(itemEntity);
		}

		return super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		ArcanaStorage data = stack.get(ArcanusDataComponents.ARCANA_STORAGE.get());

		if(data != null) {
			MutableComponent component = Component.empty();
			Arcana arcana = data.arcana();

			if(arcana != null && arcana != ArcanusArcana.NIL.get()) {
				component.append(String.format("%.0f", data.amount()));
				component.append(" ");
				component.append(Component.translatable(arcana.translationKey()));

				tooltipComponents.add(component.withColor(arcana.color().asIntARGB()));
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
			.setValue(FACING, context.getHorizontalDirection().getOpposite())
			.setValue(CONNECTED_TO_PIPE, context.getLevel().getBlockState(context.getClickedPos().above()).getBlock() instanceof ArcanaPipeBlock)
			.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if(state.getValue(WATERLOGGED))
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

		if(direction == Direction.UP)
			return state.setValue(CONNECTED_TO_PIPE, level.getBlockState(neighborPos).getBlock() instanceof ArcanaPipeBlock);

		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
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
		return new JarBlockEntity(pos, state);
	}
}
