package dev.cammiescorner.arcanus.common.screens;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class SpellBookScreenHandler extends AbstractContainerMenu {
	private final ItemStack stack;

	public SpellBookScreenHandler(int syncId, ItemStack stack) {
//		super(ArcanusScreenHandlers.SPELL_BOOK_SCREEN_HANDLER.get(), syncId);
		super(null, syncId);
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
