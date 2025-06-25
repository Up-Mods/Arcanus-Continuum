package dev.cammiescorner.arcanus.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.gui.widgets.TexturedButtonWidget;
import dev.cammiescorner.arcanus.common.menus.SpellBookMenu;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

public class SpellBookScreen extends AbstractContainerScreen<SpellBookMenu> {
	public static final ResourceLocation BOOK_TEXTURE = Arcanus.id("textures/gui/spell_book.png");

	public SpellBookScreen(SpellBookMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, Component.translatable(ArcanusItems.SPELL_BOOK.get().getDescriptionId()));
	}

	@Override
	protected void init() {
		super.init();
		leftPos = (width - 241) / 2;
		topPos = (height - 216) / 2;

		addRenderableWidget(new TexturedButtonWidget(leftPos + 112, topPos + 56, 16, 16, 0, 224, BOOK_TEXTURE, buttonWidget -> {
			minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 0);
			onClose();
		}));
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		PoseStack poseStack = guiGraphics.pose();

		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		poseStack.pushPose();
		poseStack.translate(leftPos, topPos, 0f);
		guiGraphics.blit(BOOK_TEXTURE, 0, 0, 0, 0, 241, 216);
		poseStack.popPose();
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		FormattedCharSequence sequence = title.getVisualOrderText();
		guiGraphics.drawString(font, sequence, 120 - font.width(sequence) / 2, 8, 4210752, false);
	}
}
