package dev.cammiescorner.arcanus.mixin.client;

import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DeltaTracker.Timer.class)
public abstract class TimerMixin {
	@Unique float ticksPerSecond;
	@Shadow @Final @Mutable private float msPerTick;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void captureTickRate(float ticksPerSecond, long time, FloatUnaryOperator targetMsptProvider, CallbackInfo ci) {
		this.ticksPerSecond = ticksPerSecond;
	}

	@Inject(method = "advanceTime", at = @At("HEAD"))
	private void slowDownTicks(long time, boolean advanceGameTime, CallbackInfoReturnable<Integer> cir) {
		LocalPlayer player = Minecraft.getInstance().player;

		if(player != null) {
			if(ArcanusHelper.shouldTimeDilate(player, player.level()))
				msPerTick = 1000f / (ticksPerSecond / 2f);
			else
				msPerTick = 1000f / ticksPerSecond;
		}
		else {
			msPerTick = 1000f / ticksPerSecond;
		}
	}
}
