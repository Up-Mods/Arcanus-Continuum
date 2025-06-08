package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.items.SpellBookItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;

public record ServerboundSaveBookDataPacket(BlockPos pos, Spell spell) implements CustomPacketPayload {
	public static final Type<ServerboundSaveBookDataPacket> TYPE = new Type<>(Arcanus.id("save_book_data"));
	public static final StreamCodec<? extends FriendlyByteBuf, ServerboundSaveBookDataPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeBlockPos(packet.pos);
		buffer.writeNbt(packet.spell.toNbt());
	}, buffer -> {
		BlockPos pos = buffer.readBlockPos();
		Spell spell = buffer.readNbt() instanceof CompoundTag tag ? Spell.fromNbt(tag) : new Spell();

		return new ServerboundSaveBookDataPacket(pos, spell);
	});

	public static void handle(PacketContext<ServerboundSaveBookDataPacket> context) {
		BlockPos pos = context.message().pos();
		Spell spell = context.message().spell();
		ServerLevel level = context.sender().serverLevel();

		if(level.getBlockEntity(pos) instanceof LecternBlockEntity lectern && lectern.getBook().getItem() instanceof SpellBookItem) {
			lectern.getBook().set(ArcanusDataComponents.SPELL.get(), spell);

			for(ServerPlayer serverPlayer : level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false))
				serverPlayer.connection.send(ClientboundBlockEntityDataPacket.create(lectern, BlockEntity::saveWithoutMetadata));
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
