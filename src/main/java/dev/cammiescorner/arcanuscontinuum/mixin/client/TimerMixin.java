package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.common.util.ArcanusHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Timer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Timer.class)
public abstract class TimerMixin {
	@Unique float ticksPerSecond;
	@Shadow @Final @Mutable private float msPerTick;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void captureTickRate(float ticksPerSecond, long lastMs, CallbackInfo info) {
		this.ticksPerSecond = ticksPerSecond;
	}

	@Inject(method = "advanceTime", at = @At("HEAD"))
	private void slowDownTicks(long gameTime, CallbackInfoReturnable<Integer> info) {
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
