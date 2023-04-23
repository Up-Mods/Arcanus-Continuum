package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.util.ArcanusHelper;
import dev.cammiescorner.arcanuscontinuum.common.util.StaffType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
	protected LivingEntityRendererMixin(EntityRendererFactory.Context context) { super(context); }

	@Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/entity/Entity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
	))
	private void arcanuscontinuum$boltRenderer(T livingEntity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light, CallbackInfo info) {
		Vec3d offset = new Vec3d(MathHelper.lerp(tickDelta, livingEntity.lastRenderX, livingEntity.getX()), MathHelper.lerp(tickDelta, livingEntity.lastRenderY, livingEntity.getY()), MathHelper.lerp(tickDelta, livingEntity.lastRenderZ, livingEntity.getZ())).add(getPositionOffset(livingEntity, tickDelta));

		matrices.push();
		matrices.translate(-offset.getX(), -offset.getY(), -offset.getZ());
		ArcanusHelper.renderBolts(livingEntity, livingEntity.getLerpedPos(tickDelta).add(0, livingEntity.getEyeHeight(livingEntity.getPose()) * 0.9, 0), matrices, vertices);
		matrices.pop();
	}

	@Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getHandSwingProgress(Lnet/minecraft/entity/LivingEntity;F)F"
	))
	private void arcanuscontinuum$render(T livingEntity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info) {
		if(livingEntity instanceof PlayerEntity player && ArcanusComponents.CASTING_COMPONENT.isProvidedBy(livingEntity) && livingEntity.getMainHandStack().getItem() instanceof StaffItem item && item.staffType == StaffType.STAFF && ArcanusComponents.isCasting(livingEntity))
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(player.getMainArm() == Arm.RIGHT ? 65 : -65));
	}
}
