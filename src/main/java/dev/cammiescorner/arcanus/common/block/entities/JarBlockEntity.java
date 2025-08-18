package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.client.util.JarRenderData;
import dev.cammiescorner.arcanus.common.block.JarBlock;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import dev.upcraft.sparkweave.api.color.Color;
import net.fabricmc.fabric.api.blockview.v2.RenderDataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class JarBlockEntity extends BlockEntity implements RenderDataBlockEntity {
	private Arcana arcana = ArcanusArcana.NIL.get();
	private double arcanaAmount = 0;

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

		tag.putString("Arcana", ArcanusArcana.REGISTRY.getKey(arcana).toString());
		tag.putDouble("Amount", arcanaAmount);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);

		if(tag.contains("Arcana", Tag.TAG_STRING)) {
			arcana = ArcanusArcana.REGISTRY.get(ResourceLocation.parse(tag.getString("Arcana")));
			arcanaAmount = Math.clamp(tag.getDouble("Amount"), 0, 64);
		}
		else {
			arcana = ArcanusArcana.NIL.get();
			arcanaAmount = 0;
		}

		if(hasLevel())
			markUpdated();
	}

	@Override
	public Object getRenderData() {
		return new JarRenderData(arcana != null ? arcana.color() : Color.fromARGB(0xffffffff));
	}

	protected void markUpdated() {
		BlockState newState = getBlockState().setValue(JarBlock.LEVEL, (int) Math.ceil(getArcanaAmount() / 8f));
		setChanged();
		getLevel().setBlockAndUpdate(getBlockPos(), newState);
	}

	public Arcana getArcana() {
		return arcana;
	}

	public void setArcana(Arcana arcana) {
		this.arcana = arcana;
		markUpdated();
	}

	public double getArcanaAmount() {
		return arcanaAmount;
	}

	public void setArcanaAmount(double arcana) {
		this.arcanaAmount = Math.clamp(arcana, 0, 64);
		markUpdated();
	}
}
