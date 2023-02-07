package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
	@Shadow @Final private MinecraftClient client;

	@Inject(method = "onCursorPos", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$onCursorPos(long window, double x, double y, CallbackInfo info) {
		if(client.player != null && ArcanusComponents.getStunTimer(client.player) > 0)
			info.cancel();
	}

	@Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$onMouseScroll(long window, double scrollDeltaX, double scrollDeltaY, CallbackInfo info) {
		if(client.player != null && ArcanusComponents.getStunTimer(client.player) > 0)
			info.cancel();
	}
}
