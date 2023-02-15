package dev.cammiescorner.arcanuscontinuum.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.screens.DialogueScreenHandler;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class DialogueScreen extends HandledScreen<DialogueScreenHandler> {
	public static final Identifier BACKGROUND = Arcanus.id("textures/gui/dialogue_box.png");
	private final Text wizardName;

	public DialogueScreen(DialogueScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, Text.empty());
		wizardName = MutableText.create(title.asComponent()).formatted(Formatting.UNDERLINE);
	}

	@Override
	protected void init() {
		super.init();
		x = (width - 256) / 2;
		y = (height - 62) - 4;
		playerInventoryTitleY = -10000;
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, BACKGROUND);
		DrawableHelper.drawTexture(matrices, x, y, 0, 0, 256, 62, 256, 256);
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		super.drawForeground(matrices, mouseX, mouseY);
		drawTextWithShadow(matrices, textRenderer, wizardName, 3, -10, 0xffffff);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, BACKGROUND);

		if(true) {
			// TODO player dialogue options + scroll bar
//			DrawableHelper.drawTexture(matrices, 0, 0, 0, isPointWithinBounds(3, 3, 250, 17, mouseX, mouseY) ? 85 : 62, 256, 23, 256, 256);
//			DrawableHelper.drawTexture(matrices, 0, 19, 0, isPointWithinBounds(3, 22, 250, 17, mouseX, mouseY) ? 85 : 62, 256, 23, 256, 256);
//			DrawableHelper.drawTexture(matrices, 0, 38, 0, isPointWithinBounds(3, 41, 250, 17, mouseX, mouseY) ? 85 : 62, 256, 23, 256, 256);
//
//			drawTextWithShadow(matrices, textRenderer, Text.literal("I would love to learn more!"), 6, 7, 0xffffff);
//			drawTextWithShadow(matrices, textRenderer, Text.literal("Arcane? Like, magic and stuff?"), 6, 26, 0xffffff);
//			drawTextWithShadow(matrices, textRenderer, Text.literal("Guybrush... will you be my valentine!"), 6, 45, 0xffffff);
		}
		else {
			// TODO wizard dialogue
//			List<OrderedText> lines = textRenderer.wrapLines(Text.literal("I am Guybrush Threepwood, Grand Wizard of the realm! Do you truly wish to learn more about the arcane?"), 244);
//
//			for(int i = 0; i < lines.size(); i++) {
//				OrderedText text = lines.get(i);
//				textRenderer.drawWithShadow(matrices, text, 6, 7 + (10 * i), 0xffffff);
//			}
//
//			RenderSystem.setShader(GameRenderer::getPositionTexShader);
//			RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
//			RenderSystem.setShaderTexture(0, BACKGROUND);
//			DrawableHelper.drawTexture(matrices, 248, client.world.getTime() / 10 % 2 == 0 ? 55 : 54, 0, 112, 5, 4, 256, 256);
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
