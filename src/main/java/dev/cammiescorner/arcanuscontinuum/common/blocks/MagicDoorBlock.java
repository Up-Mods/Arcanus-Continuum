package dev.cammiescorner.arcanuscontinuum.common.blocks;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicDoorBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class MagicDoorBlock extends DoorBlock implements BlockEntityProvider {
	public MagicDoorBlock() {
		super(QuiltBlockSettings.of(Material.WOOD).strength(2F, 3F).sounds(BlockSoundGroup.WOOD), BlockSetType.OAK);
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
		ItemStack stack = player.getStackInHand(hand);
		MagicDoorBlockEntity door = getBlockEntity(world, state, pos);

		if(stack.isOf(Items.NAME_TAG) && stack.hasCustomName() && door != null)
			if(door.getOwner().equals(player))
				door.setPassword(stack.getName().getString());
			else
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

		return world.getBlockEntity(pos, ArcanusBlockEntities.MAGIC_DOOR).orElse(null);
	}
}
