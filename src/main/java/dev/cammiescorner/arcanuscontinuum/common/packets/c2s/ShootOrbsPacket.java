package dev.cammiescorner.arcanuscontinuum.common.packets.c2s;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.ArcanusConfig;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.AggressorbEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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

		for(int i = 0; i < orbCount; i++)
			orbIds.add(buf.readUuid());

		server.execute(() -> {
			ServerWorld world = player.getServerWorld();
			Entity owner = world.getEntity(ownerId);

			if(owner instanceof LivingEntity livingEntity)
				shootOrb(orbIds, world, livingEntity);
		});
	}

	private static void shootOrb(List<UUID> orbIds, ServerWorld world, LivingEntity owner) {
		for(UUID orbId : orbIds) {
			if(world.getEntity(orbId) instanceof AggressorbEntity orb && owner != null && orb.isBoundToTarget()) {
				orb.setBoundToTarget(false);
				orb.setPosition(orb.getTarget().getEyePos());
				orb.setProperties(orb.getTarget(), orb.getTarget().getPitch(), orb.getTarget().getYaw(), 0F, ArcanusConfig.SpellShapes.AggressorbShapeProperties.projectileSpeed, 1F);
				world.playSound(null, orb.getX(), orb.getY(), orb.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1f, 1f, 1L);
				ArcanusComponents.removeAggressorbFromEntity(orb.getTarget(), orbId);

				break;
			}
		}
	}
}
