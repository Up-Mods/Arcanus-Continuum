package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Locale;
import java.util.UUID;

public class MagicDoorBlockEntity extends BlockEntity {
	private UUID ownerId = Util.NIL_UUID;
	private String password = "please";

	public MagicDoorBlockEntity(BlockPos pos, BlockState state) {
		super(ArcanusBlockEntities.MAGIC_DOOR.get(), pos, state);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);

		tag.putUUID("OwnerId", ownerId);
		tag.putString("Password", password);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		ownerId = tag.getUUID("OwnerId");
		password = tag.getString("Password");
	}

	public LivingEntity getOwner() {
		if(level instanceof ServerLevel server && server.getEntity(ownerId) instanceof LivingEntity livingEntity)
			return livingEntity;

		return null;
	}

	public void setOwner(LivingEntity owner) {
		ownerId = owner.getUUID();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.toLowerCase(Locale.ROOT);
	}
}
