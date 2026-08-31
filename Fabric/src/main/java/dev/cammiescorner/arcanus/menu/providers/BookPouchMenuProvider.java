package dev.cammiescorner.arcanus.menu.providers;

import dev.cammiescorner.arcanus.menu.BookPouchMenu;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BookPouchMenuProvider implements ExtendedMenuProvider<BookPouchMenuProvider.MenuData> {
	private final ItemStack pouch;

	public BookPouchMenuProvider(ItemStack pouch) {
		this.pouch = pouch;
	}

	@Override
	public Component getDisplayName() {
		return pouch.getDisplayName();
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new BookPouchMenu(i, inventory, pouch);
	}

	@Override
	public MenuData getScreenOpeningData(ServerPlayer player) {
		return new MenuData(pouch);
	}

	public record MenuData(ItemStack pouch) {
		public static final StreamCodec<RegistryFriendlyByteBuf, MenuData> STREAM_CODEC = ItemStack.STREAM_CODEC.map(MenuData::new, MenuData::pouch);
	}
}
