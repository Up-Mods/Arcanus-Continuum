package dev.cammiescorner.arcanuscontinuum.mixin.common;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public abstract class MilkBucketItemMixin extends Item {
	@Unique private StatusEffectInstance copperCurse;

	public MilkBucketItemMixin(Settings settings) { super(settings); }

	@Inject(method = "finishUsing", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"
	))
	private void arcanuscontinuum$dontClearCopperCurse(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> info) {
		if(user.hasStatusEffect(ArcanusStatusEffects.COPPER_CURSE.get()) && copperCurse == null)
			copperCurse = user.getStatusEffect(ArcanusStatusEffects.COPPER_CURSE.get());
	}

	@Inject(method = "finishUsing", at = @At(value = "INVOKE_ASSIGN",
		target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"
	))
	private void arcanuscontinuum$dontClearCopperCurse2(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> info) {
		if(copperCurse != null) {
			user.addStatusEffect(copperCurse);
			copperCurse = null;
		}
	}
}
