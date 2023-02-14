package dev.cammiescorner.arcanuscontinuum.client.renderer.entity.magic;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.AreaOfEffectEntityModel;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.AreaOfEffectEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.MathHelper;

public class AreaOfEffectEntityRenderer extends EntityRenderer<AreaOfEffectEntity> {
	private static final Identifier TEXTURE = Arcanus.id("textures/entity/magic/area_of_effect.png");
	private final AreaOfEffectEntityModel model;

	public AreaOfEffectEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		model = new AreaOfEffectEntityModel(ctx.getModelLoader().getModelPart(AreaOfEffectEntityModel.MODEL_LAYER));
	}

	@Override
	public void render(AreaOfEffectEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
		super.render(entity, yaw, tickDelta, matrices, vertices, light);
		int colour = entity.getColour();
		float alpha = 1 - (MathHelper.clamp(entity.getTrueAge() - 80, 0, 20) / 20F);
		float r = (colour >> 16 & 255) / 255F * alpha;
		float g = (colour >> 8 & 255) / 255F * alpha;
		float b = (colour & 255) / 255F * alpha;

		matrices.push();
		matrices.multiply(Axis.X_POSITIVE.rotationDegrees(180));
		matrices.translate(0, -1.51, 0);
		model.base.yaw = (entity.age + tickDelta) * 0.015F;
		model.pillar.yaw = -model.base.yaw;
		model.walls.yaw = -(entity.age + tickDelta) * 0.035F;
		model.render(matrices, vertices.getBuffer(ArcanusClient.getMagicCircles(TEXTURE)), light, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
		matrices.pop();
	}

	@Override
	public Identifier getTexture(AreaOfEffectEntity entity) {
		return TEXTURE;
	}
}
