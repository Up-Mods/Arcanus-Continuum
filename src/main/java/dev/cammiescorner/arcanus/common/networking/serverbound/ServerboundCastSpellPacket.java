package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntityAttributes;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.api.spells.SpellComponent;
import dev.cammiescorner.arcanus.api.spells.SpellGroup;
import dev.cammiescorner.arcanus.common.data.ArcanusItemTags;
import dev.cammiescorner.arcanus.common.items.StaffItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record ServerboundCastSpellPacket(int spellIndex) implements CustomPacketPayload {
	public static final Type<ServerboundCastSpellPacket> TYPE = new Type<>(Arcanus.id("cast_spell"));
	public static final StreamCodec<? extends FriendlyByteBuf, ServerboundCastSpellPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeVarInt(packet.spellIndex);
	}, buffer -> {
		return new ServerboundCastSpellPacket(buffer.readVarInt());
	});

	public static void handle(PacketContext<ServerboundCastSpellPacket> context) {
		ServerPlayer player = context.sender();
		ItemStack stack = player.getMainHandItem();
		int index = context.message().spellIndex();

		if(stack.getItem() instanceof StaffItem staff) {
			List<Spell> list = stack.getOrDefault(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell()));

			if(list.size() > index && player.getCooldowns().getCooldownPercent(staff, 1f) == 0) {
				Spell spell = list.get(index);

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
				ArcanusComponents.setLastCastTime(player, player.level().getGameTime());
				spell.cast(player, player.serverLevel(), stack);
				player.displayClientMessage(Component.translatable(spell.getName()).withStyle(ChatFormatting.GREEN), true);

				for(Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(ArcanusItemTags.STAVES))
					player.getCooldowns().addCooldown(holder.value(), (int) (spell.getCoolDown() * player.getAttributeValue(ArcanusEntityAttributes.SPELL_COOL_DOWN.holder())));
			}
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
