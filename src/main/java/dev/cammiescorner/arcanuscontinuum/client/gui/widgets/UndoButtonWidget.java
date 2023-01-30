package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.SpellcraftScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class UndoButtonWidget extends ClickableWidget {
	protected final TooltipSupplier tooltipSupplier;
	private final MinecraftClient client = MinecraftClient.getInstance();

	public UndoButtonWidget(int x, int y) {
		super(x, y, 24, 16, Text.empty());
		this.tooltipSupplier = new TooltipSupplier() {
			@Override
			public void onTooltip(UndoButtonWidget spellComponentWidget, MatrixStack matrices, int mouseX, int mouseY) {
				if(client.currentScreen != null)
					client.currentScreen.renderTooltip(matrices, Text.literal("Undo"), mouseX, mouseY);
			}

			@Override
			public void supply(Consumer<Text> consumer) {
				consumer.accept(Text.literal("Undo"));
			}
		};
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, SpellcraftScreen.BOOK_TEXTURE);

		if(!isHoveredOrFocused()) {
			DrawableHelper.drawTexture(matrices, getX(), getY(), 0, 192, 24, 16, 256, 256);
		}
		else {
			DrawableHelper.drawTexture(matrices, getX(), getY(), 24, 192, 24, 16, 256, 256);
			renderTooltip(matrices, mouseX, mouseY);
		}
	}

	public void renderTooltip(MatrixStack matrices, int mouseX, int mouseY) {
		tooltipSupplier.onTooltip(this, matrices, mouseX, mouseY);
	}

	@Override
	protected void updateNarration(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
		tooltipSupplier.supply(text -> builder.put(NarrationPart.HINT, text));
	}

	public interface TooltipSupplier {
		void onTooltip(UndoButtonWidget spellComponentWidget, MatrixStack matrixStack, int i, int j);

		default void supply(Consumer<Text> consumer) {
		}
	}
}
