package dev.cammiescorner.arcanus.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SpellBookLayerRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private final Minecraft client = Minecraft.getInstance();

	public SpellBookLayerRenderer(RenderLayerParent<T, M> renderer) {
		super(renderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		poseStack.pushPose();

		if(TrinketsApi.getTrinketComponent(livingEntity).get() instanceof TrinketComponent component && component.isEquipped(ArcanusItems.SPELL_BOOK.get())) {
			poseStack.translate(client.options.mainHand().get() == HumanoidArm.RIGHT ? 0.275 : -0.275, 1, 0.05);
			poseStack.mulPose(Axis.YP.rotationDegrees(-90));
			poseStack.mulPose(Axis.ZP.rotationDegrees(150));

			client.getItemRenderer().renderStatic(
				new ItemStack(ArcanusItems.SPELL_BOOK.get()),
				ItemDisplayContext.GROUND,
				packedLight,
				OverlayTexture.NO_OVERLAY,
				poseStack,
				bufferSource,
				livingEntity.level(),
				0
			);
		}

		poseStack.popPose();
	}
}
