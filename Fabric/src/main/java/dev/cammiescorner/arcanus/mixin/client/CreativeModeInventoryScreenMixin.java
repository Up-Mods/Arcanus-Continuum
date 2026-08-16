package dev.cammiescorner.arcanus.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.common.registry.ArcanusCreativeTabs;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin {
	@Shadow private static CreativeModeTab selectedTab;

	@ModifyExpressionValue(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab;getDisplayItems()Ljava/util/Collection;"))
	private Collection<ItemStack> removeDisabledSpellComponents(Collection<ItemStack> original) {
		if(selectedTab != ArcanusCreativeTabs.ARCANUS.get())
			return original;

		return original.stream().filter(stack -> {
			if(stack.is(ArcanusItems.SCROLL_OF_KNOWLEDGE.get())) {
				SpellComponent component = stack.getOrDefault(ArcanusDataComponents.SPELL_COMPONENT.get(), ArcanusSpellComponents.EMPTY.get());

				return component.isEnabled() && component != ArcanusSpellComponents.EMPTY.get();
			}

			return true;
		}).toList();
	}
}
