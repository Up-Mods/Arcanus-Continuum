package dev.cammiescorner.arcanus.item;

import dev.cammiescorner.arcanus.api.spell.Spell;
import dev.cammiescorner.arcanus.menu.providers.SpellScrollMenuProvider;
import dev.cammiescorner.arcanus.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.registry.ArcanusDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

import static dev.cammiescorner.arcanus.util.TranslationKeys.*;

public class SpellScrollItem extends Item {
	public SpellScrollItem(Item.Properties properties) {
		super(properties.stacksTo(1).component(ArcanusDataComponents.SPELL.get(), new Spell()));
	}

	@Override
	public Component getName(ItemStack stack) {
		return Component.translatable(SPELL_SCROLL_WITH_SPELL, super.getName(stack), getSpell(stack).getName());
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
		Spell spell = getSpell(itemStack);
		MutableComponent arcanaCost = Component.empty();

		builder.accept(Component.empty());
		builder.accept(Component.literal(spell.getName()).withStyle(ChatFormatting.GOLD));

		ArcanusArcana.primalArcana().forEach(primalArcana -> {
			if(!arcanaCost.equals(Component.empty()))
				arcanaCost.append(Component.literal(" | ").withStyle(ChatFormatting.GRAY));

			arcanaCost.append(Component.literal(spell.getArcanaCostAsString(primalArcana)).withColor(primalArcana.color().asIntARGB()));
		});

		builder.accept(arcanaCost);

		builder.accept(Component.translatable(TWO_ARGUMENT_KEY,
			Component.translatable(SPELL_BOOK_WEIGHT),
			Component.translatable(spell.getWeight().translationKey()).withStyle(ChatFormatting.GRAY)
		).withStyle(ChatFormatting.DARK_GREEN));

		if(spell.getCoolDown() > 0) {
			builder.accept(Component.translatable(TWO_ARGUMENT_KEY,
				Component.translatable(SPELL_BOOK_COOL_DOWN),
				Component.literal(spell.getCoolDownAsString()).withStyle(ChatFormatting.GRAY)
			).withStyle(ChatFormatting.DARK_RED));
		}
		else {
			builder.accept(Component.translatable(TWO_ARGUMENT_KEY,
				Component.translatable(SPELL_BOOK_COOL_DOWN),
				Component.translatable(SPELL_BOOK_INSTANT_COOL_DOWN).withStyle(ChatFormatting.GRAY)
			).withStyle(ChatFormatting.DARK_RED));
		}

		super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Spell spell = getSpell(stack);

		if(spell.isEmpty())
			return InteractionResult.PASS;

		player.openMenu(new SpellScrollMenuProvider(stack));

		return InteractionResult.SUCCESS_SERVER;
	}

	public static Spell getSpell(ItemStack stack) {
		var spell = stack.get(ArcanusDataComponents.SPELL.get());
		return spell != null ? spell : new Spell();
	}
}
