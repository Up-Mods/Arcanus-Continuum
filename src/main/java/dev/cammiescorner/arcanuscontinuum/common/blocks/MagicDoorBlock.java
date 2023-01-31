package dev.cammiescorner.arcanuscontinuum.common.blocks;

import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicDoorBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class MagicDoorBlock extends DoorBlock implements BlockEntityProvider {
	public MagicDoorBlock() {
		super(QuiltBlockSettings.of(Material.WOOD).strength(2F, 3F).sounds(BlockSoundGroup.WOOD), SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundEvents.BLOCK_WOODEN_DOOR_OPEN);
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
		return ActionResult.PASS;
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
