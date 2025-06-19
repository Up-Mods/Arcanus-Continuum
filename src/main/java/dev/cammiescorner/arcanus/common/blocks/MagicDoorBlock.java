package dev.cammiescorner.arcanus.common.blocks;

import dev.cammiescorner.arcanus.common.blocks.entities.MagicDoorBlockEntity;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MagicDoorBlock extends DoorBlock implements EntityBlock, BlockItemProvider {
	public MagicDoorBlock() {
		super(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(2f, 3f).sound(SoundType.WOOD).pushReaction(PushReaction.IGNORE).noOcclusion());
	}

	@Override
	public void setOpen(@Nullable Entity entity, Level world, BlockState state, BlockPos pos, boolean open) {
		super.setOpen(entity, world, state, pos, open);

		if(open)
			world.scheduleTick(pos, this, 100);
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if(state.getValue(OPEN))
			setOpen(null, world, state, pos, false);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if(level.isClientSide)
			return ItemInteractionResult.SUCCESS;

		MagicDoorBlockEntity door = getBlockEntity(level, state, pos);
		LivingEntity owner = door.getOwner();

		if(owner != null && stack.is(Items.NAME_TAG) && stack.has(DataComponents.CUSTOM_NAME))
			if(owner.getUUID().equals(player.getUUID())) {
				String password = stack.getHoverName().getString();

				door.setPassword(password);
				player.displayClientMessage(Component.translatable("door.arcanus.password_set", password)
					.withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC), true);
			}
			else
				player.displayClientMessage(Component.translatable("door.arcanus.not_owner").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC), true);
		else
			player.displayClientMessage(Component.translatable("door.arcanus.say_magic_word").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC), true);
		return ItemInteractionResult.SUCCESS;
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(world, pos, state, placer, stack);
		MagicDoorBlockEntity door = getBlockEntity(world, state, pos);

		if(door != null) {
			door.setOwner(placer);

			if(stack.has(DataComponents.CUSTOM_NAME))
				door.setPassword(stack.getHoverName().getString());
		}
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {

	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		if(state.getValue(HALF) == DoubleBlockHalf.LOWER)
			return new MagicDoorBlockEntity(pos, state);

		return null;
	}

	public static MagicDoorBlockEntity getBlockEntity(Level world, BlockState state, BlockPos pos) {
		if(state.getValue(HALF) == DoubleBlockHalf.UPPER)
			pos = pos.below();

		return world.getBlockEntity(pos, ArcanusBlockEntities.MAGIC_DOOR.get()).orElse(null);
	}
}
