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
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AthanorBlockEntity extends BlockEntity implements ArcanaContainer {
	private final NonNullList<ArcanaStack> inventory = NonNullList.create();

	public AthanorBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.ATHANOR.get(), pos, blockState);
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
		ListTag listTag = new ListTag();

		inventory.removeIf(ArcanaStack::isEmpty);

		for(ArcanaStack arcanaStack : inventory)
			listTag.add(ArcanaStack.CODEC.encodeStart(NbtOps.INSTANCE, arcanaStack).result().orElseThrow());

		tag.put("Arcana", listTag);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		ListTag listTag = tag.getList("Arcana", Tag.TAG_COMPOUND);

		inventory.clear();

		for(int i = 0; i < listTag.size(); i++) {
			CompoundTag compoundTag = listTag.getCompound(i);
			ArcanaStack arcanaStack = ArcanaStack.CODEC.parse(NbtOps.INSTANCE, compoundTag).result().orElseThrow();

			inventory.add(arcanaStack);
		}
	}

	@Override
	protected void applyImplicitComponents(DataComponentInput componentInput) {
		super.applyImplicitComponents(componentInput);

		inventory.clear();
		inventory.addAll(componentInput.getOrDefault(ArcanusDataComponents.ARCANA_INVENTORY.get(), NonNullList.of(ArcanaStack.EMPTY)));
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder components) {
		super.collectImplicitComponents(components);

		components.set(ArcanusDataComponents.ARCANA_INVENTORY.get(), inventory);
	}

	@Override
	public ArcanaStack getArcanaStack(int index) {
		return inventory.get(index);
	}

	@Override
	public void setArcanaStack(ArcanaStack arcanaStack, int index) {
		inventory.set(index, arcanaStack);
		setChanged();
	}

	@Override
	public void addArcanaStack(ArcanaStack arcanaStack) {
		inventory.add(arcanaStack);
		setChanged();
	}

	@Override
	public int indexOf(ArcanaStack arcanaStack) {
		for(int i = 0; i < inventory.size(); i++) {
			ArcanaStack stack = inventory.get(i);

			if(stack.sameArcana(arcanaStack))
				return i;
		}

		return -1;
	}

	@Override
	public int size() {
		return inventory.size();
	}

	@Override
	public double maximumArcana() {
		return 128;
	}

	@Override
	public boolean isEmpty() {
		return inventory.isEmpty();
	}

	@Override
	public boolean isFull() {
		double totalArcana = 0;

		for(ArcanaStack stack : inventory) {
			totalArcana += stack.amount();
		}

		return totalArcana >= maximumArcana();
	}

	@Override
	public boolean contains(Arcana arcana) {
		return inventory.stream().anyMatch(arcanaStack -> arcanaStack.arcana() == arcana);
	}

	@Override
	public List<Direction> inputDirections() {
		return List.of(Direction.DOWN);
	}

	@Override
	public List<Direction> outputDirections() {
		return List.of(Direction.UP);
	}
}
