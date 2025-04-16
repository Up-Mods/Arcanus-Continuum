package dev.cammiescorner.arcanus.common.packets.s2c;

import dev.cammiescorner.arcanus.Arcanus;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class SyncStaffTemplatePacket {
	public static final ResourceLocation ID = Arcanus.id("sync_staff_template");

	public static void send(ServerPlayer receiver, Item item) {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
//		buf.writeItem(item.getDefaultInstance());
//		ServerPlayNetworking.send(receiver, ID, buf);
	}

	@Environment(EnvType.CLIENT)
	public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender sender) {
		// TODO read/write itemstacks from bytebuf
//		ItemStack stack = buf.readItem();
//
//		client.execute(() -> {
//			if(client.screen instanceof ArcaneWorkbenchScreen screen) {
//				screen.getMenu().setTemplate(stack.getItem());
//			}
//		});
	}
}
