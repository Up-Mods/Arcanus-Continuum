package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ServerboundCycleBookPouchPacket(int index) implements CustomPacketPayload {
	public static final Type<ServerboundCycleBookPouchPacket> TYPE = new Type<>(Arcanus.id("cycle_book_pouch"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundCycleBookPouchPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeVarInt(packet.index);
	}, buffer -> new ServerboundCycleBookPouchPacket(buffer.readVarInt()));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(PacketContext<ServerboundCycleBookPouchPacket> context) {
		int index = context.message().index();

		// TODO figure out trinkets again bitch
//		if(TrinketsApi.getTrinketComponent(context.sender()).get() instanceof TrinketComponent component && component.isEquipped(ArcanusItems.BOOK_POUCH.get())) {
//			ItemStack stack = component.getEquipped(ArcanusItems.BOOK_POUCH.get()).getFirst().getB();
//
//			stack.set(ArcanusDataComponents.BOOK_POUCH_INDEX.get(), index);
//		}
	}
}
