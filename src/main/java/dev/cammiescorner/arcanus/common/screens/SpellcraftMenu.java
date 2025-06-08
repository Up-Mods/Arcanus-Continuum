package dev.cammiescorner.arcanus.common.screens;

import dev.cammiescorner.arcanus.common.registry.ArcanusMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public class SpellcraftMenu extends AbstractContainerMenu {
	private final Container container;
	private final ContainerLevelAccess access;
	private final ItemStack stack;
	private final BlockPos pos;

	public SpellcraftMenu(int syncId, Container inventory) {
		this(syncId, inventory, BlockPos.ZERO, ItemStack.EMPTY, ContainerLevelAccess.NULL);
	}

	public SpellcraftMenu(int syncId, Container container, BlockPos pos, ItemStack stack, ContainerLevelAccess access) {
		super(ArcanusMenus.SPELLCRAFT_MENU.get(), syncId);
		this.container = container;
		this.stack = stack;
		this.pos = pos;
		this.access = access;
	}

	@Override
	public boolean clickMenuButton(Player player, int id) {
		if(!player.mayBuild())
			return false;

		ItemStack itemStack = container.removeItemNoUpdate(0);
		container.setChanged();

		if(!player.getInventory().add(itemStack))
			player.drop(itemStack, false);

		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int fromIndex) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	public BlockPos getPos() {
		return pos;
	}

	public ItemStack getSpellBook() {
		return stack;
	}

	public ContainerLevelAccess getAccess() {
		return access;
	}
}
