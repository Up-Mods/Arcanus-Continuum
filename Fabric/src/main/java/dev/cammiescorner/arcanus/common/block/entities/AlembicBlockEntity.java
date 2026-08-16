package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.common.data_component.ArcanaStack;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.util.ArcanaContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlembicBlockEntity extends BlockEntity implements ArcanaContainer {
	private ArcanaStack arcanaStack = ArcanaStack.EMPTY;

	public AlembicBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.ALEMBIC.get(), pos, blockState);
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

		tag.put("Arcana", ArcanaStack.CODEC.encodeStart(NbtOps.INSTANCE, arcanaStack).result().orElseThrow());
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);

		if(tag.contains("Arcana", Tag.TAG_COMPOUND))
			arcanaStack = ArcanaStack.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("Arcana")).result().orElseThrow();
		else
			arcanaStack = ArcanaStack.EMPTY;
	}

	@Override
	protected void applyImplicitComponents(DataComponentInput componentInput) {
		super.applyImplicitComponents(componentInput);

		arcanaStack = componentInput.getOrDefault(ArcanusDataComponents.ARCANA_STACK.get(), ArcanaStack.EMPTY);
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder components) {
		super.collectImplicitComponents(components);

		components.set(ArcanusDataComponents.ARCANA_STACK.get(), arcanaStack);
	}

	@Override
	public ArcanaStack getArcanaStack(int index) {
		return arcanaStack;
	}

	@Override
	public void setArcanaStack(ArcanaStack arcanaStack, int index) {
		this.arcanaStack = arcanaStack;
		setChanged();
	}

	@Override
	public void addArcanaStack(ArcanaStack arcanaStack) {
		if(this.arcanaStack.isEmpty())
			setArcanaStack(arcanaStack, 0);
	}

	@Override
	public int indexOf(ArcanaStack arcanaStack) {
		return 0;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public double maximumArcana() {
		return 64;
	}

	@Override
	public boolean isEmpty() {
		return arcanaStack.isEmpty();
	}

	@Override
	public boolean isFull() {
		return arcanaStack.amount() >= maximumArcana();
	}

	@Override
	public boolean contains(Arcana arcana) {
		return this.arcanaStack.arcana() == arcana;
	}

	@Override
	public List<Direction> inputDirections() {
		return List.of(Direction.EAST, Direction.WEST, Direction.DOWN);
	}

	@Override
	public List<Direction> outputDirections() {
		return List.of(Direction.UP);
	}
}
