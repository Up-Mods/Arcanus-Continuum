package dev.cammiescorner.arcanus.common.networking.clientbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.client.gui.screens.SpellBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.ArrayList;
import java.util.List;

public record ClientboundUpdateSpellBookScreenPacket(List<Spell> stacks) implements CustomPacketPayload {
	public static final Type<ClientboundUpdateSpellBookScreenPacket> TYPE = new Type<>(Arcanus.id("update_spell_book_screen"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundUpdateSpellBookScreenPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeCollection(packet.stacks, (buf, stack) -> {
			if(buf instanceof RegistryFriendlyByteBuf regBuf)
				Spell.STREAM_CODEC.encode(regBuf, stack);
		});
	}, buffer -> {
		List<Spell> stacks = buffer.readCollection(ArrayList::new, buf -> {
			if(buf instanceof RegistryFriendlyByteBuf regBuf)
				return Spell.STREAM_CODEC.decode(regBuf);

			return new Spell();
		});

		return new ClientboundUpdateSpellBookScreenPacket(stacks);
	});

	public static void handle(PacketContext<ClientboundUpdateSpellBookScreenPacket> context) {
		if(Minecraft.getInstance().screen instanceof SpellBookScreen screen) {
			// TODO finish this packet & register it
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
