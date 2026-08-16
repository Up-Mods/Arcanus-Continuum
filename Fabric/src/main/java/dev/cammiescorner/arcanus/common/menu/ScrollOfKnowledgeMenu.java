package dev.cammiescorner.arcanus.common.menu;

import dev.cammiescorner.arcanus.common.registry.ArcanusMenus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ScrollOfKnowledgeMenu extends AbstractContainerMenu {
	public ScrollOfKnowledgeMenu(int containerId, ItemStack stack) {
		super(ArcanusMenus.SCROLL_OF_KNOWLEDGE_MENU.get(), containerId);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}
}
