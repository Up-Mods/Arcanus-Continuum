package dev.cammiescorner.arcanuscontinuum.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.screens.SpellcraftScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class SpellcraftScreen extends HandledScreen<SpellcraftScreenHandler> {
	private static final Identifier BOOK_TEXTURE = Arcanus.id("textures/gui/spell_book.png");
	private static final Identifier PANEL_TEXTURE = Arcanus.id("textures/gui/spell_crafting.png");

	public SpellcraftScreen(SpellcraftScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, Text.empty());
	}

	@Override
	protected void init() {
		super.init();
		x = (width - 256) / 2;
		y = (height - 256) / 2;
		playerInventoryTitleY = -10000;
		addCloseButton();
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
		DrawableHelper.drawTexture(matrices, x, y, 0, 0, 256, 180, 256, 256);

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, PANEL_TEXTURE);
		DrawableHelper.drawTexture(matrices, x - 62, y + 1, 0, 0, 380, 178, 384, 256);
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, PANEL_TEXTURE);
		DrawableHelper.drawTexture(matrices, -58, 5, 0, 184, 12, 22, 384, 256);
		DrawableHelper.drawTexture(matrices, 302, 5, 0, 184, 12, 22, 384, 256);

		super.drawForeground(matrices, mouseX, mouseY);
	}

	@Override
	public List<ClickableWidget> getButtons() {
		return null;
	}

	@Override
	public ItemRenderer getItemRenderer() {
		return getClient().getItemRenderer();
	}

	@Override
	public TextRenderer getTextRenderer() {
		return getClient().textRenderer;
	}

	@Override
	public MinecraftClient getClient() {
		return MinecraftClient.getInstance();
	}

	protected void addCloseButton() {
		addDrawableChild(new ButtonWidget(width / 2 - 100, 208, 98, 20, ScreenTexts.DONE, (button) -> closeScreen()));
		addDrawableChild(new ButtonWidget(width / 2 + 2, 208, 98, 20, Text.translatable("lectern.take_book"), (button) -> client.interactionManager.clickButton(handler.syncId, 0)));
	}
}
