package dev.cammiescorner.arcanus.common.items;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.STAFF_INVALID_DATA;

public class SpellBookItem extends TrinketItem {
	public SpellBookItem() {
		super(new Properties().stacksTo(1).component(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell())));
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		List<Spell> spells = stack.getOrDefault(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell()));

		for(int i = 0; i < spells.size(); i++) {
			Spell spell = spells.get(i);

			if(spell.getComponentGroups().isEmpty()) {
				tooltip.add(Component.translatable(STAFF_INVALID_DATA).withStyle(ChatFormatting.DARK_RED));
				return;
			}

			MutableComponent text = Component.literal(spell.getName()).withStyle(spell.isEmpty() ? ChatFormatting.GRAY : ChatFormatting.GREEN);
			tooltip.add(text.append(Component.literal(" (").withStyle(ChatFormatting.DARK_GRAY))
				.append(Arcanus.getSpellPatternAsText(i).withStyle(ChatFormatting.GRAY))
				.append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY)));
		}
	}
}
