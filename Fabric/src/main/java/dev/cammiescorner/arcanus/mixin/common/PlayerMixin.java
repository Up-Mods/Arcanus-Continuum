package dev.cammiescorner.arcanus.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.cammiescorner.arcanus.common.block.WardedJarBlock;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.common.registry.ArcanusMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
	@Shadow public abstract float getSpeed();

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
		super(entityType, world);
	}

	@WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;isEmpty()Z"))
	private boolean swimInAJar(FluidState instance, Operation<Boolean> original) {
		BlockState state = level().getBlockState(blockPosition());
		AABB insideJar = WardedJarBlock.INSIDE.bounds().inflate(0.001).move(blockPosition());

		if(state.is(ArcanusBlocks.WARDED_JAR.get()) && insideJar.contains(position()))
			return false;

		return original.call(instance);
	}

	@ModifyReturnValue(method = "getFlyingSpeed", at = @At(value = "RETURN", ordinal = 1))
	private float jumpingSpeed(float original) {
		return getSpeed() * (isSprinting() ? 0.25f : 0.2f);
	}

	@ModifyReturnValue(method = "getName", at = @At("RETURN"))
	private Component getName(Component original) {
		if(hasEffect(ArcanusMobEffects.ANONYMITY.holder()))
			return Component.literal("Yog-Sothoth").withStyle(ChatFormatting.OBFUSCATED);

		return original;
	}

	@ModifyReturnValue(method = "getScoreboardName", at = @At("RETURN"))
	private String getEntityName(String original) {
		if(hasEffect(ArcanusMobEffects.ANONYMITY.holder()))
			return "Yog-Sothoth";

		return original;
	}
}
