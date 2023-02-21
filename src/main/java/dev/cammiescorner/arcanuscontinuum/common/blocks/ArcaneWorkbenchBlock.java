package dev.cammiescorner.arcanuscontinuum.common.blocks;

import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.ArcaneWorkbenchBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.List;

public class ArcaneWorkbenchBlock extends HorizontalFacingBlock implements BlockEntityProvider {
	private static final VoxelShape SHAPE = VoxelShapes.union(
			VoxelShapes.cuboid(0, 0.1875, 0, 1, 0.8125, 1),
			VoxelShapes.cuboid(0, 0, 0, 0.125, 0.1875, 0.125),
			VoxelShapes.cuboid(0.875, 0, 0, 1, 0.1875, 0.125),
			VoxelShapes.cuboid(0, 0, 0.875, 0.125, 0.1875, 1),
			VoxelShapes.cuboid(0.875, 0, 0.875, 1, 0.1875, 1)
	);

	public ArcaneWorkbenchBlock() {
		super(QuiltBlockSettings.of(Material.WOOD, DyeColor.RED).nonOpaque().ticksRandomly());
		setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if(!world.isClient && world.getBlockEntity(pos) instanceof ArcaneWorkbenchBlockEntity arcaneWorkbench)
			player.openHandledScreen(arcaneWorkbench);

		return ActionResult.SUCCESS;
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos blockPos, RandomGenerator random) {
		Direction direction = state.get(FACING);
		Vec3d d = Vec3d.of(blockPos);
		List<Vec3d> vec3ds = switch(direction) {
			case NORTH -> List.of(new Vec3d(0.0625, 1.125, 0.8125), new Vec3d(0.3125, 1.1875, 0.9375), new Vec3d(0.6875, 1.1875, 0.9375), new Vec3d(0.9375, 1.125, 0.8125));
			case SOUTH -> List.of(new Vec3d(0.0625, 1.125, 0.1875), new Vec3d(0.3125, 1.1875, 0.0625), new Vec3d(0.6875, 1.1875, 0.0625), new Vec3d(0.9375, 1.125, 0.1875));
			case WEST -> List.of(new Vec3d(0.8125, 1.125, 0.0625), new Vec3d(0.9375, 1.1875, 0.3125), new Vec3d(0.9375, 1.1875, 0.6875), new Vec3d(0.8125, 1.125, 0.9375));
			case EAST -> List.of(new Vec3d(0.1875, 1.125, 0.0625), new Vec3d(0.0625, 1.1875, 0.3125), new Vec3d(0.0625, 1.1875, 0.6875), new Vec3d(0.1875, 1.125, 0.9375));
			default -> List.of();
		};
		float f = random.nextFloat();

		for(Vec3d vec3d : vec3ds) {
			Vec3d pos = vec3d.add(d);

			if(f < 0.3F)
				world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);

			world.addParticle(ParticleTypes.SMALL_FLAME, pos.x, pos.y, pos.z, 0, 0, 0);
		}

		if(f < 0.17F)
			world.playSound(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ArcaneWorkbenchBlockEntity(pos, state);
	}
}
