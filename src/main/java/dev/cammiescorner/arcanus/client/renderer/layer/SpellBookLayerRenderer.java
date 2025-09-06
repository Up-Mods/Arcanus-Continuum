package dev.cammiescorner.arcanus.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.model.feature.SpellBookModel;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class SpellBookLayerRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private static final ResourceLocation MAIN_TEXTURE = Arcanus.id("textures/entity/feature/spell_book.png");
	private static final ResourceLocation OVERLAY_TEXTURE = Arcanus.id("textures/entity/feature/spell_book_overlay.png");
	private final Minecraft client = Minecraft.getInstance();
	private final SpellBookModel<T> model;

	public SpellBookLayerRenderer(RenderLayerParent<T, M> renderer) {
		super(renderer);
		renderer.getModel();
		model = new SpellBookModel<>(client.getEntityModels().bakeLayer(SpellBookModel.MODEL_LAYER));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stack = ArcanusHelper.getActiveSpellBook(livingEntity);

		if(!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(client.options.mainHand().get() == HumanoidArm.RIGHT ? 0.25 : -0.25, -0.915, 0);

			getParentModel().copyPropertiesTo(model);
			model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(MAIN_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, stack.getOrDefault(DataComponents.DYED_COLOR, new DyedItemColor(0xff52392a, false)).rgb());
			model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(OVERLAY_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 0xffffffff);

			poseStack.popPose();
		}
	}
}
