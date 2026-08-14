package dev.cammiescorner.arcanus.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Unique private Entity self = (Entity) (Object) this;

	@Shadow public abstract Level level();
	@Shadow public abstract Vec3 position();
	@Shadow public abstract BlockPos blockPosition();
	@Shadow public abstract Vec3 getEyePosition();
	@Shadow public abstract double getEyeY();
	@Shadow public abstract int getBlockY();

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

	// TODO causes problems loading into levels sometimes
	@ModifyReturnValue(method = "isInWater", at = @At("RETURN"))
	private boolean getWetInAJar(boolean original) {
//		BlockState state = level().getBlockState(blockPosition());
//		AABB insideJar = WardedJarBlock.INSIDE.bounds().inflate(0.001).move(blockPosition());
//
//		if(state.is(ArcanusBlocks.WARDED_JAR.get()) && insideJar.contains(position()) && insideJar.contains(getEyePosition()))
//			return true;

		return original;
	}

	@ModifyReturnValue(method = "isUnderWater", at = @At("RETURN"))
	private boolean drownInAJar(boolean original) {
//		BlockState state = level().getBlockState(blockPosition());
//		AABB insideJar = WardedJarBlock.INSIDE.bounds().inflate(0.001).move(blockPosition());
//
//		if(state.is(ArcanusBlocks.WARDED_JAR.get()) && insideJar.contains(position()) && insideJar.contains(getEyePosition()))
//			return getBlockY() + (0.078125 * state.getValue(WardedJarBlock.LEVEL)) + 0.0625 >= getEyeY();

		return original;
	}

	@WrapOperation(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
	private boolean swimInAJar(FluidState instance, TagKey<Fluid> tag, Operation<Boolean> original) {
//		BlockState state = level().getBlockState(blockPosition());
//		AABB insideJar = WardedJarBlock.INSIDE.bounds().inflate(0.001).move(blockPosition());
//
//		if(state.is(ArcanusBlocks.WARDED_JAR.get()) && insideJar.contains(position()) && insideJar.contains(getEyePosition()))
//			return getBlockY() + (0.078125 * state.getValue(WardedJarBlock.LEVEL)) + 0.0625 >= getEyeY();

		return original.call(instance, tag);
	}
}
