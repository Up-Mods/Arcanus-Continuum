package dev.cammiescorner.arcanuscontinuum.common.screens;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;

public class SpellcraftScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private final ScreenHandlerContext context;
	private final ItemStack stack;
	private final BlockPos pos;

	public SpellcraftScreenHandler(int syncId, Inventory inventory, BlockPos pos, ItemStack stack) {
		this(syncId, inventory, ScreenHandlerContext.EMPTY, pos, stack);
	}

	public SpellcraftScreenHandler(int syncId, Inventory inventory, ScreenHandlerContext context, BlockPos pos, ItemStack stack) {
		super(ArcanusScreenHandlers.SPELLCRAFT_SCREEN_HANDLER.get(), syncId);
		this.inventory = inventory;
		this.stack = stack;
		this.pos = pos;
		this.context = context;
	}

	@Override
	public boolean onButtonClick(PlayerEntity player, int id) {
		if(!player.canModifyBlocks())
			return false;

		ItemStack itemStack = inventory.removeStack(0);
		inventory.markDirty();

		if(!player.getInventory().insertStack(itemStack))
			player.dropItem(itemStack, false);

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

	public BlockPos getPos() {
		return pos;
	}

	public ItemStack getSpellBook() {
		return stack;
	}

	public ScreenHandlerContext getContext() {
		return context;
	}
}
