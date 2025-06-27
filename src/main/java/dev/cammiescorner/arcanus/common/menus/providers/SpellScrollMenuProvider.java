package dev.cammiescorner.arcanus.common.menus.providers;

import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.common.networking.clientbound.ClientboundUpdateSpellScrollScreenPacket;
import dev.cammiescorner.arcanus.common.menus.SpellScrollMenu;
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

public class SpellScrollMenuProvider implements ExtendedScreenHandlerFactory<SpellScrollMenuProvider.MenuData> {
	private final ItemStack stack;

	public SpellScrollMenuProvider(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public Component getDisplayName() {
		return Component.empty();
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new SpellScrollMenu(i, stack);
	}

	@Override
	public MenuData getScreenOpeningData(ServerPlayer player) {
		return new MenuData(stack);
	}

	public record MenuData(ItemStack stack) {
		public static final StreamCodec<RegistryFriendlyByteBuf, SpellScrollMenuProvider.MenuData> STREAM_CODEC = ItemStack.STREAM_CODEC.map(SpellScrollMenuProvider.MenuData::new, SpellScrollMenuProvider.MenuData::stack);
	}
}
