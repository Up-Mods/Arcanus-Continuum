package dev.cammiescorner.arcanus.networking.clientbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.gui.screens.SpellcraftScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record ClientboundUpdateSpellcraftScreenPacket(ItemStack stack, BlockPos pos) implements CustomPacketPayload {
	public static final Type<ClientboundUpdateSpellcraftScreenPacket> TYPE = new Type<>(Arcanus.id("update_spellcraft_screen"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundUpdateSpellcraftScreenPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		ItemStack.STREAM_CODEC.encode(buffer, packet.stack);
		buffer.writeBlockPos(packet.pos);
	}, buffer -> {
		ItemStack stack = ItemStack.STREAM_CODEC.decode(buffer);
		BlockPos pos = buffer.readBlockPos();

		return new ClientboundUpdateSpellcraftScreenPacket(stack, pos);
	});

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(PacketContext<ClientboundUpdateSpellcraftScreenPacket> context) {
		if(Minecraft.getInstance().screen instanceof SpellcraftScreen screen) {
			screen.setBook(context.message().stack());
			screen.setBlockPos(context.message().pos());
		}
	}
}
