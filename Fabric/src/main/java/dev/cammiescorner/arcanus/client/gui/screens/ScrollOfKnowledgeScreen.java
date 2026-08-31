package dev.cammiescorner.arcanus.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.menu.ScrollOfKnowledgeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

// TODO finish screen rendering once Rites are done
public class ScrollOfKnowledgeScreen extends AbstractContainerScreen<ScrollOfKnowledgeMenu> {
	public static final ResourceLocation SCROLL_TEXTURE = Arcanus.id("textures/gui/spell_scroll.png");

	public ScrollOfKnowledgeScreen(ScrollOfKnowledgeMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		this.imageWidth = 320;
		this.imageHeight = 180;
	}

	@Override
	protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		gui.blit(SCROLL_TEXTURE, leftPos - 32, topPos, 0, 0, 320, 180, 320, 256);
	}
}
