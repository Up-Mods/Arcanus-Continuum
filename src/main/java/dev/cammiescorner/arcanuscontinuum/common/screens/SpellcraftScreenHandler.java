package dev.cammiescorner.arcanuscontinuum.common.screens;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class SpellcraftScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private final ScreenHandlerContext context;
	private ItemStack stack;

	public SpellcraftScreenHandler(int syncId, Inventory inventory, ItemStack stack) {
		this(syncId, inventory, ScreenHandlerContext.EMPTY, stack);
	}

	public SpellcraftScreenHandler(int syncId, Inventory inventory, ScreenHandlerContext context, ItemStack stack) {
		super(ArcanusScreenHandlers.SPELLCRAFT_SCREEN_HANDLER, syncId);
		this.inventory = inventory;
		this.stack = stack;
		this.context = context;
	}

	@Override
	public boolean onButtonClick(PlayerEntity player, int id) {
		if(!player.canModifyBlocks())
			return false;

		ItemStack stack = inventory.removeStack(0);
		inventory.markDirty();

		if(!player.getInventory().insertStack(stack))
			player.dropItem(stack, false);

		return true;
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

	public void getSpellBook(ItemStack stack) {
		this.stack = stack;
	}

	public ScreenHandlerContext getContext() {
		return context;
	}
}
