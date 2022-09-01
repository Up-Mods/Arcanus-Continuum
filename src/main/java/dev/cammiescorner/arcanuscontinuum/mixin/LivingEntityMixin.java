package dev.cammiescorner.arcanuscontinuum.mixin;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "createLivingAttributes", at = @At("RETURN"))
	private static void createPlayerAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
		info.getReturnValue().add(ArcanusEntityAttributes.MAX_MANA).add(ArcanusEntityAttributes.MANA_REGEN).add(ArcanusEntityAttributes.BURNOUT_REGEN).add(ArcanusEntityAttributes.MANA_LOCK);
	}
}
