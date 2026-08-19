package dev.cammiescorner.arcanus.common.menu.providers;

import dev.cammiescorner.arcanus.common.menu.SpellBookMenu;
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

public class SpellBookMenuProvider implements ExtendedMenuProvider<SpellBookMenuProvider.MenuData> {
	private final ItemStack book;

	public SpellBookMenuProvider(ItemStack book) {
		this.book = book;
	}

	@Override
	public Component getDisplayName() {
		return book.getDisplayName();
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new SpellBookMenu(i, inventory, book);
	}

	@Override
	public MenuData getScreenOpeningData(ServerPlayer player) {
		return new MenuData(book);
	}

	public record MenuData(ItemStack book) {
		public static final StreamCodec<RegistryFriendlyByteBuf, MenuData> STREAM_CODEC = ItemStack.STREAM_CODEC.map(MenuData::new, MenuData::book);
	}
}
