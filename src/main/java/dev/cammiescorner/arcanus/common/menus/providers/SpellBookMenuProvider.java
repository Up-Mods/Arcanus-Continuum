package dev.cammiescorner.arcanus.common.menus.providers;

import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.menus.SpellBookMenu;
import dev.cammiescorner.arcanus.common.networking.clientbound.ClientboundUpdateSpellBookScreenPacket;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.core.NonNullList;
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
			Network.getNetworkHandler().sendToClient(new ClientboundUpdateSpellBookScreenPacket(stack.getOrDefault(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell()))), serverPlayer);

		return new SpellBookMenu(i, inventory, stack);
	}
}
