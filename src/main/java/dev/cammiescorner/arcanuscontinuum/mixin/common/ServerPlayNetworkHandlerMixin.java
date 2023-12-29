package dev.cammiescorner.arcanuscontinuum.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/server/network/ServerPlayNetworkHandler$1")
public class ServerPlayNetworkHandlerMixin {
	@Inject(method = "attack", at = @At("HEAD"))
	private void arcanuscontinuum$attackOrbs(CallbackInfo info) {
		System.out.println("beepboop");
	}
}
