package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.util.StaffType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Axis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
public class HeldItemFeatureRendererMixin {
	@Inject(method = "renderItem", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
	))
	private void arcanuscontinuum$adjustItem(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		if(ArcanusComponents.CASTING_COMPONENT.isProvidedBy(entity) && entity.getMainHandStack().getItem() instanceof StaffItem item && ArcanusComponents.isCasting(entity)) {
			if(item.staffType == StaffType.STAFF) {
				matrices.translate(0, 0.1, 0);
				matrices.multiply(Axis.X_POSITIVE.rotationDegrees(-15));
			}
			else if(item.staffType == StaffType.GUN) {
				matrices.multiply(Axis.Y_POSITIVE.rotationDegrees(-45));
				matrices.translate(0.125, 0, 0);
			}
			else if(item.staffType == StaffType.WAND) {
				matrices.translate(0, -0.2, 0);
				matrices.multiply(Axis.X_POSITIVE.rotationDegrees(-65));
			}
		}
	}
}
