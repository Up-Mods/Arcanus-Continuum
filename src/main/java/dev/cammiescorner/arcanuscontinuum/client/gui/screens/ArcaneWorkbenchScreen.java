package dev.cammiescorner.arcanuscontinuum.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.gui.widgets.ChangeModeButtonWidget;
import dev.cammiescorner.arcanuscontinuum.client.gui.widgets.CycleTemplatesButtonWidget;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.screens.ArcaneWorkbenchScreenHandler;
import dev.cammiescorner.arcanuscontinuum.common.util.WorkbenchMode;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ArcaneWorkbenchScreen extends HandledScreen<ArcaneWorkbenchScreenHandler> {
	public ArcaneWorkbenchScreen(ArcaneWorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	protected void init() {
		super.init();
		x = (width - 176) / 2;
		y = (height - 166) / 2;

		if(handler.getMode() == WorkbenchMode.CUSTOMIZE) {
			addDrawableChild(new CycleTemplatesButtonWidget(x + 10, y + 13, true, this::cycleTemplate));
			addDrawableChild(new CycleTemplatesButtonWidget(x + 10, y + 57, false, this::cycleTemplate));
		}

		addDrawableChild(new ChangeModeButtonWidget(x + 148, y + 60, handler.getMode(), this::changeMode));
	}

	@Override
	public void clearAndInit() {
		super.clearAndInit();
	}

	private void cycleTemplate(CycleTemplatesButtonWidget widget) {
		client.interactionManager.clickButton(handler.syncId, widget.isUp ? 1 : 2);
	}

	private void changeMode(ChangeModeButtonWidget widget) {
		client.interactionManager.clickButton(handler.syncId, 0);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, getTexture());
		DrawableHelper.drawTexture(matrices, x, y, 0, 0, 176, 166, 256, 256);

		if(getScreenHandler().getMode() == WorkbenchMode.CUSTOMIZE) {
			if(getScreenHandler().getSlot(2).getStack().isEmpty())
				drawTexture(matrices, x + 95, y + 24, 176, 0, 16, 16);
			if(getScreenHandler().getSlot(3).getStack().isEmpty())
				drawTexture(matrices, x + 95, y + 46, 176, 0, 16, 16);
		}
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, getTexture());

		if(getScreenHandler().getMode() == WorkbenchMode.SPELLBINDING && getScreenHandler().getSlot(5).getStack().getItem() instanceof StaffItem) {
			float scale = 0.4F;
			matrices.push();
			matrices.scale(scale, scale, 1F);

			if(getScreenHandler().getSlot(2).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, Arcanus.getSpellPatternAsText(0), (int) (52 / scale), (int) (17 / scale), 0xffffff);
			if(getScreenHandler().getSlot(3).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, Arcanus.getSpellPatternAsText(1), (int) (72 / scale), (int) (17 / scale), 0xffffff);
			if(getScreenHandler().getSlot(6).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, Arcanus.getSpellPatternAsText(2), (int) (72 / scale), (int) (37 / scale), 0xffffff);
			if(getScreenHandler().getSlot(9).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, Arcanus.getSpellPatternAsText(3), (int) (72 / scale), (int) (57 / scale), 0xffffff);
			if(getScreenHandler().getSlot(8).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, Arcanus.getSpellPatternAsText(4), (int) (52 / scale), (int) (57 / scale), 0xffffff);
			if(getScreenHandler().getSlot(7).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, Arcanus.getSpellPatternAsText(5), (int) (32 / scale), (int) (57 / scale), 0xffffff);
			if(getScreenHandler().getSlot(4).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, Arcanus.getSpellPatternAsText(6), (int) (32 / scale), (int) (37 / scale), 0xffffff);
			if(getScreenHandler().getSlot(1).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, Arcanus.getSpellPatternAsText(7), (int) (32 / scale), (int) (17 / scale), 0xffffff);

			matrices.pop();
		}
		else if(getScreenHandler().getMode() == WorkbenchMode.CUSTOMIZE) {
			itemRenderer.renderInGui(getScreenHandler().getTemplate().getDefaultStack(), 10, 35);
		}

		drawMouseoverTooltip(matrices, mouseX - x, mouseY - y);
	}

	public Identifier getTexture() {
		return getScreenHandler().getMode().getTexture();
	}
}
