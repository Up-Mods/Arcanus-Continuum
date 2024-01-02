package dev.cammiescorner.arcanuscontinuum.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Explosion.class)
public class ExplosionMixin {
	@ModifyExpressionValue(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
	))
	private boolean arcanuscontinuum$captureDamageReturn(boolean original, @Share("tookDamage") LocalBooleanRef ref) {
		ref.set(original);
		return original;
	}

	@ModifyExpressionValue(method = "collectBlocksAndDamageEntities", slice = @Slice(from = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
	)), at = @At(value = "NEW",
			target = "(DDD)Lnet/minecraft/util/math/Vec3d;"
	))
	private Vec3d arcanuscontinuum$noKnockback(Vec3d original, @Share("tookDamage") LocalBooleanRef ref) {
		return !ref.get() ? Vec3d.ZERO : original;
	}
}
