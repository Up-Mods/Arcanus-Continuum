package dev.cammiescorner.arcanus.common.packets.c2s;

import dev.cammiescorner.arcanus.Arcanus;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class CastSpellPacket {
	public static final ResourceLocation ID = Arcanus.id("cast_spell");

	public static void send(int index) {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		buf.writeInt(index);
//		ClientPlayNetworking.send(ID, buf);
	}

	public static void handler(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender sender) {
		int index = buf.readInt();

		server.execute(() -> {
			// TODO spells from data components
//			ItemStack stack = player.getMainHandItem();
//			CompoundTag tag = stack.getOrCreateTagElement(Arcanus.MOD_ID);
//
//			if(stack.getItem() instanceof StaffItem staff) {
//				ListTag list = tag.getList("Spells", Tag.TAG_COMPOUND);
//
//				if(!list.isEmpty() && player.getCooldowns().getCooldownPercent(staff, 1F) == 0) {
//					Spell spell = Spell.fromNbt(list.getCompound(index));
//
//					if(!player.isCreative()) {
//						if(spell.getComponentGroups().stream().flatMap(SpellGroup::getAllComponents).mapToInt(SpellComponent::getMinLevel).max().orElse(1) > ArcanusComponents.WIZARD_LEVEL_COMPONENT.get(player).getLevel()) {
//							player.displayClientMessage(Component.translatable("spell.arcanus.too_low_level").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC), true);
//							return;
//						}
//
//						if(spell.getComponentGroups().stream().flatMap(SpellGroup::getAllComponents).count() > ArcanusComponents.maxSpellSize(player)) {
//							player.displayClientMessage(Component.translatable("spell.arcanus.too_many_components").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC), true);
//							return;
//						}
//
//						if(!ArcanusComponents.drainMana(player, spell.getManaCost(), false)) {
//							player.displayClientMessage(Component.translatable("spell.arcanus.not_enough_mana").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC), true);
//							return;
//						}
//					}
//
//					ArcanusComponents.setPattern(player, Arcanus.getSpellPattern(index));
//					ArcanusComponents.setLastCastTime(player, player.level().getGameTime());
//					spell.cast(player, player.serverLevel(), stack);
//					player.displayClientMessage(Component.translatable(spell.getName()).withStyle(ChatFormatting.GREEN), true);
//
//					for(Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(ArcanusItemTags.STAVES))
//						player.getCooldowns().addCooldown(holder.value(), (int) (spell.getCoolDown() * player.getAttributeValue(ArcanusEntityAttributes.SPELL_COOL_DOWN.get())));
//				}
//			}
		});
	}
}
