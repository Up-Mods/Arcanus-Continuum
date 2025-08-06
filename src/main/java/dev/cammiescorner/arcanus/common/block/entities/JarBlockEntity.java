package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
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
	private ArcanaType arcanaType;
	private double arcana = 0;

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

		if(arcanaType != null) {
			tag.putString("ManaType", arcanaType.getSerializedName());
			tag.putDouble("Mana", arcana);
		}
		else {
			tag.putDouble("Mana", 0);
		}
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);

		if(tag.contains("ManaType") && !tag.getString("ManaType").isBlank()) {
			arcanaType = ArcanaType.getByName(tag.getString("ManaType"));
			arcana = Math.clamp(tag.getDouble("Mana"), 0, 64);
		}
		else {
			arcanaType = null;
			arcana = 0;
		}

		if(hasLevel())
			markUpdated();
	}

	@Override
	public Object getRenderData() {
		return new JarRenderData(arcanaType != null ? arcanaType.getColor() : Color.fromARGB(0xffffffff));
	}

	protected void markUpdated() {
		BlockState newState = getBlockState().setValue(JarBlock.LEVEL, (int) Math.ceil(getMana() / 8f));
		setChanged();
		getLevel().setBlockAndUpdate(getBlockPos(), newState);
	}

	public ArcanaType getManaType() {
		return arcanaType;
	}

	public void setManaType(ArcanaType arcanaType) {
		this.arcanaType = arcanaType;
		markUpdated();
	}

	public double getMana() {
		return arcana;
	}

	public void setMana(double arcana) {
		this.arcana = Math.clamp(arcana, 0, 64);
		markUpdated();
	}
}
