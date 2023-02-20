package dev.cammiescorner.arcanuscontinuum.mixin.client;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper {
	@Shadow private int scaledWidth;
	@Shadow private int scaledHeight;

	@WrapWithCondition(method = "renderCrosshair", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V",
			ordinal = 0
	))
	private boolean arcanuscontinuum$hideVanillaCrosshair(InGameHud hud, MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
		matrices.push();
		matrices.translate((scaledWidth - 15) / 2F, (scaledHeight - 15) / 2F, 0);
		drawTexture(matrices, 0, 0, u, v, width, height);
		matrices.pop();
		return false;
	}
}
