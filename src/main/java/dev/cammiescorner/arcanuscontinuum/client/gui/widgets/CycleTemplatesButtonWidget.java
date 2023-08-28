package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.util.WorkbenchMode;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CycleTemplatesButtonWidget extends PressableWidget {
	private final PressAction onPress;
	private static final Identifier TEXTURE = WorkbenchMode.CUSTOMIZE.getTexture();
	public final boolean isUp;

	public CycleTemplatesButtonWidget(int x, int y, boolean isUp, PressAction onPress) {
		super(x, y, 16, 16, Text.empty());
		this.isUp = isUp;
		this.onPress = onPress;
		this.setTooltip(Tooltip.create(Arcanus.translate("screen", "tooltip", isUp ? "cycle_up" : "cycle_down")));
	}

	@Override
	public void onPress() {
		onPress.onPress(this);
	}

	@Override
	public void drawWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, TEXTURE);

		int v = isUp ? 184 : 200;

		if(!isHoveredOrFocused()) {
			DrawableHelper.drawTexture(matrices, getX(), getY(), 24, v, width, height, 256, 256);
		}
		else {
			DrawableHelper.drawTexture(matrices, getX(), getY(), 40, v, width, height, 256, 256);
		}
	}

	@Override
	protected void updateNarration(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
	}

	public interface PressAction {
		void onPress(CycleTemplatesButtonWidget buttonWidget);
	}
}
