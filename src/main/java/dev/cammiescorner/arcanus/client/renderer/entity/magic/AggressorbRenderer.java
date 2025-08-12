package dev.cammiescorner.arcanus.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.common.entity.magic.StockpileOrb;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AggressorbRenderer extends EntityRenderer<StockpileOrb> {
	private static final ResourceLocation TEXTURE = Arcanus.id("textures/block/magic_block.png");

	public AggressorbRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(StockpileOrb entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertices, int light) {
		VertexConsumer consumer = vertices.getBuffer(ArcanusClient.getMagicCirclesTri(getTextureLocation(entity))); //vertices.getBuffer(ArcanusClient.getMagicCircles(getTextureLocation(entity)));
		Color color = ArcanusHelper.getMagicColor(entity);

		matrices.pushPose();
		matrices.translate(0, 0.2, 0);
		matrices.mulPose(Axis.XN.rotationDegrees((entity.tickCount + tickDelta) * 2));
		matrices.mulPose(Axis.YP.rotationDegrees((entity.tickCount + tickDelta) * 2));
		matrices.mulPose(Axis.ZP.rotationDegrees((entity.tickCount + tickDelta) * 2));
		matrices.scale(0.25f, 0.25f, 0.25f);
		ManaShieldRenderer.drawIcosahedron(matrices, consumer, color, 1f, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(StockpileOrb entity) {
		return TEXTURE;
	}
}
