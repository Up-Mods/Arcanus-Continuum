package dev.cammiescorner.arcanus.common.menus;

import dev.cammiescorner.arcanus.api.spells.Spell;
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

import java.util.List;

// TODO there's a desync *somewhere* supposedly. items in the player's inventory get dropped when clicked, instead of picked up
public class SpellBookMenu extends AbstractContainerMenu {
	private final TransientContainer spellBookSlots = new TransientContainer(this, 8);
	private ItemStack stack;

	public SpellBookMenu(int containerId, Inventory playerInventory, ItemStack stack) {
		super(ArcanusMenus.SPELL_BOOK_MENU.get(), containerId);
		this.stack = stack;
		List<Spell> spells = stack.getOrDefault(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell()));

		for(int i = 0; i < spells.size(); i++) {
			Spell spell = spells.get(i);
			int radius = i % 2 == 0 ? 33 : 42;
			int x = (int) (Math.cos(Math.toRadians(45 * i - 90)) * radius) + 112;
			int y = (int) (Math.sin(Math.toRadians(45 * i - 90)) * radius) + 56;

			addSlot(new SpellScrollSlot(spellBookSlots, i, x, y));

			if(!spell.isEmpty()) {
				ItemStack itemStack = new ItemStack(ArcanusItems.SPELL_SCROLL.get());

				itemStack.set(ArcanusDataComponents.SPELL.get(), spell);
				spellBookSlots.setItem(i, itemStack);
			}
		}

		checkContainerSize(spellBookSlots, 8);
		spellBookSlots.startOpen(playerInventory.player);

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 42 + j * 18, 134 + i * 18));

		for(int i = 0; i < 9; i++)
			addSlot(new Slot(playerInventory, i, 42 + i * 18, 192));
	}

	@Override
	public void removed(Player player) {
		List<Spell> spells = stack.getOrDefault(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell()));

		for(int i = 0; i < spellBookSlots.getContainerSize(); i++) {
			ItemStack itemStack = spellBookSlots.getItem(i);
			spells.set(i, itemStack.getOrDefault(ArcanusDataComponents.SPELL.get(), new Spell()));
		}

		stack.set(ArcanusDataComponents.SPELL_LIST.get(), spells);
		super.removed(player);
	}

	@Override
	public boolean clickMenuButton(Player player, int id) {
		if(id == 0) {
			List<Spell> spells = stack.getOrDefault(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell()));

			for(int i = 0; i < spellBookSlots.getContainerSize(); i++) {
				ItemStack itemStack = spellBookSlots.getItem(i);
				spells.set(i, itemStack.getOrDefault(ArcanusDataComponents.SPELL.get(), new Spell()));
			}

			stack.set(ArcanusDataComponents.SPELL_LIST.get(), spells);
		}

		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);

		if(slot.hasItem()) {
			ItemStack itemStack2 = slot.getItem();
			itemStack = itemStack2.copy();

			if(index < 8) {
				if(!moveItemStackTo(itemStack2, 8, 44, true))
					return ItemStack.EMPTY;
			}
			else if(!moveItemStackTo(itemStack2, 0, 8, false)) {
				return ItemStack.EMPTY;
			}

			if(itemStack2.isEmpty())
				slot.setByPlayer(ItemStack.EMPTY);
			else
				slot.setChanged();

			if(itemStack2.getCount() == itemStack.getCount())
				return ItemStack.EMPTY;

			slot.onTake(player, itemStack2);
		}

		return itemStack;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	public TransientContainer spellBookInventory() {
		return spellBookSlots;
	}

	public class SpellScrollSlot extends Slot {
		public SpellScrollSlot(Container container, int slot, int x, int y) {
			super(container, slot, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return stack.is(ArcanusItems.SPELL_SCROLL.get());
		}
	}
}
