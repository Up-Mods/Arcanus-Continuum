package dev.cammiescorner.arcanus.common.items;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.datacomponents.SpellBookComponent;
import dev.cammiescorner.arcanus.common.menus.providers.SpellBookMenuProvider;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.STAFF_INVALID_DATA;

public class SpellBookItem extends TrinketItem {

	public static final int SLOT_COUNT = 8;

	public SpellBookItem() {
		super(new Properties().stacksTo(1).component(ArcanusDataComponents.SPELL_BOOK.get(), SpellBookComponent.empty()));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		player.openMenu(new SpellBookMenuProvider(stack));

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		var spells = stack.getOrDefault(ArcanusDataComponents.SPELL_BOOK.get(), SpellBookComponent.empty());

		for(int i = 0; i < SLOT_COUNT; i++) {
			var scroll = spells.spellScrolls().get(i);
			if(!scroll.isEmpty()) {
				Spell spell = SpellScrollItem.getSpell(scroll);

				if(spell.getComponentGroups().isEmpty()) {
					tooltip.add(Component.translatable(STAFF_INVALID_DATA).withStyle(ChatFormatting.DARK_RED));
					return;
				}

				MutableComponent text = Component.literal(spell.getName()).withStyle(spell.isEmpty() ? ChatFormatting.GRAY : ChatFormatting.GREEN);
				// TODO turn the () into a translatable component
				tooltip.add(text.append(Component.literal(" (").withStyle(ChatFormatting.DARK_GRAY))
					.append(Arcanus.getSpellPatternAsText(i).withStyle(ChatFormatting.GRAY))
					.append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY)));
			}
		}
	}
}
