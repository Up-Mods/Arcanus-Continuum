package dev.cammiescorner.arcanuscontinuum.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
	@Inject(method = "getPossibleEntries", at = @At("HEAD"))
	private static void arcanuscontinuum$captureStack(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> info, @Share("stack") LocalRef<ItemStack> ref) {
		ref.set(stack);
	}

	@ModifyExpressionValue(method = "getPossibleEntries", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/enchantment/EnchantmentTarget;isAcceptableItem(Lnet/minecraft/item/Item;)Z"
	))
	private static boolean arcanuscontinuum$patchEnchantStuff(boolean original, @Local(ordinal = 0) Enchantment enchantment, @Share("stack") LocalRef<ItemStack> ref) {
		return enchantment.isAcceptableItem(ref.get());
	}
}
