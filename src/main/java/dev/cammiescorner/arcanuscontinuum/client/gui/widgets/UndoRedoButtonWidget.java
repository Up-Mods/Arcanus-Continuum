package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
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

public class UndoRedoButtonWidget extends ClickableWidget {
	protected final TooltipSupplier tooltipSupplier;
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final boolean isUndo;

	public UndoRedoButtonWidget(int x, int y, boolean isUndo) {
		super(x, y, 24, 16, Text.empty());
		this.isUndo = isUndo;
		this.tooltipSupplier = new TooltipSupplier() {
			final String text = isUndo ? "undo" : "redo";

			@Override
			public void onTooltip(UndoRedoButtonWidget spellComponentWidget, MatrixStack matrices, int mouseX, int mouseY) {
				if(client.currentScreen != null)
					client.currentScreen.renderTooltip(matrices, Text.translatable("screen." + Arcanus.MOD_ID + ".tooltip." + text), mouseX, mouseY);
			}

			@Override
			public void supply(Consumer<Text> consumer) {
				consumer.accept(Text.translatable("screen." + Arcanus.MOD_ID + ".tooltip." + text));
			}
		};
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, SpellcraftScreen.BOOK_TEXTURE);

		int v = isUndo ? 192 : 208;

		if(!isHoveredOrFocused()) {
			DrawableHelper.drawTexture(matrices, getX(), getY(), 0, v, 24, 16, 256, 256);
		}
		else {
			DrawableHelper.drawTexture(matrices, getX(), getY(), 24, v, 24, 16, 256, 256);

			RenderSystem.disableDepthTest();
			renderTooltip(matrices, mouseX, mouseY);
			RenderSystem.enableDepthTest();
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
		void onTooltip(UndoRedoButtonWidget spellComponentWidget, MatrixStack matrixStack, int i, int j);

		default void supply(Consumer<Text> consumer) {
		}
	}
}
