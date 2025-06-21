package dev.cammiescorner.arcanus.common.blocks;

import dev.cammiescorner.arcanus.common.blocks.entities.PedestalBlockEntity;
import dev.cammiescorner.arcanus.common.data.ArcanusItemTags;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
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
		Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.25, 0.9375),
		Shapes.box(0.25, 0.25, 0.25, 0.75, 0.75, 0.75),
		Shapes.box(0.125, 0.75, 0.125, 0.875, 1, 0.875)
	);

	public PedestalBlock() {
		super(Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).noOcclusion());
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if(level.getBlockEntity(pos) instanceof PedestalBlockEntity pedestal) {
			if(pedestal.isEmpty() && stack.is(ArcanusItemTags.CRAFTING_SPELLBINDING_SPELLBOOKS)) {
				pedestal.setItem(stack.split(1));

				return ItemInteractionResult.sidedSuccess(level.isClientSide());
			}

			if(!pedestal.isEmpty()) {
				player.getInventory().add(pedestal.getItem());
				pedestal.setItem(stack);
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		return super.useWithoutItem(state, level, pos, player, hitResult);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PedestalBlockEntity(pos, state);
	}
}
