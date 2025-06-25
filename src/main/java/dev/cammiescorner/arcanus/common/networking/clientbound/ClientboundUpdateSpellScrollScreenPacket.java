package dev.cammiescorner.arcanus.common.networking.clientbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.gui.screens.SpellScrollScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record ClientboundUpdateSpellScrollScreenPacket(ItemStack stack) implements CustomPacketPayload {
	public static final Type<ClientboundUpdateSpellScrollScreenPacket> TYPE = new Type<>(Arcanus.id("update_spell_scroll_screen"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundUpdateSpellScrollScreenPacket> CODEC = StreamCodec.of((buffer, value) -> {
		ItemStack.STREAM_CODEC.encode(buffer, value.stack);
	}, buffer -> new ClientboundUpdateSpellScrollScreenPacket(ItemStack.STREAM_CODEC.decode(buffer)));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(PacketContext<ClientboundUpdateSpellScrollScreenPacket> context) {
		if(Minecraft.getInstance().screen instanceof SpellScrollScreen screen)
			screen.setScroll(context.message().stack());
	}
}
