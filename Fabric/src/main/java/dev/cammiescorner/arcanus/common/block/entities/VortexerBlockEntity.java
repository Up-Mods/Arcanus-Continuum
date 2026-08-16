package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.common.data_component.ArcanaStack;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.util.ArcanaContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VortexerBlockEntity extends BlockEntity implements ArcanaContainer {
	private ArcanaStack input1 = ArcanaStack.EMPTY;
	private ArcanaStack input2 = ArcanaStack.EMPTY;
	private ArcanaStack output = ArcanaStack.EMPTY;

	public VortexerBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.VORTEXER.get(), pos, blockState);
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

		tag.put("ArcanaInput1", ArcanaStack.CODEC.encodeStart(NbtOps.INSTANCE, input1).result().orElseThrow());
		tag.put("ArcanaInput2", ArcanaStack.CODEC.encodeStart(NbtOps.INSTANCE, input2).result().orElseThrow());
		tag.put("ArcanaOutput", ArcanaStack.CODEC.encodeStart(NbtOps.INSTANCE, output).result().orElseThrow());
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);

		input1 = ArcanaStack.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("ArcanaInput1")).result().orElseThrow();
		input2 = ArcanaStack.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("ArcanaInput2")).result().orElseThrow();
		output = ArcanaStack.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("ArcanaOutput")).result().orElseThrow();
	}

	@Override
	protected void applyImplicitComponents(DataComponentInput componentInput) {
		super.applyImplicitComponents(componentInput);

		List<ArcanaStack> stacks = componentInput.getOrDefault(ArcanusDataComponents.ARCANA_INVENTORY.get(), NonNullList.withSize(3, ArcanaStack.EMPTY));

		input1 = stacks.get(0);
		input2 = stacks.get(1);
		output = stacks.get(2);
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder components) {
		super.collectImplicitComponents(components);

		components.set(ArcanusDataComponents.ARCANA_INVENTORY.get(), List.of(input1, input2, output));
	}

	@Override
	public ArcanaStack getArcanaStack(int index) {
		if(index == 0)
			return input1;
		if(index == 1)
			return input2;

		return output;
	}

	@Override
	public void setArcanaStack(ArcanaStack arcanaStack, int index) {
		if(index == 0)
			this.input1 = arcanaStack;
		else if(index == 1)
			this.input2 = arcanaStack;
		else
			this.output = arcanaStack;

		setChanged();
	}

	@Override
	public void addArcanaStack(ArcanaStack arcanaStack) {
		if(this.input1.isEmpty())
			setArcanaStack(arcanaStack, 0);
		else if(this.input2.isEmpty())
			setArcanaStack(arcanaStack, 1);
	}

	@Override
	public int indexOf(ArcanaStack arcanaStack) {
		if(input1.sameArcana(arcanaStack))
			return 0;
		if(input2.sameArcana(arcanaStack))
			return 1;
		if(output.sameArcana(arcanaStack))
			return 2;

		return -1;
	}

	@Override
	public int size() {
		return 3;
	}

	@Override
	public double maximumArcana() {
		return 64;
	}

	@Override
	public boolean isEmpty() {
		return input1.isEmpty() && input2.isEmpty() && output.isEmpty();
	}

	@Override
	public boolean isFull() {
		return input1.amount() + input2.amount() + output.amount() >= maximumArcana();
	}

	@Override
	public boolean contains(Arcana arcana) {
		return input1.arcana() == arcana || input2.arcana() == arcana || output.arcana() == arcana;
	}

	@Override
	public List<Direction> inputDirections() {
		return List.of(Direction.UP);
	}

	@Override
	public List<Direction> outputDirections() {
		return List.of(Direction.EAST, Direction.WEST);
	}
}
