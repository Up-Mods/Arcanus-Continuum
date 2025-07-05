package dev.cammiescorner.arcanus.common.item;

import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ScrollOfKnowledgeItem extends Item {
	public ScrollOfKnowledgeItem() {
		super(new Item.Properties().stacksTo(1).component(ArcanusDataComponents.SPELL_COMPONENT.get(), ArcanusSpellComponents.EMPTY.get()));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
		// TODO this is temporary to make it easy to learn spell components, until rites are implemented
		//  then we just display a screen with a button for creative users to auto learn the component

		ItemStack stack = user.getItemInHand(hand);
		SpellComponent spellComponent = stack.getOrDefault(ArcanusDataComponents.SPELL_COMPONENT.get(), ArcanusSpellComponents.EMPTY.get());

		if(ArcanusComponents.knowsSpellComponents(user, spellComponent)) {
			user.displayClientMessage(Component.translatable(TranslationKeys.USE_SCROLL_ALREADY_KNOW).withStyle(ChatFormatting.RED), true);

			return InteractionResultHolder.fail(stack);
		}

		if(!spellComponent.isEnabled()) {
			user.displayClientMessage(Component.translatable(TranslationKeys.USE_SCROLL_DISABLED_COMPONENT).withStyle(ChatFormatting.RED), true);

			return InteractionResultHolder.fail(stack);
		}

		ArcanusComponents.learnSpellComponents(user, spellComponent);

		user.displayClientMessage(Component.translatable(TranslationKeys.USE_SCROLL_SUCCESS).withStyle(ChatFormatting.LIGHT_PURPLE), true);

		return InteractionResultHolder.success(stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		SpellComponent component = stack.getOrDefault(ArcanusDataComponents.SPELL_COMPONENT.get(), ArcanusSpellComponents.EMPTY.get());

		if(component.isEnabled())
			tooltipComponents.add(component.getName().withStyle(ChatFormatting.GOLD));
		else
			tooltipComponents.add(Component.translatable(TranslationKeys.DISABLED_COMPONENT).withStyle(ChatFormatting.RED));
	}
}
