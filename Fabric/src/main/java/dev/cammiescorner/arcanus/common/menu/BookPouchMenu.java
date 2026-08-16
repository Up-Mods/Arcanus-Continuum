package dev.cammiescorner.arcanus.common.menu;

import com.google.common.base.Preconditions;
import dev.cammiescorner.arcanus.common.data_component.BookPouchComponent;
import dev.cammiescorner.arcanus.common.item.BookPouchItem;
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

public class BookPouchMenu extends AbstractContainerMenu {
	private static final int SLOT_COUNT = BookPouchItem.SLOT_COUNT;
	private final TransientContainer bookPouchSlots = new TransientContainer(this, SLOT_COUNT);
	private final ItemStack pouch;

	public BookPouchMenu(int containerId, Inventory playerInventory, ItemStack pouch) {
		super(ArcanusMenus.BOOK_POUCH_MENU.get(), containerId);
		this.pouch = pouch;

		BookPouchComponent spellBooks = pouch.getOrDefault(ArcanusDataComponents.BOOK_POUCH.get(), BookPouchComponent.EMPTY);
		Preconditions.checkArgument(spellBooks.spellBooks().size() >= SLOT_COUNT, "Spell pouch had invalid data!");

		// spell book slots
		for(int i = 0; i < SLOT_COUNT; i++) {
			bookPouchSlots.setItem(i, spellBooks.spellBooks().get(i));

			addSlot(new SpellBookSlot(bookPouchSlots, i, 17 + i * 18, 35));
		}

		createInventorySlots(playerInventory);
	}

	@Override
	public void removed(Player player) {
		saveSpellBooksToStack();
		super.removed(player);
	}

	@Override
	public boolean clickMenuButton(Player player, int id) {
		if(id == 0) {
			saveSpellBooksToStack();
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

	public ItemStack getPouch() {
		return pouch;
	}

	private void saveSpellBooksToStack() {
		BookPouchComponent spellBooks = pouch.getOrDefault(ArcanusDataComponents.BOOK_POUCH.get(), BookPouchComponent.EMPTY);
		NonNullList<ItemStack> items = NonNullList.withSize(SpellBookItem.SLOT_COUNT, ItemStack.EMPTY);

		for(int i = 0; i < Math.min(bookPouchSlots.getContainerSize(), SpellBookItem.SLOT_COUNT); i++)
			items.set(i, bookPouchSlots.getItem(i));

		pouch.set(ArcanusDataComponents.BOOK_POUCH.get(), spellBooks.withSpellBooks(items));
	}

	private void createInventorySlots(Inventory playerInventory) {
		// main inventory
		for(int row = 0; row < 3; row++)
			for(int col = 0; col < 9; col++)
				addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

		// hotbar
		for(int col = 0; col < 9; col++)
			addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
	}

	public static class SpellBookSlot extends Slot {
		public SpellBookSlot(Container container, int slot, int x, int y) {
			super(container, slot, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return BookPouchMenu.isValidSpellBook(stack);
		}
	}

	public static boolean isValidSpellBook(ItemStack stack) {
		return stack.is(ArcanusItems.SPELL_BOOK.get());
	}
}
