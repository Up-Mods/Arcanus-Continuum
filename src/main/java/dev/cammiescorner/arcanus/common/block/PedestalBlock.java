package dev.cammiescorner.arcanus.common.block;

import dev.cammiescorner.arcanus.common.block.entities.PedestalBlockEntity;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends Block implements EntityBlock, BlockItemProvider {
	private static final VoxelShape SHAPE = Shapes.or(
		Shapes.box(0.0625, 0,    0.0625, 0.9375, 0.25, 0.9375),
		Shapes.box(0.25,   0.25, 0.25,   0.75,   0.75, 0.75),
		Shapes.box(0.125,  0.75, 0.125,  0.875,  1,    0.875)
	);

	public PedestalBlock() {
		super(Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).noOcclusion());
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if(level.getBlockEntity(pos) instanceof PedestalBlockEntity pedestal && pedestal.stillValid(player)) {
			ItemStack itemStack = player.isCreative() ? stack.copyWithCount(1) : stack.split(1);

			if(pedestal.isEmpty() && !itemStack.isEmpty()) {
				pedestal.setItem(itemStack);

				return ItemInteractionResult.sidedSuccess(level.isClientSide());
			}

			if(!pedestal.isEmpty()) {
				ItemStack pedestalStack = pedestal.getItem().copy();
				ItemEntity itemEntity;

				level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2f, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1f) * 2f);
				pedestal.removeItem();

				if(!itemStack.isEmpty())
					pedestal.setItem(itemStack);

				if(!player.getInventory().add(pedestalStack)) {
					itemEntity = player.drop(pedestalStack, false);

					if(itemEntity != null) {
						itemEntity.setNoPickUpDelay();
						itemEntity.setTarget(player.getUUID());
					}
				}

				return ItemInteractionResult.sidedSuccess(level.isClientSide());
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		Containers.dropContentsOnDestroy(state, newState, level, pos);
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PedestalBlockEntity(pos, state);
	}
}
