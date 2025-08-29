package dev.cammiescorner.arcanus.common.block;

import dev.cammiescorner.arcanus.common.block.entities.JarBlockEntity;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class JarBlock extends Block implements BlockItemProvider, EntityBlock {
	private static final VoxelShape SHAPE = Shapes.or(
		Shapes.box(0.1875,  0,      0.1875,  0.8125,  0.0625, 0.8125), // bottom
		Shapes.box(0.1875,  0.0625, 0.1875,  0.8125,  0.6875, 0.25),   // front
		Shapes.box(0.1875,  0.0625, 0.1875,  0.25,    0.6875, 0.8125), // right side
		Shapes.box(0.75,    0.0625, 0.1875,  0.8125,  0.6875, 0.8125), // left side
		Shapes.box(0.1875,  0.0625, 0.75,    0.8125,  0.6875, 0.8125), // back
		Shapes.box(0.1875,  0.6875, 0.1875,  0.8125,  0.75,   0.8125), // top
		Shapes.box(0.28125, 0.75,   0.28125, 0.71875, 0.875,  0.71875) // lid
	);
	private static final VoxelShape INSIDE = Shapes.box(0.25, 0.0625, 0.25, 0.75, 0.6875, 0.75);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 8);

	public JarBlock() {
		super(Properties.of().noOcclusion());
		registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(LEVEL, 0));
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
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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
		return SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, LEVEL);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new JarBlockEntity(pos, state);
	}
}
