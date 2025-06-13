package dev.cammiescorner.arcanus.common.networking.clientbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.gui.screens.SpellcraftScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record ClientboundUpdateSpellcraftScreenPacket(ItemStack stack) implements CustomPacketPayload {
	public static final Type<ClientboundUpdateSpellcraftScreenPacket> TYPE = new Type<>(Arcanus.id("update_spellcraft_screen"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundUpdateSpellcraftScreenPacket> CODEC = StreamCodec.of((buffer, value) -> {
		ItemStack.STREAM_CODEC.encode(buffer, value.stack);
	}, buffer -> new ClientboundUpdateSpellcraftScreenPacket(ItemStack.STREAM_CODEC.decode(buffer)));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(PacketContext<ClientboundUpdateSpellcraftScreenPacket> context) {
		if(Minecraft.getInstance().screen instanceof SpellcraftScreen screen)
			screen.setBook(context.message().stack());
	}
}
