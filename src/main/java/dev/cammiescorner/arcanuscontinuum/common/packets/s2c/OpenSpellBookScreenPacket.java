package dev.cammiescorner.arcanuscontinuum.common.packets.s2c;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.SpellBookScreen;
import io.netty.buffer.Unpooled;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public class OpenSpellBookScreenPacket {
	public static final Identifier ID = Arcanus.id("open_spell_book_screen");

	public static void send(ServerPlayerEntity player, Spell spell, ItemStack stack) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

		buf.writeNbt(spell.toNbt());
		buf.writeItemStack(stack);
		ServerPlayNetworking.send(player, ID, buf);
	}

	public static void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		NbtCompound spellNbt = buf.readNbt();
		ItemStack stack = buf.readItemStack();

		client.execute(() -> {
			if(spellNbt != null) {
				Spell spell = Spell.fromNbt(spellNbt);
				client.setScreen(new SpellBookScreen(Text.literal(spell.getName()), stack));
			}
		});
	}
}
