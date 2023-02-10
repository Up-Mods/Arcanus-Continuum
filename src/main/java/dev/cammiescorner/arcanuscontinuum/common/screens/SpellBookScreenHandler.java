package dev.cammiescorner.arcanuscontinuum.common.screens;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class SpellBookScreenHandler extends ScreenHandler {
	private final ItemStack stack;

	public SpellBookScreenHandler(int syncId, Inventory inventory, ItemStack stack) {
		super(ArcanusScreenHandlers.SPELL_BOOK_SCREEN_HANDLER, syncId);
		this.stack = stack;
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

	public ItemStack getSpellBook() {
		return stack;
	}
}
