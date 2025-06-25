package dev.cammiescorner.arcanus.common.menus;

import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.cammiescorner.arcanus.common.registry.ArcanusMenus;
import dev.cammiescorner.arcanus.common.util.TransientContainer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SpellBookMenu extends AbstractContainerMenu {
	private final TransientContainer spellBookSlots = new TransientContainer(this, 8);

	public SpellBookMenu(int containerId, Inventory playerInventory, ItemStack stack) {
		super(ArcanusMenus.SPELL_BOOK_MENU.get(), containerId);
		List<Spell> spells = stack.getOrDefault(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell()));

		for(int i = 0; i < spells.size(); i++) {
			Spell spell = spells.get(i);

			if(!spell.isEmpty()) {
				ItemStack itemStack = new ItemStack(ArcanusItems.SPELL_SCROLL.get());

				itemStack.set(ArcanusDataComponents.SPELL.get(), spell);
				spellBookSlots.setItem(i, itemStack);
			}
		}

		// TODO place all 8 slots please
		addSlot(new Slot(spellBookSlots, 0, 0, 0));
		addSlot(new Slot(spellBookSlots, 1, 0, 0));
		addSlot(new Slot(spellBookSlots, 2, 0, 0));
		addSlot(new Slot(spellBookSlots, 3, 0, 0));
		addSlot(new Slot(spellBookSlots, 4, 0, 0));
		addSlot(new Slot(spellBookSlots, 5, 0, 0));
		addSlot(new Slot(spellBookSlots, 6, 0, 0));
		addSlot(new Slot(spellBookSlots, 7, 0, 0));

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for(int i = 0; i < 9; i++)
			addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);

		if(slot.hasItem()) {
			ItemStack itemStack2 = slot.getItem();
			itemStack = itemStack2.copy();

			if(index < 8) {
				if(!moveItemStackTo(itemStack2, 8, 45, true))
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
}
