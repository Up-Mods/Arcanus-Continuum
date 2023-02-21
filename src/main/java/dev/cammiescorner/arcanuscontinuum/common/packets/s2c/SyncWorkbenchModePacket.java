package dev.cammiescorner.arcanuscontinuum.common.packets.s2c;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.ArcaneWorkbenchScreen;
import dev.cammiescorner.arcanuscontinuum.common.util.WorkbenchMode;
import io.netty.buffer.Unpooled;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public class SyncWorkbenchModePacket {
	public static final Identifier ID = Arcanus.id("sync_workbench_mode");

	public static void send(ServerPlayerEntity receiver, WorkbenchMode mode) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(mode.ordinal());
		ServerPlayNetworking.send(receiver, ID, buf);
	}

	@ClientOnly
	public static void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		int ordinal = buf.readVarInt();

		client.execute(() -> {
			if(client.currentScreen instanceof ArcaneWorkbenchScreen screen) {
				WorkbenchMode mode = WorkbenchMode.values()[ordinal];
				screen.getScreenHandler().setMode(mode);
				screen.clearAndInit();
			}
		});
	}
}
