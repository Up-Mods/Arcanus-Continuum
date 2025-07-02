package dev.cammiescorner.arcanus.common.menu;

import dev.cammiescorner.arcanus.common.registry.ArcanusMenus;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.LecternBlockEntity;

public class SpellcraftMenu extends AbstractContainerMenu {
	private final Container container;
	private final ContainerLevelAccess access;

	public SpellcraftMenu(int syncId, Container inventory) {
		this(syncId, inventory, ContainerLevelAccess.NULL);
	}

	public SpellcraftMenu(int syncId, Container container, ContainerLevelAccess access) {
		super(ArcanusMenus.SPELLCRAFT_MENU.get(), syncId);
		this.container = container;
		this.access = access;
	}

	@Override
	public boolean clickMenuButton(Player player, int id) {
		if(!player.mayBuild())
			return false;

		if(id == 0) {
			ItemStack itemStack = container.removeItemNoUpdate(0);

			if(!player.getInventory().add(itemStack))
				player.drop(itemStack, false);
		}

		container.setChanged();

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

	public ItemStack getSpellBook() {
		return access.evaluate((level, blockPos) -> {
			if(level.getBlockEntity(blockPos) instanceof LecternBlockEntity lectern)
				return lectern.getBook();

			return ItemStack.EMPTY;
		}).orElse(ItemStack.EMPTY);
	}

	public ContainerLevelAccess getAccess() {
		return access;
	}
}
