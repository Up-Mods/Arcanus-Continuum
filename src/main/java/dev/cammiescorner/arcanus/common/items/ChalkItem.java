package dev.cammiescorner.arcanus.common.items;

import dev.cammiescorner.arcanus.api.spells.mana.ManaType;
import dev.cammiescorner.arcanus.common.blocks.ChalkBlock;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import static dev.cammiescorner.arcanus.common.blocks.ChalkBlock.RUNE;

public class ChalkItem extends BlockItem {
	public ChalkItem(Block block) {
		super(block, new Properties().stacksTo(1).durability(64));
	}

	@Override
	public InteractionResult place(BlockPlaceContext context) {
		BlockPlaceContext blockPlaceContext = updatePlacementContext(context);

		if(!context.canPlace())
			return InteractionResult.FAIL;
		if(blockPlaceContext == null)
			return InteractionResult.FAIL;

		BlockState placementState = getPlacementState(blockPlaceContext);

		if(placementState == null)
			return InteractionResult.FAIL;
		if(!this.placeBlock(blockPlaceContext, placementState))
			return InteractionResult.FAIL;

		BlockPos blockPos = blockPlaceContext.getClickedPos();
		Level level = blockPlaceContext.getLevel();
		Player player = blockPlaceContext.getPlayer();
		ItemStack itemStack = blockPlaceContext.getItemInHand();
		BlockState state = level.getBlockState(blockPos);

		if(state.is(placementState.getBlock())) {
			state = updateBlockStateFromTag(blockPos, level, itemStack, state);

			updateCustomBlockEntityTag(blockPos, level, player, itemStack, state);
			updateBlockEntityComponents(level, blockPos, itemStack);

			state.getBlock().setPlacedBy(level, blockPos, state, player, itemStack);

			if(player instanceof ServerPlayer)
				CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
		}

		EquipmentSlot slot = context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
		SoundType soundType = state.getSoundType();
		level.playSound(player, blockPos, this.getPlaceSound(state), SoundSource.BLOCKS, (soundType.getVolume() + 1f) / 2f, soundType.getPitch() * 0.8f);
		level.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(player, state));

		itemStack.hurtAndBreak(1, player, slot);

		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);

		if(state.getBlock() instanceof ChalkBlock) {
			ManaType manaType = state.getValue(RUNE);
			int index = manaType.ordinal();

			level.setBlockAndUpdate(pos, state.setValue(RUNE, ManaType.values()[index < ManaType.values().length - 1 ? index + 1 : 0]));

			return InteractionResult.sidedSuccess(level.isClientSide());
		}

		return super.useOn(context);
	}
}
