package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.api.Network;
import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.items.SpellBookItem;
import dev.cammiescorner.arcanus.common.networking.clientbound.ClientboundUpdateSpellcraftScreenPacket;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.screens.SpellcraftMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.LecternBlockEntity;

public record ServerboundSaveBookDataPacket(int containerId, Spell spell) implements CustomPacketPayload {
	public static final Type<ServerboundSaveBookDataPacket> TYPE = new Type<>(Arcanus.id("save_book_data"));
	public static final StreamCodec<? extends RegistryFriendlyByteBuf, ServerboundSaveBookDataPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeVarInt(packet.containerId);
		Spell.STREAM_CODEC.encode(buffer, packet.spell);
	}, buffer -> {
		int containerId = buffer.readVarInt();
		Spell spell = Spell.STREAM_CODEC.decode(buffer);

		return new ServerboundSaveBookDataPacket(containerId, spell);
	});

	public static void handle(PacketContext<ServerboundSaveBookDataPacket> context) {
		int containerId = context.message().containerId;
		Spell spell = context.message().spell();
		ServerPlayer player = context.sender();

		if(player.containerMenu.containerId == containerId && player.containerMenu instanceof SpellcraftMenu menu) {
			menu.getAccess().execute((level, blockPos) -> {
				if(level.getBlockEntity(blockPos) instanceof LecternBlockEntity lectern && lectern.getBook().getItem() instanceof SpellBookItem) {
					ItemStack stack = lectern.getBook();

					stack.set(ArcanusDataComponents.SPELL.get(), spell);
					lectern.setChanged();
					level.sendBlockUpdated(lectern.getBlockPos(), lectern.getBlockState(), lectern.getBlockState(), Block.UPDATE_ALL);

					Network.getNetworkHandler().sendToClientsLoadingPos(new ClientboundUpdateSpellcraftScreenPacket(stack), (ServerLevel) level, lectern.getBlockPos());
				}
			});
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
