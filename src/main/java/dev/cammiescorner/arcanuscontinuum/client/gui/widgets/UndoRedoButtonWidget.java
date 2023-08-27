package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.SpellcraftScreen;
import dev.cammiescorner.arcanuscontinuum.client.gui.util.UndoRedoStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class UndoRedoButtonWidget extends PressableWidget {
	private final TooltipSupplier tooltipSupplier;
	private final PressAction onPress;
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final UndoRedoStack undoRedoStack;
	private final boolean isUndo;

	public UndoRedoButtonWidget(int x, int y, boolean isUndo, UndoRedoStack stack, PressAction onPress) {
		super(x, y, 24, 16, Text.empty());
		this.isUndo = isUndo;
		this.onPress = onPress;
		this.undoRedoStack = stack;
		this.tooltipSupplier = new TooltipSupplier() {
			final String text = isUndo ? "undo" : "redo";

			@Override
			public void onTooltip(UndoRedoButtonWidget spellComponentWidget, GuiGraphics gui, int mouseX, int mouseY) {
				if(client.currentScreen != null)
					gui.drawTooltip(client.textRenderer, Arcanus.translate("screen", "tooltip", text), mouseX, mouseY);
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
	public void drawWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

		int v = isUndo ? 192 : 208;
		active = isUndo ? undoRedoStack.canUndo() : undoRedoStack.canRedo();

		if(!active)
			gui.drawTexture(SpellcraftScreen.BOOK_TEXTURE, getX(), getY(), 48, v, width, height, 256, 256);
		else if(!isHoveredOrFocused())
			gui.drawTexture(SpellcraftScreen.BOOK_TEXTURE, getX(), getY(), 0, v, width, height, 256, 256);
		else
			gui.drawTexture(SpellcraftScreen.BOOK_TEXTURE, getX(), getY(), 24, v, width, height, 256, 256);

		if(isHoveredOrFocused()) {
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
		void onTooltip(UndoRedoButtonWidget spellComponentWidget, GuiGraphics gui, int i, int j);

		default void supply(Consumer<Text> consumer) {
		}
	}

	public interface PressAction {
		void onPress(UndoRedoButtonWidget buttonWidget);
	}
}
