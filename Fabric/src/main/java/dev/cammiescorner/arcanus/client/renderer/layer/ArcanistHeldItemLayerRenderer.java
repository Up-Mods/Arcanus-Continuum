package dev.cammiescorner.arcanus.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.common.entity.living.Arcanist;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ArcanistHeldItemLayerRenderer<T extends Arcanist, M extends EntityModel<T> & ArmedModel & HeadedModel> extends ItemInHandLayer<T, M> {
	private final ItemInHandRenderer itemRenderer;
	private static final float HEAD_YAW = (float) (-Math.PI / 6);
	private static final float HEAD_ROLL = (float) (Math.PI / 2);

	public ArcanistHeldItemLayerRenderer(RenderLayerParent<T, M> featureRendererContext, ItemInHandRenderer heldItemRenderer) {
		super(featureRendererContext, heldItemRenderer);
		this.itemRenderer = heldItemRenderer;
	}

	@Override
	protected void renderArmWithItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformationMode, HumanoidArm arm, PoseStack poseStack, MultiBufferSource vertexConsumers, int light) {
		if(stack.is(Items.SPYGLASS) && entity.getUseItem() == stack && entity.swingTime == 0)
			renderSpyglass(entity, stack, arm, poseStack, vertexConsumers, light);
		else
			super.renderArmWithItem(entity, stack, transformationMode, arm, poseStack, vertexConsumers, light);
	}

	private void renderSpyglass(LivingEntity entity, ItemStack stack, HumanoidArm arm, PoseStack poseStack, MultiBufferSource vertexConsumers, int light) {
		poseStack.pushPose();
		ModelPart modelPart = getParentModel().getHead();
		float pitch = modelPart.xRot;
		modelPart.xRot = Mth.clamp(modelPart.xRot, HEAD_YAW, HEAD_ROLL);
		modelPart.translateAndRotate(poseStack);
		modelPart.xRot = pitch;
		CustomHeadLayer.translateToHead(poseStack, false);
		boolean bl = arm == HumanoidArm.LEFT;
		poseStack.translate((bl ? -2.5f : 2.5f) / 16f, -0.0625, 0);
		itemRenderer.renderItem(entity, stack, ItemDisplayContext.HEAD, false, poseStack, vertexConsumers, light);
		poseStack.popPose();
	}
}
