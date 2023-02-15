package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.DialogueScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class DialogueOptionWidget extends PressableWidget {
	private static final Identifier TEXTURE = DialogueScreen.BACKGROUND;
	private final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
	private final PressAction onPress;

	public DialogueOptionWidget(int x, int y, Text text, PressAction onPress) {
		super(x, y, 250, 0, text);
		this.onPress = onPress;
		int size = Math.min(textRenderer.wrapLines(text, 244).size(), 3);
		this.height = size * 18 + (2 * (size - 1));
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int v = getYImage(isHoveredOrFocused());
		drawTexture(matrices, getX(), getY(), 0, v, width, height / 2);
		drawTexture(matrices, getX(), getY() + height / 2, 0, v + 58 - height / 2, width, height / 2);

		Formatting textColour = isHoveredOrFocused() ? Formatting.WHITE : Formatting.GRAY;
		List<OrderedText> lines = textRenderer.wrapLines(getMessage().copy().formatted(textColour), 238);

		for(int i = 0; i < lines.size(); i++) {
			OrderedText text = lines.get(i);
			int fontHeight = textRenderer.fontHeight;
			int padding = 1;
			int lineHeight = fontHeight + padding;
			int textHeight = lines.size() * lineHeight;
			float startY = padding + getY() + (height - textHeight) / 2F;

			textRenderer.drawWithShadow(matrices, text, getX() + 6, startY + i * lineHeight, 0xffffff);
		}
	}

	@Override
	protected int getYImage(boolean hovered) {
		return hovered ? 122 : 64;
	}

	@Override
	public void onPress() {
		onPress.onPress(this);
	}

	@Override
	protected void updateNarration(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
		builder.put(NarrationPart.HINT, getMessage());
	}

	public interface PressAction {
		void onPress(DialogueOptionWidget buttonWidget);
	}
}
