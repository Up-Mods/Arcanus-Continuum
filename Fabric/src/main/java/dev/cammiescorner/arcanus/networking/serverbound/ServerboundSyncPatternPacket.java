package dev.cammiescorner.arcanus.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.Pattern;
import dev.cammiescorner.arcanus.item.StaffItem;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import eu.pb4.trinkets.api.TrinketAttachment;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record ServerboundSyncPatternPacket(List<Pattern> patterns, boolean castSpell) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<ServerboundSyncPatternPacket> TYPE = new CustomPacketPayload.Type<>(Arcanus.id("sync_pattern"));
	public static final StreamCodec<? extends FriendlyByteBuf, ServerboundSyncPatternPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeCollection(packet.patterns, FriendlyByteBuf::writeEnum);
		buffer.writeBoolean(packet.castSpell);
	}, buffer -> {
		List<Pattern> patterns = buffer.readCollection(ArrayList::new, buf -> buf.readEnum(Pattern.class));
		boolean castSpell = buffer.readBoolean();

		return new ServerboundSyncPatternPacket(patterns, castSpell);
	});

	public static void handle(PacketContext<ServerboundSyncPatternPacket> context) {
		ServerPlayer player = context.sender();
		List<Pattern> pattern = context.message().patterns();

		ArcanusComponents.setPattern(player, pattern);

		if(pattern.size() >= 3 && context.message().castSpell()) {
			ItemStack stack = player.getMainHandItem();

			if(stack.getItem() instanceof StaffItem staff) {
				TrinketAttachment attachment = TrinketsApi.getAttachment(player);

				// TODO trinkets
//				if(attachment.isPresent()) {
//					ItemStack spellBook = ArcanusHelper.getActiveSpellBook(player);
//					SpellBookComponent spells = spellBook.getOrDefault(ArcanusDataComponents.SPELL_BOOK.get(), SpellBookComponent.empty());
//					int index = ArcanusHelper.getSpellIndex(pattern);
//
//					if(player.getCooldowns().getCooldownPercent(stack, 1f) == 0) {
//						Spell spell = spells.getSpell(index);
//
//						if(!spell.getComponentGroups().stream().flatMap(SpellGroup::getAllComponents).allMatch(spellComponent -> ArcanusComponents.knowsSpellComponents(player, spellComponent))) {
//							player.sendOverlayMessage(Component.translatable(SPELL_UNKNOWN_SPELL_COMPONENTS).withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
//							return;
//						}
//
//						if(spell.getComponentGroups().stream().flatMap(SpellGroup::getAllComponents).count() > ArcanusComponents.maxSpellSize()) {
//							player.sendOverlayMessage(Component.translatable(SPELL_TOO_MANY_COMPONENTS));
//							return;
//						}
//
//						for(PrimalArcana primalArcana : spell.getArcanaCost().keySet()) {
//							if(!player.isCreative() && !ArcanusComponents.drainArcana(player, primalArcana, spell.getArcanaCost().getDouble(primalArcana), false)) {
//								player.sendOverlayMessage(Component.translatable(SPELL_NOT_ENOUGH_MANA).withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
//								return;
//							}
//						}
//
//						ArcanusComponents.setPattern(player, ArcanusHelper.getSpellPattern(index));
//						ArcanusComponents.setLastCastTime(player, player.level().getGameTime());
//						spell.cast(player, player.level(), stack);
//						player.sendOverlayMessage(Component.translatable(spell.getName()).withStyle(ChatFormatting.GREEN));
//
//						for(Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(ArcanusItemTags.STAVES))
//							player.getCooldowns().addCooldown(stack, (int) (spell.getCoolDown() * player.getAttributeValue(ArcanusAttributes.SPELL_COOL_DOWN.holder())));
//					}
//				}
			}
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
