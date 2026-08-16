package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public record ServerboundOpenCloseHoodPacket(int slot, boolean value) implements CustomPacketPayload {
	public static final Type<ServerboundOpenCloseHoodPacket> TYPE = new Type<>(Arcanus.id("open_close_hood"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundOpenCloseHoodPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeVarInt(packet.slot);
		buffer.writeBoolean(packet.value);
	}, buffer -> new ServerboundOpenCloseHoodPacket(buffer.readVarInt(), buffer.readBoolean()));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(PacketContext<ServerboundOpenCloseHoodPacket> context) {
		int slot = context.message().slot();
		boolean value = context.message().value();
		Inventory inventory = context.sender().getInventory();
		ItemStack stack = inventory.getItem(slot);

		stack.set(ArcanusDataComponents.HOOD_DOWN.get(), value);
	}
}
