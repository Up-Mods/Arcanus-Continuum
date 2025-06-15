package dev.cammiescorner.arcanus.common.screens.providers;

import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.common.networking.clientbound.ClientboundUpdateSpellBookScreenPacket;
import dev.cammiescorner.arcanus.common.screens.SpellBookMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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
		if(player instanceof ServerPlayer serverPlayer)
			Network.getNetworkHandler().sendToClient(new ClientboundUpdateSpellBookScreenPacket(stack), serverPlayer);

		return new SpellBookMenu(i, stack);
	}
}
