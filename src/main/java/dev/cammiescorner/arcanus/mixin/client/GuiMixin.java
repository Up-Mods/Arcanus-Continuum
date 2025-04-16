package dev.cammiescorner.arcanus.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public class GuiMixin {
	@WrapOperation(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0))
	private void moveCrosshair(GuiGraphics instance, ResourceLocation sprite, int x, int y, int width, int height, Operation<Void> original) {
		PoseStack matrices = instance.pose();
		matrices.pushPose();
		matrices.translate((Minecraft.getInstance().getWindow().getGuiScaledWidth() - 15) / 2f, (Minecraft.getInstance().getWindow().getGuiScaledHeight() - 15) / 2f, 0);
		original.call(instance, sprite, 0, 0, width, height);
		matrices.popPose();
	}
}
