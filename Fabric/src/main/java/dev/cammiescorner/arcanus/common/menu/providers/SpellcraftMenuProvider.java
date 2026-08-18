package dev.cammiescorner.arcanus.common.menu.providers;

import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.common.menu.SpellcraftMenu;
import dev.cammiescorner.arcanus.common.networking.clientbound.ClientboundUpdateSpellcraftScreenPacket;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SpellcraftMenuProvider implements ExtendedMenuProvider<SpellcraftMenuProvider.MenuData> {
	private final Level level;
	private final ItemStack stack;
	private final BlockPos pos;
	private final Container container;

	public SpellcraftMenuProvider(Level level, ItemStack stack, BlockPos pos, Container container) {
		this.level = level;
		this.stack = stack;
		this.pos = pos;
		this.container = container;
	}

	@Override
	public Component getDisplayName() {
		return Component.empty();
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		if(player instanceof ServerPlayer serverPlayer)
			Network.getNetworkHandler().sendToClient(new ClientboundUpdateSpellcraftScreenPacket(stack, pos), serverPlayer);

		return new SpellcraftMenu(i, container, ContainerLevelAccess.create(level, pos));
	}

	@Override
	public MenuData getScreenOpeningData(ServerPlayer player) {
		return new MenuData(stack);
	}

	public record MenuData(ItemStack stack) {
		public static final StreamCodec<RegistryFriendlyByteBuf, SpellcraftMenuProvider.MenuData> STREAM_CODEC = ItemStack.STREAM_CODEC.map(SpellcraftMenuProvider.MenuData::new, SpellcraftMenuProvider.MenuData::stack);
	}
}
