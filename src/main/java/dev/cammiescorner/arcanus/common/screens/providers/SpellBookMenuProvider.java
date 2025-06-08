package dev.cammiescorner.arcanus.common.screens.providers;

import dev.cammiescorner.arcanus.common.screens.SpellBookMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SpellBookMenuProvider implements MenuProvider {
	private final ItemStack stack;

	public SpellBookMenuProvider(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public Component getDisplayName() {
		return Component.empty();
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new SpellBookMenu(i, stack);
	}
}
