package dev.cammiescorner.arcanus.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.client.util.ClientUtils;
import dev.cammiescorner.arcanus.item.StaffItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
	@Shadow @Final private Minecraft minecraft;
	@Shadow private ItemStack mainHandItem;
	@Shadow private float oMainHandHeight;
	@Shadow private float mainHandHeight;

	@Shadow
	protected abstract void renderPlayerArm(PoseStack poseStack, MultiBufferSource vertexConsumers, int light, float equipProgress, float swingProgress, HumanoidArm arm);

	@Inject(method = "renderHandsWithItems", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
		ordinal = 0
	))
	private void animateStaff(float tickDelta, PoseStack poseStack, MultiBufferSource.BufferSource vertexConsumers, LocalPlayer player, int light, CallbackInfo info) {
		boolean isCasting = ((ClientUtils) minecraft).isCasting();

		if(minecraft.player != null && isCasting && mainHandItem.getItem() instanceof StaffItem) {
			double time = minecraft.player.tickCount + tickDelta;

			poseStack.mulPose(Axis.XP.rotationDegrees(-65f));
			poseStack.mulPose(Axis.YP.rotationDegrees((float) Math.cos(time * 0.25)));
			poseStack.mulPose(Axis.ZP.rotationDegrees(20f + (float) Math.sin(time * 0.25)));
			poseStack.translate(0.1, 1.2, -0.4);
		}
	}
}
