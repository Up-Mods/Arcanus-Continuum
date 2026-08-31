package dev.cammiescorner.arcanus.client.gui.screens;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.util.TranslationKeys;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.concurrent.CompletableFuture;

public class SupporterSavingScreen extends Screen {
	private final CompletableFuture<Void> saveFuture;
	private final Runnable onComplete;

	public SupporterSavingScreen(CompletableFuture<Void> saveFuture, Runnable onComplete) {
		super(Component.empty());
		this.saveFuture = saveFuture;
		this.onComplete = onComplete;
	}

	@Override
	protected void init() {
		super.init();

		saveFuture.exceptionally(throwable -> {
			Arcanus.LOGGER.error("Unable to save supporter data", throwable);
			return null;
		}).thenRunAsync(onComplete, minecraft);
	}

	@Override
	public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		this.renderBlurredBackground(partialTick);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		super.render(graphics, mouseX, mouseY, delta);

		graphics.drawCenteredString(font, Component.translatable(TranslationKeys.CONFIG_SUPPORTER_SETTINGS_SAVING), width / 2, height / 2, 0xffffffff);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return false;
	}
}
