package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.common.entity.magic.StockpileOrb;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ServerboundShootOrbsPacket(UUID ownerId, List<UUID> orbIds) implements CustomPacketPayload {
	public static final Type<ServerboundShootOrbsPacket> TYPE = new Type<>(Arcanus.id("shoot_orb"));
	public static final StreamCodec<? extends FriendlyByteBuf, ServerboundShootOrbsPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeUUID(packet.ownerId);
		buffer.writeCollection(packet.orbIds, (buf, uuid) -> buf.writeUUID(uuid));
	}, buffer -> {
		UUID ownerId = buffer.readUUID();
		List<UUID> orbIds = buffer.readCollection(ArrayList::new, buf -> buf.readUUID());

		return new ServerboundShootOrbsPacket(ownerId, orbIds);
	});

	public static void handle(PacketContext<ServerboundShootOrbsPacket> context) {
		UUID ownerId = context.message().ownerId();
		List<UUID> orbIds = context.message().orbIds();
		ServerLevel world = context.sender().serverLevel();
		Entity owner = world.getEntity(ownerId);

		if(owner instanceof LivingEntity livingEntity)
			shootOrb(orbIds, world, livingEntity);
	}

	private static void shootOrb(List<UUID> orbIds, ServerLevel world, LivingEntity owner) {
		for(UUID orbId : orbIds) {
			if(world.getEntity(orbId) instanceof StockpileOrb orb && owner != null && orb.isBoundToTarget()) {
				orb.setBoundToTarget(false);
				orb.setPos(orb.getTarget().getEyePosition());
				orb.shootFromRotation(orb.getTarget(), orb.getTarget().getXRot(), orb.getTarget().getYRot(), 0f, ArcanusConfig.SpellShapes.StockpileShapeProperties.projectileSpeed, 1f);
				world.playSeededSound(null, orb.getX(), orb.getY(), orb.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.NEUTRAL, 1f, 1f, 1L);
				ArcanusComponents.removeStockpileOrbFromEntity(orb.getTarget(), orbId);

				break;
			}
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
