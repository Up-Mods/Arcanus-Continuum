package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Locale;
import java.util.UUID;

public class MagicDoorBlockEntity extends BlockEntity {
	private UUID ownerId = Util.NIL_UUID;
	private String password = "please";

	public MagicDoorBlockEntity(BlockPos pos, BlockState state) {
		super(ArcanusBlockEntities.MAGIC_DOOR.get(), pos, state);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);

		output.store("OwnerId", UUIDUtil.CODEC, ownerId);
		output.putString("Password", password);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		ownerId = input.read("OwnerId", UUIDUtil.CODEC).orElse(Util.NIL_UUID);
		password = input.getString("Password").orElse("Please");
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
