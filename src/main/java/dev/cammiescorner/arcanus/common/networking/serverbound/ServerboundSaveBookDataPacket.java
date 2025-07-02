package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.api.Network;
import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.item.SpellScrollItem;
import dev.cammiescorner.arcanus.common.networking.clientbound.ClientboundUpdateSpellcraftScreenPacket;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.LecternBlockEntity;

public record ServerboundSaveBookDataPacket(BlockPos pos, Spell spell) implements CustomPacketPayload {
	public static final Type<ServerboundSaveBookDataPacket> TYPE = new Type<>(Arcanus.id("save_book_data"));
	public static final StreamCodec<? extends RegistryFriendlyByteBuf, ServerboundSaveBookDataPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeBlockPos(packet.pos);
		Spell.STREAM_CODEC.encode(buffer, packet.spell);
	}, buffer -> {
		BlockPos pos = buffer.readBlockPos();
		Spell spell = Spell.STREAM_CODEC.decode(buffer);

		return new ServerboundSaveBookDataPacket(pos, spell);
	});

	public static void handle(PacketContext<ServerboundSaveBookDataPacket> context) {
		BlockPos pos = context.message().pos();
		Spell spell = context.message().spell();
		ServerPlayer player = context.sender();
		ServerLevel level = player.serverLevel();

		if(level.getBlockEntity(pos) instanceof LecternBlockEntity lectern && lectern.getBook().getItem() instanceof SpellScrollItem) {
			ItemStack stack = lectern.getBook();

			stack.set(ArcanusDataComponents.SPELL.get(), spell);
			lectern.setChanged();
			level.sendBlockUpdated(lectern.getBlockPos(), lectern.getBlockState(), lectern.getBlockState(), Block.UPDATE_ALL);

			Network.getNetworkHandler().sendToClientsLoadingPos(new ClientboundUpdateSpellcraftScreenPacket(stack, pos), level, pos);
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
