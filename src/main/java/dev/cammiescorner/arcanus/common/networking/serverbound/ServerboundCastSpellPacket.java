package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.api.spells.SpellComponent;
import dev.cammiescorner.arcanus.api.spells.SpellGroup;
import dev.cammiescorner.arcanus.common.data.ArcanusItemTags;
import dev.cammiescorner.arcanus.common.datacomponents.SpellBookComponent;
import dev.cammiescorner.arcanus.common.items.SpellBookItem;
import dev.cammiescorner.arcanus.common.items.StaffItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntityAttributes;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
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
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Optional;

public record ServerboundCastSpellPacket(int spellIndex) implements CustomPacketPayload {

	public static final Type<ServerboundCastSpellPacket> TYPE = new Type<>(Arcanus.id("cast_spell"));
	public static final StreamCodec<? extends FriendlyByteBuf, ServerboundCastSpellPacket> CODEC = StreamCodec.ofMember((packet, buf) -> buf.writeVarInt(packet.spellIndex()), buf -> {
		var index = buf.readVarInt();
		// throwing here is safe because vanilla does it, too :p
		Validate.exclusiveBetween(-1, SpellBookItem.SLOT_COUNT, index, "Invalid spell index: " + index);
		return new ServerboundCastSpellPacket(index);
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
						if(spell.getComponentGroups().stream().flatMap(SpellGroup::getAllComponents).mapToInt(SpellComponent::getMinLevel).max().orElse(1) > ArcanusComponents.WIZARD_LEVEL_COMPONENT.get(player).getLevel()) {
							player.displayClientMessage(Component.translatable("spell.arcanus.too_low_level").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC), true);
							return;
						}

						if(spell.getComponentGroups().stream().flatMap(SpellGroup::getAllComponents).count() > ArcanusComponents.maxSpellSize(player)) {
							player.displayClientMessage(Component.translatable("spell.arcanus.too_many_components").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC), true);
							return;
						}

						if(!ArcanusComponents.drainMana(player, spell.getManaCost(), false)) {
							player.displayClientMessage(Component.translatable("spell.arcanus.not_enough_mana").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC), true);
							return;
						}
					}

					ArcanusComponents.setPattern(player, Arcanus.getSpellPattern(index));
					ArcanusComponents.setLastCastTime(player, player.serverLevel().getGameTime());
					spell.cast(player, player.serverLevel(), stack);
					player.displayClientMessage(Component.translatable(spell.getName()).withStyle(ChatFormatting.GREEN), true);

					for(Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(ArcanusItemTags.STAVES))
						player.getCooldowns().addCooldown(holder.value(), (int) (spell.getCoolDown() * player.getAttributeValue(ArcanusEntityAttributes.SPELL_COOL_DOWN.holder())));
				}
			}
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
