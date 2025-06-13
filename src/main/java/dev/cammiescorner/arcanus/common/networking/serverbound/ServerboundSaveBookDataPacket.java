package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.items.SpellBookItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.screens.SpellcraftMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;

public record ServerboundSaveBookDataPacket(int containerId, Spell spell) implements CustomPacketPayload {
	public static final Type<ServerboundSaveBookDataPacket> TYPE = new Type<>(Arcanus.id("save_book_data"));
	public static final StreamCodec<? extends FriendlyByteBuf, ServerboundSaveBookDataPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeVarInt(packet.containerId);

		if(buffer instanceof RegistryFriendlyByteBuf regBuf)
			Spell.STREAM_CODEC.encode(regBuf, packet.spell);
	}, buffer -> {
		int containerId = buffer.readVarInt();
		Spell spell = buffer instanceof RegistryFriendlyByteBuf regBuf ? Spell.STREAM_CODEC.decode(regBuf) : new Spell();

		return new ServerboundSaveBookDataPacket(containerId, spell);
	});

	public static void handle(PacketContext<ServerboundSaveBookDataPacket> context) {
		int containerId = context.message().containerId;
		Spell spell = context.message().spell();
		ServerPlayer player = context.sender();

		if(player.containerMenu.containerId == containerId && player.containerMenu instanceof SpellcraftMenu menu) {
			menu.getAccess().execute((level, blockPos) -> {
				if(level.getBlockEntity(blockPos) instanceof LecternBlockEntity lectern && lectern.getBook().getItem() instanceof SpellBookItem) {
					lectern.getBook().set(ArcanusDataComponents.SPELL.get(), spell);

					for(ServerPlayer serverPlayer : player.serverLevel().getChunkSource().chunkMap.getPlayers(new ChunkPos(blockPos), false))
						serverPlayer.connection.send(ClientboundBlockEntityDataPacket.create(lectern, BlockEntity::saveWithoutMetadata));
				}
			});
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
