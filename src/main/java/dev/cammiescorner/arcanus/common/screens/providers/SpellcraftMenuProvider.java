package dev.cammiescorner.arcanus.common.screens.providers;

import dev.cammiescorner.arcanus.common.screens.SpellcraftMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SpellcraftMenuProvider implements MenuProvider {
	private final Level level;
	private final BlockPos pos;
	private final Container container;

	public SpellcraftMenuProvider(Level level, BlockPos pos, Container container) {
		this.level = level;
		this.pos = pos;
		this.container = container;
	}

	@Override
	public Component getDisplayName() {
		return Component.empty();
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new SpellcraftMenu(i, container, ContainerLevelAccess.create(level, pos));
	}
}
