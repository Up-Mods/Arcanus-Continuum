package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.SpellcraftScreen;
import dev.cammiescorner.arcanuscontinuum.client.gui.util.UndoRedoStack;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class UndoRedoButtonWidget extends PressableWidget {
	private final PressAction onPress;
	private final UndoRedoStack undoRedoStack;
	private final boolean isUndo;

	public UndoRedoButtonWidget(int x, int y, boolean isUndo, UndoRedoStack stack, PressAction onPress) {
		super(x, y, 24, 16, Text.empty());
		this.isUndo = isUndo;
		this.onPress = onPress;
		this.undoRedoStack = stack;
		this.setTooltip(Tooltip.create(Arcanus.translate("screen", "tooltip", isUndo ? "undo" : "redo")));
	}

	@Override
	public void onPress() {
		onPress.onPress(this);
	}

	@Override
	public void drawWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, SpellcraftScreen.BOOK_TEXTURE);

		int v = isUndo ? 192 : 208;
		active = isUndo ? undoRedoStack.canUndo() : undoRedoStack.canRedo();

		if(!active)
			DrawableHelper.drawTexture(matrices, getX(), getY(), 48, v, width, height, 256, 256);
		else if(!isHoveredOrFocused())
			DrawableHelper.drawTexture(matrices, getX(), getY(), 0, v, width, height, 256, 256);
		else
			DrawableHelper.drawTexture(matrices, getX(), getY(), 24, v, width, height, 256, 256);
	}

	@Override
	protected void updateNarration(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
	}

	public interface PressAction {
		void onPress(UndoRedoButtonWidget buttonWidget);
	}
}
