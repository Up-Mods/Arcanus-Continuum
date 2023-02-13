package dev.cammiescorner.arcanuscontinuum.common.packets.c2s;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.common.items.SpellBookItem;
import io.netty.buffer.Unpooled;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.LinkedList;

public class SaveBookDataPacket {
	public static final Identifier ID = Arcanus.id("save_book_data");

	public static void send(BlockPos pos, LinkedList<SpellGroup> spellComponents, String spellName) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeBlockPos(pos);
		buf.writeString(spellName);
		buf.writeInt(spellComponents.size());

		for(int i = 0; i < spellComponents.size(); i++)
			buf.writeNbt(spellComponents.get(i).toNbt());

		ClientPlayNetworking.send(ID, buf);
	}

	public static void handler(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		BlockPos pos = buf.readBlockPos();
		String newSpellName = buf.readString();
		LinkedList<SpellGroup> groups = new LinkedList<>();
		int groupCount = buf.readInt();

		for(int i = 0; i < groupCount; i++)
			groups.add(SpellGroup.fromNbt(buf.readNbt()));

		server.execute(() -> {
			World world = player.getWorld();

			if(world.getBlockEntity(pos) instanceof LecternBlockEntity lectern && lectern.getBook().getItem() instanceof SpellBookItem) {
				ItemStack stack = lectern.getBook();

				if(groups.get(0).isEmpty() && groups.size() > 1 && !groups.get(1).isEmpty())
					groups.remove(0);

				stack.getOrCreateNbt().put("Spell", new Spell(groups, newSpellName).toNbt());
			}
		});
	}
}
