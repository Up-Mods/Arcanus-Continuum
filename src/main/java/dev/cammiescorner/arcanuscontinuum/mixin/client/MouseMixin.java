package dev.cammiescorner.arcanuscontinuum.mixin.client;

import com.mojang.blaze3d.platform.InputUtil;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.KeyBind;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Mouse.class)
public class MouseMixin {
	@Shadow @Final private MinecraftClient client;

	@Shadow private double y;

	@ModifyArg(method = "updateLookDirection", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"), index = 0)
	public double arcanuscontinuum$invertMouseX(double x) {
		if(client.player != null && client.player.hasStatusEffect(ArcanusStatusEffects.DISCOMBOBULATE.get()))
			return -x;

		return x;
	}

	@ModifyArg(method = "updateLookDirection", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"), index = 1)
	public double arcanuscontinuum$invertMouseY(double y) {
		if(client.player != null && client.player.hasStatusEffect(ArcanusStatusEffects.DISCOMBOBULATE.get()))
			return -y;

		return y;
	}

	@ModifyArg(method = "onMouseButton", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputUtil$Type;createFromKeyCode(I)Lcom/mojang/blaze3d/platform/InputUtil$Key;"), index = 0)
	public int arcanuscontinuum$invertMouseButtons(int i) {
		if(client.player != null && client.player.hasStatusEffect(ArcanusStatusEffects.DISCOMBOBULATE.get())) {
			return switch(i) {
				case 0 -> {
					KeyBind.setKeyPressed(InputUtil.Type.MOUSE.createFromKeyCode(0), false);
					KeyBind.setKeyPressed(InputUtil.Type.MOUSE.createFromKeyCode(1), true);
					yield 1;
				}
				case 1 -> {
					KeyBind.setKeyPressed(InputUtil.Type.MOUSE.createFromKeyCode(1), false);
					KeyBind.setKeyPressed(InputUtil.Type.MOUSE.createFromKeyCode(0), true);
					yield 0;
				}
				default -> i;
			};
		}

		return i;
	}
}
