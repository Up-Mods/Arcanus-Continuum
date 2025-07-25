package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class JarBlockEntity extends BlockEntity {
	private ManaType manaType;
	private double mana = 0;

	public JarBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.JAR.get(), pos, blockState);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		CompoundTag tag = super.getUpdateTag(registries);
		saveAdditional(tag, registries);

		return tag;
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);

		if(manaType != null) {
			tag.putString("ManaType", manaType.getSerializedName());
			tag.putDouble("Mana", mana);
		}
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);

		if(tag.contains("ManaType")) {
			manaType = ManaType.getByName(tag.getString("ManaType"));
			mana = tag.getDouble("Mana");
		}
	}

	protected void markUpdated() {
		setChanged();
		getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
	}

	public ManaType getManaType() {
		return manaType;
	}

	public void setManaType(ManaType manaType) {
		this.manaType = manaType;
		markUpdated();
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
		markUpdated();
	}
}
