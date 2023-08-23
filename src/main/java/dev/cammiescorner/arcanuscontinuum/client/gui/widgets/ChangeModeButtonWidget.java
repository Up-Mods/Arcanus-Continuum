package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.util.WorkbenchMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ChangeModeButtonWidget extends PressableWidget {
	private final TooltipSupplier tooltipSupplier;
	private final PressAction onPress;
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final Identifier texture;

	public ChangeModeButtonWidget(int x, int y, WorkbenchMode mode, PressAction onPress) {
		super(x, y, 24, 16, Text.empty());
		this.onPress = onPress;
		this.texture = mode.getTexture();
		this.tooltipSupplier = new TooltipSupplier() {
			@Override
			public void onTooltip(ChangeModeButtonWidget spellComponentWidget, GuiGraphics gui, int mouseX, int mouseY) {
				if(client.currentScreen != null)
					gui.drawTooltip(client.textRenderer, Arcanus.translate("screen", "tooltip", "change_screens"), mouseX, mouseY);
			}

			@Override
			public void supply(Consumer<Text> consumer) {
				consumer.accept(Arcanus.translate("screen", "tooltip", "change_screens"));
			}
		};
	}

	@Override
	public void onPress() {
		onPress.onPress(this);
	}

	@Override
	public void drawWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

		if(!isHoveredOrFocused()) {
			gui.drawTexture(texture, getX(), getY(), 24, 168, width, height, 256, 256);
		}
		else {
			gui.drawTexture(texture, getX(), getY(), 48, 168, width, height, 256, 256);

			RenderSystem.disableDepthTest();
			renderTooltip(gui, mouseX, mouseY);
			RenderSystem.enableDepthTest();
		}
	}

	public void renderTooltip(GuiGraphics gui, int mouseX, int mouseY) {
		tooltipSupplier.onTooltip(this, gui, mouseX, mouseY);
	}

	@Override
	protected void updateNarration(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
		tooltipSupplier.supply(text -> builder.put(NarrationPart.HINT, text));
	}

	public interface TooltipSupplier {
		void onTooltip(ChangeModeButtonWidget spellComponentWidget, GuiGraphics gui, int i, int j);

		default void supply(Consumer<Text> consumer) {
		}
	}

	public interface PressAction {
		void onPress(ChangeModeButtonWidget buttonWidget);
	}
}
