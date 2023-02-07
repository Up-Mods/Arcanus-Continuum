package dev.cammiescorner.arcanuscontinuum.mixin;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusStatusEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Inject(method = "getName", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$getName(CallbackInfoReturnable<Text> info) {
		if(hasStatusEffect(ArcanusStatusEffects.ANONYMITY))
			info.setReturnValue(Text.literal("Yog-Sothoth").formatted(Formatting.OBFUSCATED));
	}

	@Inject(method = "getEntityName", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$getEntityName(CallbackInfoReturnable<String> info) {
		if(hasStatusEffect(ArcanusStatusEffects.ANONYMITY))
			info.setReturnValue("Yog-Sothoth");
	}
}
