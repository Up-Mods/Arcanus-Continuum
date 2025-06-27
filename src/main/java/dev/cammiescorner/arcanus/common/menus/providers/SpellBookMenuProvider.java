package dev.cammiescorner.arcanus.common.menus.providers;

import dev.cammiescorner.arcanus.common.menus.SpellBookMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SpellBookMenuProvider implements ExtendedScreenHandlerFactory<SpellBookMenuProvider.MenuData> {
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
		return new SpellBookMenu(i, inventory, stack);
	}

	@Override
	public MenuData getScreenOpeningData(ServerPlayer player) {
		return new MenuData(stack);
	}

	public record MenuData(ItemStack stack) {
		public static final StreamCodec<RegistryFriendlyByteBuf, MenuData> STREAM_CODEC = ItemStack.STREAM_CODEC.map(MenuData::new, MenuData::stack);
	}
}
