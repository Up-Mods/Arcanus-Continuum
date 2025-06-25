package dev.cammiescorner.arcanus.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanus.client.gui.widgets.ChangeModeButtonWidget;
import dev.cammiescorner.arcanus.client.gui.widgets.CycleTemplatesButtonWidget;
import dev.cammiescorner.arcanus.common.menus.ArcaneWorkbenchMenu;
import dev.cammiescorner.arcanus.common.util.WorkbenchMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ArcaneWorkbenchScreen extends AbstractContainerScreen<ArcaneWorkbenchMenu> {
	public ArcaneWorkbenchScreen(ArcaneWorkbenchMenu handler, Inventory inventory, Component title) {
		super(handler, inventory, title);
	}

	@Override
	protected void init() {
		super.init();
		leftPos = (width - 176) / 2;
		topPos = (height - 166) / 2;

		addRenderableWidget(new CycleTemplatesButtonWidget(leftPos + 10, topPos + 13, true, this::cycleTemplate));
		addRenderableWidget(new CycleTemplatesButtonWidget(leftPos + 10, topPos + 57, false, this::cycleTemplate));
	}

	@Override
	public void rebuildWidgets() {
		super.rebuildWidgets();
	}

	private void cycleTemplate(CycleTemplatesButtonWidget widget) {
		if(minecraft != null && minecraft.gameMode != null)
			minecraft.gameMode.handleInventoryButtonClick(menu.containerId, widget.isUp ? 1 : 2);
	}

	private void changeMode(ChangeModeButtonWidget widget) {
		if(minecraft != null && minecraft.gameMode != null)
			minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 0);
	}

	@Override
	protected void renderBg(GuiGraphics gui, float delta, int mouseX, int mouseY) {
		this.renderBackground(gui, mouseX, mouseY, delta);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		gui.blit(getTexture(), leftPos, topPos, 0, 0, 176, 166, 256, 256);

		if(getMenu().getMode() == WorkbenchMode.CUSTOMIZE) {
			if(getMenu().getSlot(2).getItem().isEmpty())
				gui.blit(getTexture(), leftPos + 95, topPos + 24, 176, 0, 16, 16);
			if(getMenu().getSlot(3).getItem().isEmpty())
				gui.blit(getTexture(), leftPos + 95, topPos + 46, 176, 0, 16, 16);
		}
	}

	@Override
	protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
		gui.renderItem(getMenu().getTemplate().getDefaultInstance(), 10, 35);

		renderTooltip(gui, mouseX - leftPos, mouseY - topPos);
	}

	public ResourceLocation getTexture() {
		return getMenu().getMode().getTexture();
	}
}
