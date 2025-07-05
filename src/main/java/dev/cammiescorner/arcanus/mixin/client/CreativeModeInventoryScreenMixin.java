package dev.cammiescorner.arcanus.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.common.registry.ArcanusCreativeTabs;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin {
	@Shadow private static CreativeModeTab selectedTab;

	@ModifyExpressionValue(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab;getDisplayItems()Ljava/util/Collection;"))
	private Collection<ItemStack> removeDisabledSpellComponents(Collection<ItemStack> original) {
		if(selectedTab != ArcanusCreativeTabs.SCROLLS.get())
			return original;

		List<ItemStack> filteredItems = new ArrayList<>();

		original.stream().filter(stack -> {
			SpellComponent component = stack.getOrDefault(ArcanusDataComponents.SPELL_COMPONENT.get(), ArcanusSpellComponents.EMPTY.get());

			return component.isEnabled() && component != ArcanusSpellComponents.EMPTY.get();
		}).collect(Collectors.toCollection(() -> filteredItems));

		return filteredItems;
	}
}
