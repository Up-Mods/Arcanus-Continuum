package dev.cammiescorner.arcanus.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.common.item.StaffItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {
	protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
		super(context);
	}

	@Inject(method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
	private void hideNameWithHood(T entity, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = entity.getItemBySlot(EquipmentSlot.HEAD);

		if(!stack.getOrDefault(ArcanusDataComponents.HOOD_DOWN.get(), true))
			info.setReturnValue(false);
	}

	@Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"
	))
	private void boltRenderer(T livingEntity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource vertices, int light, CallbackInfo info) {
		Vec3 offset = new Vec3(Mth.lerp(tickDelta, livingEntity.xOld, livingEntity.getX()), Mth.lerp(tickDelta, livingEntity.yOld, livingEntity.getY()), Mth.lerp(tickDelta, livingEntity.zOld, livingEntity.getZ())).add(getRenderOffset(livingEntity, tickDelta));

		poseStack.pushPose();
		poseStack.translate(-offset.x(), -offset.y(), -offset.z());
		ArcanusClient.renderBolts(livingEntity, livingEntity.getPosition(tickDelta).add(0, livingEntity.getEyeHeight(livingEntity.getPose()) * 0.9, 0), poseStack, vertices);
		poseStack.popPose();
	}

	@Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getAttackAnim(Lnet/minecraft/world/entity/LivingEntity;F)F"
	))
	private void render(T livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int i, CallbackInfo info) {
		if(livingEntity instanceof Player player && ArcanusComponents.CASTING_COMPONENT.isProvidedBy(livingEntity) && livingEntity.getMainHandItem().getItem() instanceof StaffItem && ArcanusComponents.isCasting(livingEntity))
			poseStack.mulPose(Axis.YP.rotationDegrees(player.getMainArm() == HumanoidArm.RIGHT ? 65 : -65));
	}
}
