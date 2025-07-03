package dev.cammiescorner.arcanus.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Unique private Entity self = (Entity) (Object) this;
	@Shadow public abstract Vec3 position();
	@Shadow public abstract Level level();

	@ModifyVariable(method = "playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", at = @At("HEAD"), argsOnly = true, ordinal = 1)
	private float pitchDown(float pitch) {
		if(ArcanusHelper.shouldTimeDilate(self, level()))
			return pitch * 0.5f;

		return pitch;
	}

	@ModifyReturnValue(method = "canSpawnSprintParticle", at = @At("RETURN"))
	private boolean smallBeansDontKickUpDirt(boolean original) {
		if(self instanceof LivingEntity entity)
			return entity.getAttributeValue(Attributes.SCALE) > 0.25 && original;

		return original;
	}
}
