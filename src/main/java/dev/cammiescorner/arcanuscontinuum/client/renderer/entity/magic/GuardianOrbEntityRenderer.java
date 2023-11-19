package dev.cammiescorner.arcanuscontinuum.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.GuardianOrbEntityModel;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.GuardianOrbEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GuardianOrbEntityRenderer extends EntityRenderer<GuardianOrbEntity> {
	private static final Identifier TEXTURE = Arcanus.id("textures/entity/magic/lob.png");
	private final GuardianOrbEntityModel model;

	public GuardianOrbEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		model = new GuardianOrbEntityModel(context.getModelLoader().getModelPart(GuardianOrbEntityModel.MODEL_LAYER));
	}

	@Override
	public void render(GuardianOrbEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
		VertexConsumer consumer = vertices.getBuffer(ArcanusClient.getMagicCircles(getTexture(entity)));
		int colour = entity.getColour();
		float r = (colour >> 16 & 255) / 255F;
		float g = (colour >> 8 & 255) / 255F;
		float b = (colour & 255) / 255F;

		matrices.push();
		matrices.translate(0, 0.2, 0);
		model.cube1.pitch = (entity.age + tickDelta) * 0.1F;
		model.cube1.yaw = (entity.age + tickDelta) * 0.1F;
		model.cube2.yaw = -(entity.age + tickDelta) * 0.125F;
		model.cube2.roll = -(entity.age + tickDelta) * 0.125F;
		model.cube3.roll = (entity.age + tickDelta) * 0.15F;
		model.cube3.pitch = (entity.age + tickDelta) * 0.15F;
		model.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
		matrices.pop();
	}

	@Override
	public Identifier getTexture(GuardianOrbEntity entity) {
		return TEXTURE;
	}
}
