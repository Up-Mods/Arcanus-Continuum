package dev.cammiescorner.arcanuscontinuum.common.packets.s2c;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import io.netty.buffer.Unpooled;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public class SyncStatusEffectPacket {
	public static final Identifier ID = Arcanus.id("sync_status_effect");

	public static void sendToAll(LivingEntity entity, StatusEffect status, boolean hasEffect) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(entity.getId());
		buf.writeVarInt(Registries.STATUS_EFFECT.getRawId(status));
		buf.writeBoolean(hasEffect);
		ServerPlayNetworking.send(PlayerLookup.tracking(entity), ID, buf);
	}

	public static void sendTo(ServerPlayerEntity receiver, LivingEntity entity, StatusEffect status, boolean hasEffect) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(entity.getId());
		buf.writeVarInt(Registries.STATUS_EFFECT.getRawId(status));
		buf.writeBoolean(hasEffect);
		ServerPlayNetworking.send(receiver, ID, buf);
	}

	@ClientOnly
	public static void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		int entityId = buf.readVarInt();
		int statusEffectId = buf.readVarInt();
		boolean hasEffect = buf.readBoolean();

		client.execute(() -> {
			if(client.world != null && client.world.getEntityById(entityId) instanceof LivingEntity entity) {
				StatusEffect effect = Registries.STATUS_EFFECT.get(statusEffectId);

				if(effect != null) {
					if(hasEffect)
						entity.addStatusEffect(new StatusEffectInstance(effect, 100, 0, false, false));
					else
						entity.removeStatusEffect(effect);
				}
			}
		});
	}
}
