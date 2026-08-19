package dev.cammiescorner.arcanus.common.menu.providers;

import dev.cammiescorner.arcanus.common.menu.ScrollOfKnowledgeMenu;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
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

public class ScrollOfKnowledgeMenuProvider implements ExtendedMenuProvider<ScrollOfKnowledgeMenuProvider.MenuData> {
	private final ItemStack stack;

	public ScrollOfKnowledgeMenuProvider(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public Component getDisplayName() {
		return stack.getOrDefault(ArcanusDataComponents.SPELL_COMPONENT.get(), ArcanusSpellComponents.EMPTY.get()).getName();
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new ScrollOfKnowledgeMenu(i, stack);
	}

	@Override
	public MenuData getScreenOpeningData(ServerPlayer player) {
		return new MenuData(stack);
	}

	public record MenuData(ItemStack stack) {
		public static final StreamCodec<RegistryFriendlyByteBuf, ScrollOfKnowledgeMenuProvider.MenuData> STREAM_CODEC = ItemStack.STREAM_CODEC.map(ScrollOfKnowledgeMenuProvider.MenuData::new, ScrollOfKnowledgeMenuProvider.MenuData::stack);
	}
}
