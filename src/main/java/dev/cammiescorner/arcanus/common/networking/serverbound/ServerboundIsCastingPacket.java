package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record ServerboundIsCastingPacket(boolean isCasting) implements CustomPacketPayload{
	public static final CustomPacketPayload.Type<ServerboundIsCastingPacket> TYPE = new CustomPacketPayload.Type<>(Arcanus.id("is_casting"));
	public static final StreamCodec<? extends FriendlyByteBuf, ServerboundIsCastingPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeBoolean(packet.isCasting);
	}, buffer -> {
		return new ServerboundIsCastingPacket(buffer.readBoolean());
	});

	public static void handle(PacketContext<ServerboundIsCastingPacket> context) {
		ServerPlayer player = context.sender();
		boolean isCasting = context.message().isCasting();

		if(!isCasting)
			ArcanusComponents.clearPattern(player);

		ArcanusComponents.setCasting(player, isCasting);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
