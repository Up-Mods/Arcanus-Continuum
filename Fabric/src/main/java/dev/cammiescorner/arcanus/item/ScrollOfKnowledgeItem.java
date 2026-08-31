package dev.cammiescorner.arcanus.item;

import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanus.util.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class ScrollOfKnowledgeItem extends Item {
	public ScrollOfKnowledgeItem() {
		super(new Item.Properties().stacksTo(1).component(ArcanusDataComponents.SPELL_COMPONENT.get(), ArcanusSpellComponents.EMPTY.get()));
	}

	@Override
	public InteractionResult use(Level level, Player user, InteractionHand hand) {
		// TODO this is temporary to make it easy to learn spell components, until rites are implemented
		//  then we just display a screen with a button for creative users to auto learn the component

		ItemStack stack = user.getItemInHand(hand);
		SpellComponent spellComponent = stack.getOrDefault(ArcanusDataComponents.SPELL_COMPONENT.get(), ArcanusSpellComponents.EMPTY.get());

		if(ArcanusComponents.knowsSpellComponents(user, spellComponent)) {
			user.sendOverlayMessage(Component.translatable(TranslationKeys.USE_SCROLL_ALREADY_KNOW).withStyle(ChatFormatting.RED));

			return InteractionResult.FAIL;
		}

		if(!spellComponent.isEnabled()) {
			user.sendOverlayMessage(Component.translatable(TranslationKeys.USE_SCROLL_DISABLED_COMPONENT).withStyle(ChatFormatting.RED));

			return InteractionResult.FAIL;
		}

		ArcanusComponents.learnSpellComponents(user, spellComponent);

		user.sendOverlayMessage(Component.translatable(TranslationKeys.USE_SCROLL_SUCCESS).withStyle(ChatFormatting.LIGHT_PURPLE));

		return InteractionResult.SUCCESS;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
		SpellComponent component = itemStack.getOrDefault(ArcanusDataComponents.SPELL_COMPONENT.get(), ArcanusSpellComponents.EMPTY.get());

		if(component.isEnabled())
			builder.accept(component.getName().withStyle(ChatFormatting.GOLD));
		else
			builder.accept(Component.translatable(TranslationKeys.DISABLED_COMPONENT).withStyle(ChatFormatting.RED));
	}
}
