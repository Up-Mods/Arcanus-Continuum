package dev.cammiescorner.arcanus.networking.clientbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.compat.ArcanusCompat;
import dev.cammiescorner.arcanus.compat.ExplosiveEnhancementCompat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public record ClientboundBurstVfxPacket(Vector3f pos, float strength, boolean didDestroyBlocks) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<ClientboundBurstVfxPacket> TYPE = new CustomPacketPayload.Type<>(Arcanus.id("burst_vfx"));
	public static final StreamCodec<? extends FriendlyByteBuf, ClientboundBurstVfxPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeVector3f(packet.pos);
		buffer.writeFloat(packet.strength);
		buffer.writeBoolean(packet.didDestroyBlocks);
	}, buffer -> {
		Vector3f pos = buffer.readVector3f();
		float strength = buffer.readFloat();
		boolean destroyedBlocks = buffer.readBoolean();

		return new ClientboundBurstVfxPacket(pos, strength, destroyedBlocks);
	});

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(PacketContext<ClientboundBurstVfxPacket> context) {
		Handler.handle(context);
	}

	@Environment(EnvType.CLIENT)
	private static class Handler {
		private static void handle(PacketContext<ClientboundBurstVfxPacket> context) {
			Level level = Minecraft.getInstance().level;

			if(level != null) {
				double x = context.message().pos().x();
				double y = context.message().pos().y();
				double z = context.message().pos().z();
				float strength = context.message().strength();
				boolean destroyedBlocks = context.message().didDestroyBlocks();

				if(ArcanusCompat.EXPLOSIVE_ENHANCEMENT.isEnabled())
					ExplosiveEnhancementCompat.spawnEnhancedBooms(level, x, y, z, strength, destroyedBlocks);
				else
					level.addParticle(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 1, 1, 1);
			}
		}
	}
}
