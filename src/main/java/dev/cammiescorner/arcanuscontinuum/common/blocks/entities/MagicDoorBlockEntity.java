package dev.cammiescorner.arcanuscontinuum.common.blocks.entities;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

import java.util.Locale;
import java.util.UUID;

public class MagicDoorBlockEntity extends BlockEntity {
	private UUID ownerId = Util.NIL_UUID;
	private String password = "please";

	public MagicDoorBlockEntity(BlockPos pos, BlockState state) {
		super(ArcanusBlockEntities.MAGIC_DOOR.get(), pos, state);
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
		this.password = password.toLowerCase(Locale.ROOT);
	}
}
