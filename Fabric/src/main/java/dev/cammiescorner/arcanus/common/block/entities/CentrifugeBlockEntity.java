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
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CentrifugeBlockEntity extends BlockEntity implements ArcanaContainer {
	private ArcanaStack input = ArcanaStack.EMPTY;
	private ArcanaStack output1 = ArcanaStack.EMPTY;
	private ArcanaStack output2 = ArcanaStack.EMPTY;

	public CentrifugeBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.CENTRIFUGE.get(), pos, blockState);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		CompoundTag tag = super.getUpdateTag(registries);

		tag.put("ArcanaInput", ArcanaStack.CODEC.encodeStart(NbtOps.INSTANCE, input).result().orElseThrow());
		tag.put("ArcanaOutput1", ArcanaStack.CODEC.encodeStart(NbtOps.INSTANCE, output1).result().orElseThrow());
		tag.put("ArcanaOutput2", ArcanaStack.CODEC.encodeStart(NbtOps.INSTANCE, output2).result().orElseThrow());

		return tag;
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);

		output.store("ArcanaInput", ArcanaStack.CODEC, input);
		output.store("ArcanaOutput1", ArcanaStack.CODEC, output1);
		output.store("ArcanaOutput2", ArcanaStack.CODEC, output2);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		this.input = input.read("ArcanaInput", ArcanaStack.CODEC).orElse(ArcanaStack.EMPTY);
		this.output1 = input.read("ArcanaOutput1", ArcanaStack.CODEC).orElse(ArcanaStack.EMPTY);
		this.output2 = input.read("ArcanaOutput2", ArcanaStack.CODEC).orElse(ArcanaStack.EMPTY);
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter components) {
		super.applyImplicitComponents(components);

		List<ArcanaStack> stacks = components.getOrDefault(ArcanusDataComponents.ARCANA_INVENTORY.get(), NonNullList.withSize(3, ArcanaStack.EMPTY));

		input = stacks.get(0);
		output1 = stacks.get(1);
		output2 = stacks.get(2);
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder components) {
		super.collectImplicitComponents(components);

		components.set(ArcanusDataComponents.ARCANA_INVENTORY.get(), List.of(input, output1, output2));
	}

	@Override
	public ArcanaStack getArcanaStack(int index) {
		if(index == 0)
			return input;
		if(index == 1)
			return output1;

		return output2;
	}

	@Override
	public void setArcanaStack(ArcanaStack arcanaStack, int index) {
		if(index == 0)
			this.input = arcanaStack;
		else if(index == 1)
			this.output1 = arcanaStack;
		else
			this.output2 = arcanaStack;

		setChanged();
	}

	@Override
	public void addArcanaStack(ArcanaStack arcanaStack) {
		if(this.input.isEmpty())
			setArcanaStack(arcanaStack, 0);
		else if(this.output1.isEmpty())
			setArcanaStack(arcanaStack, 1);
	}

	@Override
	public int indexOf(ArcanaStack arcanaStack) {
		if(input.sameArcana(arcanaStack))
			return 0;
		if(output1.sameArcana(arcanaStack))
			return 1;
		if(output2.sameArcana(arcanaStack))
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
		return input.isEmpty() && output1.isEmpty() && output2.isEmpty();
	}

	@Override
	public boolean isFull() {
		return input.amount() + output1.amount() + output2.amount() >= maximumArcana();
	}

	@Override
	public boolean contains(Arcana arcana) {
		return input.arcana() == arcana || output1.arcana() == arcana || output2.arcana() == arcana;
	}

	@Override
	public List<Direction> inputDirections() {
		return List.of(Direction.EAST, Direction.WEST);
	}

	@Override
	public List<Direction> outputDirections() {
		return List.of(Direction.DOWN);
	}
}
