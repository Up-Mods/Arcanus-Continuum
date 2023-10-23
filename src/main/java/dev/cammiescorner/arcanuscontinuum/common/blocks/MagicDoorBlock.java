package dev.cammiescorner.arcanuscontinuum.common.blocks;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicDoorBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlockEntities;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.Locale;

public class MagicDoorBlock extends DoorBlock implements BlockEntityProvider, BlockItemProvider {
	public MagicDoorBlock() {
		super(QuiltBlockSettings.create().strength(2F, 3F).sounds(BlockSoundGroup.WOOD), BlockSetType.OAK);
	}

	@Override
	public Item createItem() {
		return new BlockItem(this, new QuiltItemSettings());
	}

	@Override
	public void setOpen(@Nullable Entity entity, World world, BlockState state, BlockPos pos, boolean open) {
		super.setOpen(entity, world, state, pos, open);

		if(open)
			world.scheduleBlockTick(pos, this, 100);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
		if(state.get(OPEN))
			setOpen(null, world, state, pos, false);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) return ActionResult.SUCCESS;

		ItemStack stack = player.getStackInHand(hand);
		MagicDoorBlockEntity door = getBlockEntity(world, state, pos);
		LivingEntity owner = door.getOwner();

		if(owner != null && stack.isOf(Items.NAME_TAG) && stack.hasCustomName())
			if(owner.getUuid().compareTo(player.getUuid()) == 0) {
				String password = stack.getName().getString();

				door.setPassword(password);
				player.sendMessage(Text.translatable("door.arcanuscontinuum.password_set", password)
					.formatted(Formatting.GOLD, Formatting.ITALIC), true);
			} else
				player.sendMessage(Arcanus.translate("door", "not_owner").formatted(Formatting.GRAY, Formatting.ITALIC), true);
		else
			player.sendMessage(Arcanus.translate("door", "say_magic_word").formatted(Formatting.GRAY, Formatting.ITALIC), true);
		return ActionResult.SUCCESS;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.onPlaced(world, pos, state, placer, stack);
		MagicDoorBlockEntity door = getBlockEntity(world, state, pos);

		if(door != null) {
			door.setOwner(placer);

			if(stack.hasCustomName())
				door.setPassword(stack.getName().getString());
		}
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {

	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		if(state.get(HALF) == DoubleBlockHalf.LOWER)
			return new MagicDoorBlockEntity(pos, state);

		return null;
	}

	public static MagicDoorBlockEntity getBlockEntity(World world, BlockState state, BlockPos pos) {
		if(state.get(HALF) == DoubleBlockHalf.UPPER)
			pos = pos.down();

		return world.getBlockEntity(pos, ArcanusBlockEntities.MAGIC_DOOR.get()).orElse(null);
	}
}
