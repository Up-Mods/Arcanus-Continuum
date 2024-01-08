package dev.cammiescorner.arcanuscontinuum.client.renderer.feature;

import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class CounterFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	private static final Identifier TEXTURE = new Identifier("textures/misc/white.png");
	private final EntityModel<T> model;

	public CounterFeatureRenderer(FeatureRendererContext<T, M> context) {
		super(context);
		model = context.getModel();
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider verticies, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if(ArcanusComponents.isCounterActive(entity)) {
			int colour = ArcanusComponents.getCounterColour(entity);
			long endTime = ArcanusComponents.getCounterEnd(entity);
			long timer = endTime - entity.getWorld().getTime();
			float alpha = timer > 20 ? 1f : timer / 20f;
			float r = ((colour >> 16 & 255) / 255F) * alpha;
			float g = ((colour >> 8 & 255) / 255F) * alpha;
			float b = ((colour & 255) / 255F) * alpha;

			matrices.push();
			matrices.scale(1.1f, 1.1f, 1.1f);
			model.render(matrices, verticies.getBuffer(ArcanusClient.getMagicCircles(TEXTURE)), light, OverlayTexture.DEFAULT_UV, r, g, b, 1f);
			matrices.pop();
		}
	}
}
