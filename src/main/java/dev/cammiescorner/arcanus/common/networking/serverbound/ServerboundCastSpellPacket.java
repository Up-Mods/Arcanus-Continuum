package dev.cammiescorner.arcanus.common.networking.serverbound;

import com.google.common.base.Preconditions;
import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.ManaType;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.api.spells.SpellGroup;
import dev.cammiescorner.arcanus.common.data.ArcanusItemTags;
import dev.cammiescorner.arcanus.common.data_components.SpellBookComponent;
import dev.cammiescorner.arcanus.common.items.SpellBookItem;
import dev.cammiescorner.arcanus.common.items.StaffItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public record ServerboundCastSpellPacket(int spellIndex) implements CustomPacketPayload {

	public static final Type<ServerboundCastSpellPacket> TYPE = new Type<>(Arcanus.id("cast_spell"));
	public static final StreamCodec<? extends FriendlyByteBuf, ServerboundCastSpellPacket> CODEC = StreamCodec.ofMember((packet, buf) -> buf.writeVarInt(packet.spellIndex()), buf -> {
		// throwing here is safe because vanilla does it, too :p
		return new ServerboundCastSpellPacket(Preconditions.checkElementIndex(buf.readVarInt(), SpellBookItem.SLOT_COUNT));
	});

	public static void handle(PacketContext<ServerboundCastSpellPacket> context) {
		ServerPlayer player = context.sender();
		ItemStack stack = player.getMainHandItem();
		int index = context.message().spellIndex();

		if(stack.getItem() instanceof StaffItem staff) {
			Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent(player);

			if(optional.isPresent()) {
				TrinketComponent component = optional.get();
				List<Tuple<SlotReference, ItemStack>> equipped = component.getEquipped(ArcanusItems.SPELL_BOOK.get());
				ItemStack spellBook = equipped.isEmpty() ? ItemStack.EMPTY : equipped.getFirst().getB();

				var spells = spellBook.getOrDefault(ArcanusDataComponents.SPELL_BOOK.get(), SpellBookComponent.empty());


				if(player.getCooldowns().getCooldownPercent(staff, 1f) == 0 && spells.hasSpell(index)) {
					Spell spell = spells.getSpell(index);

					if(!player.isCreative()) {
						if(spell.getComponentGroups().stream().flatMap(SpellGroup::getAllComponents).allMatch(spellComponent -> ArcanusComponents.knowsSpellComponents(player, spellComponent))) {
							player.displayClientMessage(Component.translatable(TranslationKeys.SPELL_UNKNOWN_SPELL_COMPONENTS).withStyle(ChatFormatting.RED, ChatFormatting.ITALIC), true);
							return;
						}

						if(spell.getComponentGroups().stream().flatMap(SpellGroup::getAllComponents).count() > ArcanusComponents.maxSpellSize()) {
							player.displayClientMessage(Component.translatable(TranslationKeys.SPELL_TOO_MANY_COMPONENTS).withStyle(ChatFormatting.RED, ChatFormatting.ITALIC), true);
							return;
						}

						for(ManaType manaType : spell.getManaCost().keySet()) {
							if(!ArcanusComponents.drainMana(player, manaType, spell.getManaCost().get(manaType), false)) {
								player.displayClientMessage(Component.translatable(TranslationKeys.SPELL_NOT_ENOUGH_MANA).withStyle(ChatFormatting.RED, ChatFormatting.ITALIC), true);
								return;
							}
						}
					}

					ArcanusComponents.setPattern(player, Arcanus.getSpellPattern(index));
					ArcanusComponents.setLastCastTime(player, player.serverLevel().getGameTime());
					spell.cast(player, player.serverLevel(), stack);
					player.displayClientMessage(Component.translatable(spell.getName()).withStyle(ChatFormatting.GREEN), true);

					for(Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(ArcanusItemTags.STAVES))
						player.getCooldowns().addCooldown(holder.value(), (int) (spell.getCoolDown() * player.getAttributeValue(ArcanusAttributes.SPELL_COOL_DOWN.holder())));
				}
			}
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
