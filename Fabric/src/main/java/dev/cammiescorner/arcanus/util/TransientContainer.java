package dev.cammiescorner.arcanus.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TransientContainer implements Container {
	private final NonNullList<ItemStack> items;
	private final AbstractContainerMenu menu;

	public TransientContainer(AbstractContainerMenu menu, int size) {
		this.menu = menu;
		this.items = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	@Override
	public int getContainerSize() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemStack : items)
			if(!itemStack.isEmpty())
				return false;

		return true;
	}

	@Override
	public ItemStack getItem(int slot) {
		return slot >= getContainerSize() ? ItemStack.EMPTY : items.get(slot);
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(items, slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		ItemStack itemStack = ContainerHelper.removeItem(items, slot, amount);

		if(!itemStack.isEmpty())
			menu.slotsChanged(this);

		return itemStack;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		items.set(slot, stack);
		menu.slotsChanged(this);
	}

	@Override
	public void setChanged() {
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		this.items.clear();
	}

	public List<ItemStack> getItems() {
		return List.copyOf(items);
	}
}
