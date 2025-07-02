package dev.cammiescorner.arcanus.common.menu;

import com.google.common.base.Preconditions;
import dev.cammiescorner.arcanus.common.data_component.SpellBookComponent;
import dev.cammiescorner.arcanus.common.item.SpellBookItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.cammiescorner.arcanus.common.registry.ArcanusMenus;
import dev.cammiescorner.arcanus.common.util.TransientContainer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SpellBookMenu extends AbstractContainerMenu {
	private static final int SLOT_COUNT = SpellBookItem.SLOT_COUNT;
	private final TransientContainer spellBookSlots = new TransientContainer(this, SLOT_COUNT);
	private final ItemStack book;

	public SpellBookMenu(int containerId, Inventory playerInventory, ItemStack book) {
		super(ArcanusMenus.SPELL_BOOK_MENU.get(), containerId);
		this.book = book;
		var spells = book.getOrDefault(ArcanusDataComponents.SPELL_BOOK.get(), SpellBookComponent.empty());
		Preconditions.checkArgument(spells.spellScrolls().size() >= SLOT_COUNT, "Spell pouch had invalid data!");

		// spell slots
		for(int i = 0; i < SLOT_COUNT; i++) {
			int radius = i % 2 == 0 ? 33 : 42;
			int x = (int) (Math.cos(Math.toRadians(45 * i - 90)) * radius) + 112;
			int y = (int) (Math.sin(Math.toRadians(45 * i - 90)) * radius) + 56;

			spellBookSlots.setItem(i, spells.spellScrolls().get(i));

			addSlot(new SpellScrollSlot(spellBookSlots, i, x, y));
		}

		this.createInventorySlots(playerInventory);
	}

	@Override
	public void removed(Player player) {
		saveSpellsToStack();
		super.removed(player);
	}

	@Override
	public boolean clickMenuButton(Player player, int id) {
		if(id == 0) {
			saveSpellsToStack();
			return true;
		}

		return false;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		Slot slot = slots.get(index);

		if(slot.hasItem()) {
			ItemStack toMove = slot.getItem();
			ItemStack remainder = toMove.copy();

			// spellbook -> player inv
			if(index < SLOT_COUNT) {
				if(!moveItemStackTo(toMove, 8, 44, true))
					return ItemStack.EMPTY;
			}
			// player inv -> spellbook
			else if(!moveItemStackTo(toMove, 0, 8, false)) {
				return ItemStack.EMPTY;
			}

			if(toMove.isEmpty())
				slot.setByPlayer(ItemStack.EMPTY);
			else
				slot.setChanged();

			if(toMove.getCount() == remainder.getCount())
				return ItemStack.EMPTY;

			slot.onTake(player, toMove);
		}

		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	private void saveSpellsToStack() {
		SpellBookComponent spells = book.getOrDefault(ArcanusDataComponents.SPELL_BOOK.get(), SpellBookComponent.empty());
		NonNullList<ItemStack> items = NonNullList.withSize(SpellBookItem.SLOT_COUNT, ItemStack.EMPTY);

		for(int i = 0; i < Math.min(spellBookSlots.getContainerSize(), SpellBookItem.SLOT_COUNT); i++)
			items.set(i, spellBookSlots.getItem(i));

		book.set(ArcanusDataComponents.SPELL_BOOK.get(), spells.withSpells(items));
	}

	private void createInventorySlots(Inventory playerInventory) {
		// main inventory
		for(int row = 0; row < 3; row++)
			for(int col = 0; col < 9; col++)
				addSlot(new Slot(playerInventory, col + row * 9 + 9, 42 + col * 18, 134 + row * 18));

		// hotbar
		for(int col = 0; col < 9; col++)
			addSlot(new Slot(playerInventory, col, 42 + col * 18, 192));
	}

	public static class SpellScrollSlot extends Slot {
		public SpellScrollSlot(Container container, int slot, int x, int y) {
			super(container, slot, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return SpellBookMenu.isValidSpellScroll(stack);
		}
	}

	public static boolean isValidSpellScroll(ItemStack stack) {
		return stack.is(ArcanusItems.SPELL_SCROLL.get());
	}
}
