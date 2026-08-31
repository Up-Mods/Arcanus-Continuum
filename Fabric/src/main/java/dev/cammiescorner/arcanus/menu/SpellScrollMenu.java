package dev.cammiescorner.arcanus.menu;

import dev.cammiescorner.arcanus.registry.ArcanusMenus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class SpellScrollMenu extends AbstractContainerMenu {
	private final ItemStack stack;

	public SpellScrollMenu(int syncId, ItemStack stack) {
		super(ArcanusMenus.SPELL_SCROLL_MENU.get(), syncId);
		this.stack = stack;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int fromIndex) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	public ItemStack getSpellBook() {
		return stack;
	}
}
