package dev.cammiescorner.arcanus.common.items;

import dev.cammiescorner.arcanus.api.spells.ManaType;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.menus.providers.SpellScrollMenuProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

public class SpellScrollItem extends Item {
	public SpellScrollItem() {
		super(new Item.Properties().stacksTo(1).component(ArcanusDataComponents.SPELL.get(), new Spell()));
	}

	@Override
	public Component getName(ItemStack stack) {
		Spell spell = getSpell(stack);

		return ((MutableComponent) super.getName(stack)).append(" (" + spell.getName() + ")");
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		Spell spell = getSpell(stack);

		tooltipComponents.add(Component.literal(spell.getName()).withStyle(ChatFormatting.GOLD));
		tooltipComponents.add(Component.translatable(TWO_ARGUMENT_KEY,
			Component.translatable(SPELL_BOOK_WEIGHT),
			Component.translatable(spell.getWeight().translationKey()).withStyle(ChatFormatting.GRAY)
		).withStyle(ChatFormatting.GREEN));

		for(ManaType manaType : ManaType.values()) {
			if(spell.getManaCost().get(manaType) <= 0)
				continue;

			tooltipComponents.add(Component.translatable(TWO_ARGUMENT_KEY,
				Component.translatable(SPELL_BOOK_MANA_COST),
				Component.literal(spell.getManaCostAsString(manaType)).withStyle(ChatFormatting.GRAY)
			).withStyle(manaType.getChatFormatting()));
		}
		tooltipComponents.add(Component.translatable(TWO_ARGUMENT_KEY,
			Component.translatable(SPELL_BOOK_COOL_DOWN),
			Component.literal(spell.getCoolDownAsString()).withStyle(ChatFormatting.GRAY)
		).withStyle(ChatFormatting.RED));

		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Spell spell = getSpell(stack);

		if(spell.isEmpty())
			return InteractionResultHolder.pass(stack);

		player.openMenu(new SpellScrollMenuProvider(stack));

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	public static Spell getSpell(ItemStack stack) {
		var spell = stack.get(ArcanusDataComponents.SPELL.get());
		return spell != null ? spell : new Spell();
	}
}
