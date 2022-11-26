package dev.cammiescorner.arcanuscontinuum.client.renderer.feature;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.OpossumEntityModel;
import dev.cammiescorner.arcanuscontinuum.client.models.feature.OpossumHatModel;
import dev.cammiescorner.arcanuscontinuum.common.entities.OpossumEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class OpossumHatFeatureRenderer extends FeatureRenderer<OpossumEntity, OpossumEntityModel> {
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final Identifier mainTexture = Arcanus.id("textures/entity/feature/opossum_hat.png");
	private final Identifier overlayTexture = Arcanus.id("textures/entity/feature/opossum_hat_trim.png");
	private OpossumHatModel model;

	public OpossumHatFeatureRenderer(FeatureRendererContext<OpossumEntity, OpossumEntityModel> featureRendererContext) {
		super(featureRendererContext);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, OpossumEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if(model == null)
			model = new OpossumHatModel(client.getEntityModelLoader().getModelPart(OpossumHatModel.MODEL_LAYER));

		model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(mainTexture)), light, OverlayTexture.DEFAULT_UV, 0.5F, 0F, 0.5F, 1F);
		model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(overlayTexture)), light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1F);
	}
}
