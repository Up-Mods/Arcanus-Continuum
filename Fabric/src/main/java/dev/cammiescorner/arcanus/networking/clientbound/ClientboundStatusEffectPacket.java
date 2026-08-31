package dev.cammiescorner.arcanus.networking.clientbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public record ClientboundStatusEffectPacket(int entityId, Holder<MobEffect> effect, boolean hasEffect) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<ClientboundStatusEffectPacket> TYPE = new CustomPacketPayload.Type<>(Arcanus.id("sync_status_effects"));
	public static final StreamCodec<? extends FriendlyByteBuf, ClientboundStatusEffectPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		if(buffer instanceof RegistryFriendlyByteBuf regBuf) {
			MobEffect.STREAM_CODEC.encode(regBuf, packet.effect);
			regBuf.writeVarInt(packet.entityId);
			regBuf.writeBoolean(packet.hasEffect);
		}
	}, buffer -> {
		if(buffer instanceof RegistryFriendlyByteBuf regBuf) {
			Holder<MobEffect> effect = MobEffect.STREAM_CODEC.decode(regBuf);
			int entityId = regBuf.readVarInt();
			boolean hasEffect = buffer.readBoolean();

			return new ClientboundStatusEffectPacket(entityId, effect, hasEffect);
		}

		return new ClientboundStatusEffectPacket(-1, null, false);
	});

	public static void handle(PacketContext<ClientboundStatusEffectPacket> context) {
		ClientLevel level = Minecraft.getInstance().level;

		if(level != null) {
			Holder<MobEffect> effect = context.message().effect();
			int entityId = context.message().entityId();
			boolean hasEffect = context.message().hasEffect();

			if(level.getEntity(entityId) instanceof LivingEntity entity) {
				if(hasEffect)
					entity.addEffect(new MobEffectInstance(effect, 100, 0, false, false));
				else
					entity.removeEffect(effect);
			}
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
