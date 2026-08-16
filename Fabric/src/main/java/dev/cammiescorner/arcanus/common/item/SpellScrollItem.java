package dev.cammiescorner.arcanus.common.item;

import dev.cammiescorner.arcanus.api.spell.Spell;
import dev.cammiescorner.arcanus.common.menu.providers.SpellScrollMenuProvider;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
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
		return Component.translatable(SPELL_SCROLL_WITH_SPELL, super.getName(stack), getSpell(stack).getName());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		Spell spell = getSpell(stack);
		MutableComponent arcanaCost = Component.empty();

		tooltipComponents.add(Component.empty());
		tooltipComponents.add(Component.literal(spell.getName()).withStyle(ChatFormatting.GOLD));

		ArcanusArcana.primalArcana().forEach(primalArcana -> {
			if(!arcanaCost.equals(Component.empty()))
				arcanaCost.append(Component.literal(" | ").withStyle(ChatFormatting.GRAY));

			arcanaCost.append(Component.literal(spell.getArcanaCostAsString(primalArcana)).withColor(primalArcana.color().asIntARGB()));
		});

		tooltipComponents.add(arcanaCost);

		tooltipComponents.add(Component.translatable(TWO_ARGUMENT_KEY,
			Component.translatable(SPELL_BOOK_WEIGHT),
			Component.translatable(spell.getWeight().translationKey()).withStyle(ChatFormatting.GRAY)
		).withStyle(ChatFormatting.DARK_GREEN));

		if(spell.getCoolDown() > 0) {
			tooltipComponents.add(Component.translatable(TWO_ARGUMENT_KEY,
				Component.translatable(SPELL_BOOK_COOL_DOWN),
				Component.literal(spell.getCoolDownAsString()).withStyle(ChatFormatting.GRAY)
			).withStyle(ChatFormatting.DARK_RED));
		}
		else {
			tooltipComponents.add(Component.translatable(TWO_ARGUMENT_KEY,
				Component.translatable(SPELL_BOOK_COOL_DOWN),
				Component.translatable(SPELL_BOOK_INSTANT_COOL_DOWN).withStyle(ChatFormatting.GRAY)
			).withStyle(ChatFormatting.DARK_RED));
		}

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
