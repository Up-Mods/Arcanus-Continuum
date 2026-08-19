package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.client.util.ColorRenderData;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import dev.upcraft.sparkweave.api.color.Color;
import net.fabricmc.fabric.api.blockgetter.v2.RenderDataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class ManaBeanBlockEntity extends BlockEntity implements RenderDataBlockEntity {
	private Arcana arcana = ArcanusArcana.NIL.get();

	public ManaBeanBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.MANA_BEAN.get(), pos, blockState);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		CompoundTag tag = super.getUpdateTag(registries);
		tag.putString("Arcana", ArcanusArcana.REGISTRY.getKey(arcana).toString());

		return tag;
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);

		output.putString("Arcana", ArcanusArcana.REGISTRY.getKey(arcana).toString());
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		var optional = input.getString("Arcana");

		if(optional.isPresent())
			arcana = ArcanusArcana.REGISTRY.getValue(Identifier.parse(optional.get()));
		else
			arcana = ArcanusArcana.NIL.get();

		if(hasLevel())
			markUpdated();
	}

	@Override
	public Object getRenderData() {
		return new ColorRenderData(arcana != null ? arcana.color() : Color.fromARGB(0xffffffff));
	}

	protected void markUpdated() {
		setChanged();
		getLevel().setBlockAndUpdate(getBlockPos(), getBlockState());
	}

	public Arcana getArcana() {
		return arcana;
	}

	public void setArcana(Arcana arcana) {
		this.arcana = arcana;
		markUpdated();
	}
}
