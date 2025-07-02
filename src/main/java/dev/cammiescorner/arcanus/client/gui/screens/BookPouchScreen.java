package dev.cammiescorner.arcanus.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.menu.BookPouchMenu;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BookPouchScreen extends AbstractContainerScreen<BookPouchMenu> {
	public static final ResourceLocation POUCH_TEXTURE = Arcanus.id("textures/gui/book_pouch.png");

	public BookPouchScreen(BookPouchMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
	}

	@Override
	protected void init() {
		super.init();
		leftPos = (width - 176) / 2;
		topPos = (height - 166) / 2;
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		PoseStack poseStack = guiGraphics.pose();

		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		poseStack.pushPose();
		poseStack.translate(leftPos, topPos, 0f);
		guiGraphics.blit(POUCH_TEXTURE, 0, 0, 0, 0, 176, 166);
		poseStack.popPose();
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		int index = getMenu().getPouch().getOrDefault(ArcanusDataComponents.BOOK_POUCH_INDEX.get(), 0);

		super.render(guiGraphics, mouseX, mouseY, partialTick);
		guiGraphics.blit(POUCH_TEXTURE, leftPos + 13 + index * 18, topPos + 31, 0, 168, 24, 24);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}
}
