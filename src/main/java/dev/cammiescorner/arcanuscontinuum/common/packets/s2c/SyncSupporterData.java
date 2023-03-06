package dev.cammiescorner.arcanuscontinuum.common.packets.s2c;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.util.SupporterData;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.Map;
import java.util.UUID;

public class SyncSupporterData {
	public static final Identifier ID = Arcanus.id("sync_supporter_data");

	public static void send(ServerPlayerEntity player) {
		PacketByteBuf buf = createBuf();
		ServerPlayNetworking.send(player, ID, buf);
	}

	public static void sendToAll(MinecraftServer server) {
		PacketByteBuf buf = createBuf();
		ServerPlayNetworking.send(PlayerLookup.all(server), ID, buf);
	}

	private static PacketByteBuf createBuf() {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(Arcanus.getSupporters().size());

		Arcanus.getSupporters().forEach((uuid, supporter) -> {
			buf.writeUuid(uuid);
			buf.writeString(supporter.username());
			buf.writeInt(supporter.magicColour());
		});

		return buf;
	}

	@ClientOnly
	public static void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		int size = buf.readVarInt();
		Map<UUID, SupporterData.Supporter> supporters = new Object2ObjectOpenHashMap<>();

		for(int i = 0; i < size; i++) {
			UUID uuid = buf.readUuid();
			String username = buf.readString();
			int magicColour = buf.readInt();

			supporters.put(uuid, new SupporterData.Supporter(username, uuid, magicColour));
		}

		client.execute(() -> {
			Arcanus.STORAGE.supporters.clear();
			Arcanus.STORAGE.supporters.putAll(supporters);
		});
	}
}
