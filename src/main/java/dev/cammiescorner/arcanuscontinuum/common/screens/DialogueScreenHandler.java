package dev.cammiescorner.arcanuscontinuum.common.screens;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class DialogueScreenHandler extends ScreenHandler {
	public DialogueScreenHandler(int syncId) {
		super(ArcanusScreenHandlers.DIALOGUE_SCREEN_HANDLER, syncId);
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
