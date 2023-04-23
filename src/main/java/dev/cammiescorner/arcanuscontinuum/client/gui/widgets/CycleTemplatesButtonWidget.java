package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.util.WorkbenchMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class CycleTemplatesButtonWidget extends PressableWidget {
	private final TooltipSupplier tooltipSupplier;
	private final PressAction onPress;
	private final MinecraftClient client = MinecraftClient.getInstance();
	private static final Identifier TEXTURE = WorkbenchMode.CUSTOMIZE.getTexture();
	public final boolean isUp;

	public CycleTemplatesButtonWidget(int x, int y, boolean isUp, PressAction onPress) {
		super(x, y, 16, 16, Text.empty());
		this.isUp = isUp;
		this.onPress = onPress;
		this.tooltipSupplier = new TooltipSupplier() {
			final String text = isUp ? "cycle_up" : "cycle_down";

			@Override
			public void onTooltip(CycleTemplatesButtonWidget spellComponentWidget, MatrixStack matrices, int mouseX, int mouseY) {
				if(client.currentScreen != null)
					client.currentScreen.renderTooltip(matrices, Arcanus.translate("screen", "tooltip", text), mouseX, mouseY);
			}

			@Override
			public void supply(Consumer<Text> consumer) {
				consumer.accept(Arcanus.translate("screen", "tooltip", text));
			}
		};
	}

	@Override
	public void onPress() {
		onPress.onPress(this);
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, TEXTURE);

		int v = isUp ? 184 : 200;

		if(!isHoveredOrFocused()) {
			DrawableHelper.drawTexture(matrices, x, y, 24, v, width, height, 256, 256);
		}
		else {
			DrawableHelper.drawTexture(matrices, x, y, 40, v, width, height, 256, 256);

			RenderSystem.disableDepthTest();
			renderTooltip(matrices, mouseX, mouseY);
			RenderSystem.enableDepthTest();
		}
	}

	public void renderTooltip(MatrixStack matrices, int mouseX, int mouseY) {
		tooltipSupplier.onTooltip(this, matrices, mouseX, mouseY);
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
		tooltipSupplier.supply(text -> builder.put(NarrationPart.HINT, text));
	}

	public interface TooltipSupplier {
		void onTooltip(CycleTemplatesButtonWidget spellComponentWidget, MatrixStack matrixStack, int i, int j);

		default void supply(Consumer<Text> consumer) {
		}
	}

	public interface PressAction {
		void onPress(CycleTemplatesButtonWidget buttonWidget);
	}
}
