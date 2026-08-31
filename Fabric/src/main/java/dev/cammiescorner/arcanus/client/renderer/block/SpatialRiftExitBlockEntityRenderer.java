package dev.cammiescorner.arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.client.model.entity.magic.SpatialRiftSigilModel;
import dev.cammiescorner.arcanus.block.entities.SpatialRiftExitBlockEntity;
import dev.cammiescorner.arcanus.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.Level;

public class SpatialRiftExitBlockEntityRenderer implements BlockEntityRenderer<SpatialRiftExitBlockEntity> {
	private static final RenderType LAYER = ArcanusClient.getMagicCircles(Arcanus.id("textures/entity/magic/spatial_rift_sigil.png"));
	private final SpatialRiftSigilModel sigilModel;

	public SpatialRiftExitBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
		sigilModel = new SpatialRiftSigilModel(ctx.bakeLayer(SpatialRiftSigilModel.MODEL_LAYER));
	}

	@Override
	public void render(SpatialRiftExitBlockEntity entity, float tickDelta, PoseStack poseStack, MultiBufferSource vertices, int light, int overlay) {
		Level world = entity.getLevel();

		if(world != null) {
			Color color = ArcanusHelper.getMagicColor(entity);
			float ageDelta = world.getGameTime() + tickDelta;

			poseStack.pushPose();
			poseStack.translate(1f, 0f, 1f);
			poseStack.scale(0.75f, 0.75f, 0.75f);
			sigilModel.sigil.yRot = ageDelta * 0.015f;
			sigilModel.renderToBuffer(poseStack, vertices.getBuffer(LAYER), light, OverlayTexture.NO_OVERLAY, color.asIntARGB());
			poseStack.popPose();
		}
	}
}
