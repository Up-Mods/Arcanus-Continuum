package dev.cammiescorner.arcanus.common.packets.s2c;

import dev.cammiescorner.arcanus.Arcanus;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

public class SyncStatusEffectPacket {
	public static final ResourceLocation ID = Arcanus.id("sync_status_effect");

	public static void sendToAll(LivingEntity entity, MobEffect status, boolean hasEffect) {
		PlayerLookup.tracking(entity).forEach(player -> sendTo(player, entity, status, hasEffect));
	}

	public static void sendTo(ServerPlayer receiver, LivingEntity entity, MobEffect status, boolean hasEffect) {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		buf.writeVarInt(entity.getId());
		buf.writeVarInt(BuiltInRegistries.MOB_EFFECT.getId(status));
		buf.writeBoolean(hasEffect);
//		ServerPlayNetworking.send(receiver, ID, buf);
	}

	@Environment(EnvType.CLIENT)
	public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender sender) {
		int entityId = buf.readVarInt();
		int statusEffectId = buf.readVarInt();
		boolean hasEffect = buf.readBoolean();

		client.execute(() -> {
			if(client.level != null && client.level.getEntity(entityId) instanceof LivingEntity entity) {
				Optional<Holder.Reference<MobEffect>> effect = BuiltInRegistries.MOB_EFFECT.getHolder(statusEffectId);

				if(effect.isPresent()) {
					if(hasEffect)
						entity.addEffect(new MobEffectInstance(effect.get(), 100, 0, false, false));
					else
						entity.removeEffect(effect.get());
				}
			}
		});
	}
}
