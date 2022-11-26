package dev.cammiescorner.arcanuscontinuum.client.renderer.entity;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.OpossumEntityModel;
import dev.cammiescorner.arcanuscontinuum.client.renderer.feature.OpossumHatFeatureRenderer;
import dev.cammiescorner.arcanuscontinuum.common.entities.OpossumEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class OpossumEntityRenderer extends MobEntityRenderer<OpossumEntity, OpossumEntityModel> {
	public static final Identifier TEXTURE = Arcanus.id("textures/entity/living/opossum.png");

	public OpossumEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new OpossumEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(OpossumEntityModel.MODEL_LAYER)), 0.3F);
		addFeature(new OpossumHatFeatureRenderer(this));
	}

	@Override
	public void render(OpossumEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public Identifier getTexture(OpossumEntity entity) {
		return TEXTURE;
	}
}
