package dev.cammiescorner.arcanus.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.gui.widgets.TexturedButtonWidget;
import dev.cammiescorner.arcanus.common.menu.SpellBookMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

public class SpellBookScreen extends AbstractContainerScreen<SpellBookMenu> {
	public static final ResourceLocation BOOK_TEXTURE = Arcanus.id("textures/gui/spell_book.png");

	public SpellBookScreen(SpellBookMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		this.imageWidth = 241;
		this.imageHeight = 216;
	}

	@Override
	protected void init() {
		super.init();
		leftPos = (width - 241) / 2;
		topPos = (height - 216) / 2;

		addRenderableWidget(new TexturedButtonWidget(leftPos + 112, topPos + 56, 16, 16, 0, 224, BOOK_TEXTURE, buttonWidget -> onClose()));
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		PoseStack poseStack = guiGraphics.pose();

		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		poseStack.pushPose();
		poseStack.translate(leftPos, topPos, 0f);
		guiGraphics.blit(BOOK_TEXTURE, 0, 0, 0, 0, 241, 216);
		poseStack.popPose();

		for(int i = 0; i < 8; i++) {
			if(menu.getSlot(i).getItem().isEmpty()) {
				int radius = i % 2 == 0 ? 33 : 42;
				int x = (int) (Math.cos(Math.toRadians(45 * i - 90)) * radius) + leftPos + 120;
				int y = (int) (Math.sin(Math.toRadians(45 * i - 90)) * radius) + topPos + 62;

				poseStack.pushPose();
				poseStack.translate(x, y, 0);
				poseStack.scale(0.4f, 0.4f, 1f);
				guiGraphics.drawCenteredString(font, Arcanus.getSpellPatternAsText(i), 0, 0, 0xaaaaaa);
				poseStack.popPose();
			}
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		FormattedCharSequence sequence = title.getVisualOrderText();
		guiGraphics.drawString(font, sequence, 120 - font.width(sequence) / 2, 8, 0x404040, false);
	}
}
