package dev.cammiescorner.arcanuscontinuum.common.packets.c2s;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusTags;
import io.netty.buffer.Unpooled;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.quiltmc.qsl.tag.api.TagRegistry;

public class CastSpellPacket {
	public static final Identifier ID = Arcanus.id("cast_spell");

	public static void send(int index) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(index);
		ClientPlayNetworking.send(ID, buf);
	}

	public static void handler(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		int index = buf.readInt();

		server.execute(() -> {
			ItemStack stack = player.getMainHandStack();
			NbtCompound tag = stack.getOrCreateSubNbt(Arcanus.MOD_ID);

			if(stack.getItem() instanceof StaffItem staff) {
				NbtList list = tag.getList("Spells", NbtElement.STRING_TYPE);

				if(!list.isEmpty()) {
					Spell spell = Arcanus.SPELLS.get(new Identifier(list.getString(index)));

					if(ArcanusComponents.drainMana(player, spell.getManaCost(), player.isCreative())) {
						ArcanusComponents.setPattern(player, Arcanus.getSpellPattern(index));
						ArcanusComponents.setLastCastTime(player, player.world.getTime());
						spell.cast(player, player.getWorld(), staff);
						player.sendMessage(Text.translatable(spell.getTranslationKey()).formatted(Formatting.GREEN), true);

						for(Holder<Item> holder : TagRegistry.getTag(ArcanusTags.STAVES))
							player.getItemCooldownManager().set(holder.value(), spell.getCooldown());
					}
				}
			}
		});
	}
}
