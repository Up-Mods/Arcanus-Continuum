package dev.cammiescorner.arcanus.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TexturedButtonWidget extends AbstractButton {
	private final ResourceLocation texture;
	private final PressAction onPress;
	private final int uOffset, vOffset;

	public TexturedButtonWidget(int x, int y, int width, int height, int uOffset, int vOffset, ResourceLocation texture, PressAction onPress) {
		super(x, y, width, height, Component.empty());
		this.texture = texture;
		this.onPress = onPress;
		this.uOffset = uOffset;
		this.vOffset = vOffset;
	}

	@Override
	protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		if(!isHoveredOrFocused())
			guiGraphics.blit(texture, getX(), getY(), uOffset, vOffset, width, height);
		else
			guiGraphics.blit(texture, getX(), getY(), uOffset + width, vOffset, width, height);
	}

	@Override
	public void onPress() {
		onPress.onPress(this);
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput builder) {
		defaultButtonNarrationText(builder);
	}

	public interface PressAction {
		void onPress(TexturedButtonWidget buttonWidget);
	}
}
