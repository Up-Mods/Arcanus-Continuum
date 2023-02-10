package dev.cammiescorner.arcanuscontinuum.common.packets.s2c;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import io.netty.buffer.Unpooled;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
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

	public static void sendToAll(ServerPlayerEntity player, StatusEffect status, boolean hasEffect) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(player.getId());
		buf.writeVarInt(Registries.STATUS_EFFECT.getRawId(status));
		buf.writeBoolean(hasEffect);
		ServerPlayNetworking.send(PlayerLookup.tracking(player), ID, buf);
	}

	public static void sendTo(ServerPlayerEntity receiver, ServerPlayerEntity player, StatusEffect status, boolean hasEffect) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(player.getId());
		buf.writeVarInt(Registries.STATUS_EFFECT.getRawId(status));
		buf.writeBoolean(hasEffect);
		ServerPlayNetworking.send(receiver, ID, buf);
	}

	@ClientOnly
	public static void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		int playerId = buf.readVarInt();
		int statusEffectId = buf.readVarInt();
		boolean hasEffect = buf.readBoolean();

		client.execute(() -> {
			if(client.world != null && client.world.getEntityById(playerId) instanceof PlayerEntity player) {
				StatusEffect effect = Registries.STATUS_EFFECT.get(statusEffectId);

				if(effect != null) {
					if(hasEffect)
						player.addStatusEffect(new StatusEffectInstance(effect, 100, 0, false, false));
					else
						player.removeStatusEffect(effect);
				}
			}
		});
	}
}
