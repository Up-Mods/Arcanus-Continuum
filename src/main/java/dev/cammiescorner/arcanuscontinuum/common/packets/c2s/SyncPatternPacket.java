package dev.cammiescorner.arcanuscontinuum.common.packets.c2s;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.ArrayList;
import java.util.List;

public class SyncPatternPacket {
	public static final Identifier ID = Arcanus.id("sync_pattern");

	public static void send(List<Pattern> pattern) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

		buf.writeInt(pattern.size());

		for(int i = 0; i < pattern.size(); i++)
			buf.writeInt(pattern.get(i).ordinal());

		ClientPlayNetworking.send(ID, buf);
	}

	public static void handler(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		List<Pattern> pattern = new ArrayList<>();
		int listSize = buf.readInt();

		for(int i = 0; i < listSize; i++)
			pattern.add(Pattern.values()[buf.readInt()]);

		server.execute(() -> {
			ArcanusComponents.setPattern(player, pattern);
		});
	}
}
