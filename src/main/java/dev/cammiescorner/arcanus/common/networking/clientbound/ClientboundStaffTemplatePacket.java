package dev.cammiescorner.arcanus.common.networking.clientbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.gui.screens.ArcaneWorkbenchScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record ClientboundStaffTemplatePacket(ItemStack stack) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<ClientboundStaffTemplatePacket> TYPE = new CustomPacketPayload.Type<>(Arcanus.id("staff_template"));
	public static final StreamCodec<? extends FriendlyByteBuf, ClientboundStaffTemplatePacket> CODEC = StreamCodec.of((buffer, packet) -> {
		if(buffer instanceof RegistryFriendlyByteBuf regBuf)
			ItemStack.STREAM_CODEC.encode(regBuf, packet.stack);
	}, buffer -> {
		if(buffer instanceof RegistryFriendlyByteBuf regBuf)
			return new ClientboundStaffTemplatePacket(ItemStack.STREAM_CODEC.decode(regBuf));

		return new ClientboundStaffTemplatePacket(ItemStack.EMPTY);
	});

	public static void handle(PacketContext<ClientboundStaffTemplatePacket> context) {
		ItemStack stack = context.message().stack();

		if(Minecraft.getInstance().screen instanceof ArcaneWorkbenchScreen screen)
			screen.getMenu().setTemplate(stack.getItem());
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
