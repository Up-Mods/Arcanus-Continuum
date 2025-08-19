package dev.cammiescorner.arcanus.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.client.model.entity.magic.EntangledOrbModel;
import dev.cammiescorner.arcanus.common.entity.magic.MagicOrb;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class EntangledOrbRenderer extends EntityRenderer<MagicOrb> {
	private static final ResourceLocation TEXTURE = Arcanus.id("textures/entity/magic/lob.png");
	private final EntangledOrbModel model;

	public EntangledOrbRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new EntangledOrbModel(context.getModelSet().bakeLayer(EntangledOrbModel.MODEL_LAYER));
	}

	@Override
	public void render(MagicOrb entity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource vertices, int light) {
		VertexConsumer consumer = vertices.getBuffer(ArcanusClient.getMagicCircles(getTextureLocation(entity)));
		Color color = ArcanusHelper.getMagicColor(entity);

		poseStack.pushPose();
		poseStack.translate(0, 0.2, 0);
		model.cube1.xRot = (entity.tickCount + tickDelta) * 0.1f;
		model.cube1.yRot = (entity.tickCount + tickDelta) * 0.1f;
		model.cube2.yRot = -(entity.tickCount + tickDelta) * 0.125f;
		model.cube2.zRot = -(entity.tickCount + tickDelta) * 0.125f;
		model.cube3.zRot = (entity.tickCount + tickDelta) * 0.15f;
		model.cube3.xRot = (entity.tickCount + tickDelta) * 0.15f;
		model.renderToBuffer(poseStack, consumer, light, OverlayTexture.NO_OVERLAY, color.asIntARGB());
		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(MagicOrb entity) {
		return TEXTURE;
	}
}
