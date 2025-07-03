package dev.cammiescorner.arcanus.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.client.model.entity.magic.AreaOfEffectModel;
import dev.cammiescorner.arcanus.common.entity.magic.AreaOfEffect;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AreaOfEffectRenderer extends EntityRenderer<AreaOfEffect> {
	private static final ResourceLocation TEXTURE = Arcanus.id("textures/entity/magic/area_of_effect.png");
	private final AreaOfEffectModel model;

	public AreaOfEffectRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
		model = new AreaOfEffectModel(ctx.getModelSet().bakeLayer(AreaOfEffectModel.MODEL_LAYER));
	}

	@Override
	public void render(AreaOfEffect entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertices, int light) {
		super.render(entity, yaw, tickDelta, matrices, vertices, light);
		Color color = ArcanusHelper.getMagicColor(entity);
		float alpha = 1 - (Mth.clamp(entity.getTrueAge() - (ArcanusConfig.SpellShapes.AOEShapeProperties.baseLifeSpan * 0.9f), 0f, 10f) / 10f);
		float r = color.redF() * alpha;
		float g = color.greenF() * alpha;
		float b = color.blueF() * alpha;
		color = Color.fromFloatsRGB(r, g, b);

		matrices.pushPose();
		matrices.mulPose(Axis.XP.rotationDegrees(180));
		matrices.translate(0, -1.51, 0);
		model.base.yRot = (entity.tickCount + tickDelta) * 0.015f;
		model.pillar.yRot = -model.base.yRot;
		model.walls.yRot = -(entity.tickCount + tickDelta) * 0.035f;
		model.renderToBuffer(matrices, vertices.getBuffer(ArcanusClient.getMagicCircles(TEXTURE)), light, OverlayTexture.NO_OVERLAY, color.asIntARGB());
		matrices.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(AreaOfEffect entity) {
		return TEXTURE;
	}
}
