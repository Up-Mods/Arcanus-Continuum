package dev.cammiescorner.arcanus.common.blocks.entities;

import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PedestalBlockEntity extends BlockEntity implements Container {
	private ItemStack stack = ItemStack.EMPTY;

	public PedestalBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.PEDESTAL.get(), pos, blockState);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);

		if(!stack.isEmpty())
			tag.put("ItemStack", stack.save(registries));
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		stack = ItemStack.parseOptional(registries, tag.getCompound("ItemStack"));
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
	public int getContainerSize() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public ItemStack getItem(int slot) {
		return stack;
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		removeItemNoUpdate(0);
		markUpdated();
		return stack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return stack.split(1);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		this.stack = stack;
		markUpdated();
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		stack = ItemStack.EMPTY;
		markUpdated();
	}

	public ItemStack getItem() {
		return getItem(0);
	}

	public ItemStack removeItem() {
		return removeItem(0, 0);
	}

	public ItemStack removeItemNoUpdate() {
		return removeItemNoUpdate(0);
	}

	public void setItem(ItemStack stack) {
		setItem(0, stack);
	}

	private void markUpdated() {
		setChanged();
		getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
	}
}
