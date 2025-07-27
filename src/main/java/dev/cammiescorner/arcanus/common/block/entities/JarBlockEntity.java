package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.client.util.JarRenderData;
import dev.cammiescorner.arcanus.common.block.JarBlock;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import dev.upcraft.sparkweave.api.color.Color;
import net.fabricmc.fabric.api.blockview.v2.RenderDataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class JarBlockEntity extends BlockEntity implements RenderDataBlockEntity {
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
		else {
			tag.putDouble("Mana", 0);
		}
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);

		if(tag.contains("ManaType") && !tag.getString("ManaType").isBlank()) {
			manaType = ManaType.getByName(tag.getString("ManaType"));
			mana = Math.clamp(tag.getDouble("Mana"), 0, 64);
		}
		else {
			manaType = null;
			mana = 0;
		}

		if(hasLevel())
			markUpdated();
	}

	@Override
	public Object getRenderData() {
		return new JarRenderData(manaType != null ? manaType.getColor() : Color.fromARGB(0xffffffff));
	}

	protected void markUpdated() {
		BlockState newState = getBlockState().setValue(JarBlock.LEVEL, (int) Math.ceil(getMana() / 8f));
		setChanged();
		getLevel().setBlockAndUpdate(getBlockPos(), newState);
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
		this.mana = Math.clamp(mana, 0, 64);
		markUpdated();
	}
}
