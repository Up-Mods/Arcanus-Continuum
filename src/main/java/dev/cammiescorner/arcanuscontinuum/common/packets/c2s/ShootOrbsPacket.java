package dev.cammiescorner.arcanuscontinuum.common.packets.c2s;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.AggressorbEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShootOrbsPacket {
	public static final Identifier ID = Arcanus.id("shoot_orb");

	public static void send(List<UUID> orbIds, UUID ownerId) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

		buf.writeUuid(ownerId);
		buf.writeInt(orbIds.size());

		for(UUID orbId : orbIds)
			buf.writeUuid(orbId);

		ClientPlayNetworking.send(ID, buf);
	}

	public static void handler(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		List<UUID> orbIds = new ArrayList<>();
		UUID ownerId = buf.readUuid();
		int orbCount = buf.readInt();
		int delay = 5;

		for(int i = 0; i < orbCount; i++)
			orbIds.add(buf.readUuid());

		server.execute(() -> {
			ServerWorld world = player.getServerWorld();
			Entity owner = world.getEntity(ownerId);

			for(UUID orbId : orbIds) {
				if(world.getEntity(orbId) instanceof AggressorbEntity orb && owner != null) {
					if(owner.age % delay * (orbIds.indexOf(orbId) + 1) == 0) {
						orb.setBoundToTarget(false);
						orb.setPosition(owner.getEyePos());
						orb.fire(owner.getRotationVec(1f), 4f);
					}
				}
			}
		});
	}
}
