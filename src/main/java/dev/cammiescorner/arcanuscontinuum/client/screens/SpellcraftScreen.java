package dev.cammiescorner.arcanuscontinuum.client.screens;

import dev.cammiescorner.arcanuscontinuum.common.screens.SpellcraftScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

import java.util.List;

public class SpellcraftScreen extends HandledScreen<SpellcraftScreenHandler> {
	public SpellcraftScreen(SpellcraftScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

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
}
