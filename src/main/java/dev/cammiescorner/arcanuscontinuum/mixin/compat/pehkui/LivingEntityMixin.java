package dev.cammiescorner.arcanuscontinuum.mixin.compat.pehkui;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import virtuoel.pehkui.api.ScaleTypes;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	private LivingEntityMixin(EntityType<?> variant, World world) {
		super(variant, world);
		throw new UnsupportedOperationException();
	}

	@ModifyVariable(method = "handleFallDamage", at = @At("HEAD"), index = 1, argsOnly = true)
	private float arcanuscontinuum$alterFallDistance(float fallDistance) {
		float scale = ScaleTypes.MOTION.getScaleData(this).getScale();

		if (scale < 1)
			fallDistance *= scale / 2;

		return fallDistance;
	}
}
