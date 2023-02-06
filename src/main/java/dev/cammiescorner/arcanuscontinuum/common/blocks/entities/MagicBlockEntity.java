package dev.cammiescorner.arcanuscontinuum.common.blocks.entities;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MagicBlockEntity extends BlockEntity {
	private int colour = 0x68e1ff;

	public MagicBlockEntity(BlockPos pos, BlockState state) {
		super(ArcanusBlockEntities.MAGIC_BLOCK, pos, state);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		colour = nbt.getInt("Colour");
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putInt("Colour", colour);
	}

	@Override
	public NbtCompound toSyncedNbt() {
		NbtCompound tag = super.toSyncedNbt();
		writeNbt(tag);
		return tag;
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.of(this);
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}
}
