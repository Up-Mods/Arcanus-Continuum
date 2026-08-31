package dev.cammiescorner.arcanus.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.client.model.entity.magic.MagicLobModel;
import dev.cammiescorner.arcanus.entity.magic.Lob;
import dev.cammiescorner.arcanus.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class LobRenderer extends ArrowRenderer<Lob> {
	private static final ResourceLocation LOB_TEXTURE = Arcanus.id("textures/entity/magic/lob.png");
	private final MagicLobModel lobModel;

	public LobRenderer(EntityRendererProvider.Context context) {
		super(context);
		lobModel = new MagicLobModel(context.getModelSet().bakeLayer(MagicLobModel.MODEL_LAYER));
	}

	@Override
	public void render(Lob entity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource vertices, int light) {
		VertexConsumer consumer = vertices.getBuffer(ArcanusClient.getMagicCircles(getTextureLocation(entity)));
		Color color = ArcanusHelper.getMagicColor(entity);

		poseStack.pushPose();

		poseStack.translate(0, 0.3, 0);
		lobModel.cube1.xRot = (entity.tickCount + tickDelta) * 0.1f;
		lobModel.cube1.yRot = (entity.tickCount + tickDelta) * 0.1f;
		lobModel.cube2.yRot = -(entity.tickCount + tickDelta) * 0.125f;
		lobModel.cube2.zRot = -(entity.tickCount + tickDelta) * 0.125f;
		lobModel.cube3.zRot = (entity.tickCount + tickDelta) * 0.15f;
		lobModel.cube3.xRot = (entity.tickCount + tickDelta) * 0.15f;
		lobModel.renderToBuffer(poseStack, consumer, light, OverlayTexture.NO_OVERLAY, color.asIntARGB());

		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(Lob entity) {
		return LOB_TEXTURE;
	}
}
