package dev.cammiescorner.arcanuscontinuum.common.blocks.entities;

import dev.cammiescorner.arcanuscontinuum.common.blocks.MagicDoorBlock;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class MagicDoorBlockEntity extends BlockEntity {
	private UUID ownerId = Util.NIL_UUID;
	private String password = "please";
	public int timer = 0;

	public MagicDoorBlockEntity(BlockPos pos, BlockState state) {
		super(ArcanusBlockEntities.MAGIC_DOOR, pos, state);
	}

	public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T blockEntity) {
		if(blockEntity instanceof MagicDoorBlockEntity door && state.getBlock() instanceof MagicDoorBlock doorBlock) {
			if(door.timer > 0)
				door.timer--;
			else
				doorBlock.setOpen(null, world, state, pos, false);
		}
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);

		ownerId = nbt.getUuid("OwnerId");
		password = nbt.getString("Password");
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);

		nbt.putUuid("OwnerId", ownerId);
		nbt.putString("Password", password);
	}

	public LivingEntity getOwner() {
		if(world instanceof ServerWorld server && server.getEntity(ownerId) instanceof LivingEntity livingEntity)
			return livingEntity;

		return null;
	}

	public void setOwner(LivingEntity owner) {
		ownerId = owner.getUuid();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
