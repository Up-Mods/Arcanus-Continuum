package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input {
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$tick(boolean slowDown, float f, CallbackInfo info) {
		if(ArcanusComponents.getStunTimer(MinecraftClient.getInstance().player) > 0) {
			pressingForward = false;
			pressingBack = false;
			pressingLeft = false;
			pressingRight = false;
			forwardMovement = 0;
			sidewaysMovement = 0;
			jumping = false;
			sneaking = false;
			info.cancel();
		}
	}
}
