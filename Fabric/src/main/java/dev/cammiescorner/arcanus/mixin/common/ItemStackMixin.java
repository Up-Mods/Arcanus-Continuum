package dev.cammiescorner.arcanus.mixin.common;

import dev.cammiescorner.arcanus.registry.ArcanusDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Shadow
	protected abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> component, Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag);

	// inject at TAIL so we don't add to it if tooltips are hidden!
	@Inject(method = "getTooltipLines", at = @At("TAIL"))
	private void injectComponentTooltips(Item.TooltipContext tooltipContext, @Nullable Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir) {
		this.addToTooltip(ArcanusDataComponents.STAFF_CAP.get(), tooltipContext, component -> cir.getReturnValue().add(component), tooltipFlag);
		this.addToTooltip(ArcanusDataComponents.STAFF_CORE.get(),  tooltipContext, component -> cir.getReturnValue().add(component), tooltipFlag);
	}
}
