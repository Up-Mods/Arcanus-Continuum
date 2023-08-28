package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.util.WorkbenchMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ChangeModeButtonWidget extends PressableWidget {
	private final PressAction onPress;
	private final Identifier texture;

	public ChangeModeButtonWidget(int x, int y, WorkbenchMode mode, PressAction onPress) {
		super(x, y, 24, 16, Text.empty());
		this.onPress = onPress;
		this.texture = mode.getTexture();
		this.setTooltip(Tooltip.create(Arcanus.translate("screen", "tooltip", "change_screens")));
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
		}
	}

	@Override
	protected void updateNarration(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
	}

	public interface PressAction {
		void onPress(ChangeModeButtonWidget buttonWidget);
	}
}
