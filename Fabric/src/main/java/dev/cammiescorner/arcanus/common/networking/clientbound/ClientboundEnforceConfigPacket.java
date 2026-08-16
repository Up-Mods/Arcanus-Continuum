package dev.cammiescorner.arcanus.common.networking.clientbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ClientboundEnforceConfigPacket(boolean castingSpeedHasCoolDown) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<ClientboundEnforceConfigPacket> TYPE = new CustomPacketPayload.Type<>(Arcanus.id("enforce_config"));
	public static final StreamCodec<? extends FriendlyByteBuf, ClientboundEnforceConfigPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeBoolean(packet.castingSpeedHasCoolDown);
	}, buffer -> {
		return new ClientboundEnforceConfigPacket(buffer.readBoolean());
	});

	public static void handle(PacketContext<ClientboundEnforceConfigPacket> context) {
		ArcanusClient.castingSpeedHasCoolDown = context.message().castingSpeedHasCoolDown();
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
