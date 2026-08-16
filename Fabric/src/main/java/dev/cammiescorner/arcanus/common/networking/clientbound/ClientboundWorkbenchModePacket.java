package dev.cammiescorner.arcanus.common.networking.clientbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.gui.screens.ArcaneWorkbenchScreen;
import dev.cammiescorner.arcanus.common.util.WorkbenchMode;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ClientboundWorkbenchModePacket(WorkbenchMode mode) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<ClientboundWorkbenchModePacket> TYPE = new CustomPacketPayload.Type<>(Arcanus.id("sync_workbench_mode"));
	public static final StreamCodec<? extends FriendlyByteBuf, ClientboundWorkbenchModePacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeEnum(packet.mode);
	}, buffer -> {
		return new ClientboundWorkbenchModePacket(buffer.readEnum(WorkbenchMode.class));
	});

	public static void handle(PacketContext<ClientboundWorkbenchModePacket> context) {
		if(Minecraft.getInstance().screen instanceof ArcaneWorkbenchScreen screen) {
			WorkbenchMode mode = context.message().mode();
			screen.getMenu().setMode(mode);
			screen.rebuildWidgets();
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
