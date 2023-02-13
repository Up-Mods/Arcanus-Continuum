package dev.cammiescorner.arcanuscontinuum.client.renderer.entity.magic;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.MagicRuneEntityModel;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.MagicRuneEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Axis;

public class MagicRuneEntityRenderer extends EntityRenderer<MagicRuneEntity> {
	private static final Identifier TEXTURE = Arcanus.id("textures/entity/magic/rune.png");
	private final MagicRuneEntityModel model;
	private final int[] keyFrames = {
			16, 16, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
			4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16, 16
	};

	public MagicRuneEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		model = new MagicRuneEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MagicRuneEntityModel.MODEL_LAYER));
	}

	@Override
	public void render(MagicRuneEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
		super.render(entity, yaw, tickDelta, matrices, vertices, light);
		int colour = entity.getColour();
		float alpha = keyFrames[entity.age % keyFrames.length] / 16F;
		float r = (colour >> 16 & 255) / 255F * alpha;
		float g = (colour >> 8 & 255) / 255F * alpha;
		float b = (colour & 255) / 255F * alpha;

		matrices.push();
		matrices.translate(0, Math.sin((entity.age + tickDelta) * 0.125) * 0.05, 0);
		matrices.multiply(Axis.Y_POSITIVE.rotationDegrees(entity.age + tickDelta));
		model.render(matrices, vertices.getBuffer(ArcanusClient.getMagicCircles(TEXTURE)), 15728850, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
		matrices.pop();
	}

	@Override
	public Identifier getTexture(MagicRuneEntity entity) {
		return TEXTURE;
	}
}
