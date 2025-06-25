package dev.cammiescorner.arcanus.client.gui.screens;

import dev.cammiescorner.arcanus.common.menus.SpellBookMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class SpellBookScreen  extends AbstractContainerScreen<SpellBookMenu> {
	public SpellBookScreen(SpellBookMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

	}
}
