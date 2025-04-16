package dev.cammiescorner.arcanus.mixin.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.cammiescorner.arcanus.common.registry.ArcanusMobEffects;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
	@Shadow @Final private Minecraft minecraft;

	@ModifyArgs(method = "turnPlayer", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"
	))
	private void slowMouse(Args args) {
		if(minecraft.player != null && ArcanusHelper.shouldTimeDilate(minecraft.player, minecraft.player.level())) {
			double x = args.get(0);
			double y = args.get(1);
			args.setAll(x * 0.5, y * 0.5);
		}
	}

	@ModifyArgs(method = "turnPlayer", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"
	))
	public void invertMouseMovements(Args args) {
		if(minecraft.player != null && minecraft.player.hasEffect(ArcanusMobEffects.DISCOMBOBULATE.holder())) {
			double x = args.get(0);
			double y = args.get(1);
			args.setAll(-x, -y);
		}
	}

	@ModifyArg(method = "onPress", at = @At(
		value = "INVOKE",
		target = "Lcom/mojang/blaze3d/platform/InputConstants$Type;getOrCreate(I)Lcom/mojang/blaze3d/platform/InputConstants$Key;"
	), index = 0)
	public int invertMouseButtons(int i) {
		if(minecraft.player != null && minecraft.player.hasEffect(ArcanusMobEffects.DISCOMBOBULATE.holder())) {
			return switch(i) {
				case 0 -> {
					KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate(0), false);
					KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate(1), true);
					yield 1;
				}
				case 1 -> {
					KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate(1), false);
					KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate(0), true);
					yield 0;
				}
				default -> i;
			};
		}

		return i;
	}
}
