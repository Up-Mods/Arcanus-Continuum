package dev.cammiescorner.arcanus.common.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntityAttributes;
import dev.cammiescorner.arcanus.api.spells.Pattern;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.api.spells.SpellComponent;
import dev.cammiescorner.arcanus.api.spells.SpellGroup;
import dev.cammiescorner.arcanus.common.data.ArcanusItemTags;
import dev.cammiescorner.arcanus.common.items.StaffItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
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

public record ServerboundSyncPatternPacket(List<Pattern> patterns) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<ServerboundSyncPatternPacket> TYPE = new CustomPacketPayload.Type<>(Arcanus.id("shoot_orb"));
	public static final StreamCodec<? extends FriendlyByteBuf, ServerboundSyncPatternPacket> CODEC = StreamCodec.of((buffer, packet) -> {
		buffer.writeVarInt(packet.patterns.size());

		for(Pattern pattern : packet.patterns)
			buffer.writeEnum(pattern);
	}, buffer -> {
		List<Pattern> patterns = new ArrayList<>();

		for(int i = 0; i < buffer.readVarInt(); i++)
			patterns.add(buffer.readEnum(Pattern.class));

		return new ServerboundSyncPatternPacket(patterns);
	});

	public static void handle(PacketContext<ServerboundSyncPatternPacket> context) {
		ServerPlayer player = context.sender();
		List<Pattern> pattern = context.message().patterns();

		ArcanusComponents.setPattern(player, pattern);

		if(pattern.size() >= 3) {
			ItemStack stack = player.getMainHandItem();

			if(stack.getItem() instanceof StaffItem staff) {
				List<Spell> spells = stack.getOrDefault(ArcanusDataComponents.SPELL_LIST.get(), new ArrayList<>());
				int index = Arcanus.getSpellIndex(pattern);

				if(!spells.isEmpty() && spells.size() > index && player.getCooldowns().getCooldownPercent(staff, 1f) == 0) {
					Spell spell = spells.get(index);

					if(spell.getComponentGroups().stream().flatMap(SpellGroup::getAllComponents).mapToInt(SpellComponent::getMinLevel).max().orElse(1) > ArcanusComponents.WIZARD_LEVEL_COMPONENT.get(player).getLevel()) {
						player.displayClientMessage(Component.translatable("spell.arcanus.too_low_level"), true);
						return;
					}

					if(spell.getComponentGroups().stream().flatMap(SpellGroup::getAllComponents).count() > ArcanusComponents.maxSpellSize(player)) {
						player.displayClientMessage(Component.translatable("spell.arcanus.too_many_components"), true);
						return;
					}

					if(!ArcanusComponents.drainMana(player, spell.getManaCost(), player.isCreative())) {
						player.displayClientMessage(Component.translatable("spell.arcanus.not_enough_mana"), true);
						return;
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
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
