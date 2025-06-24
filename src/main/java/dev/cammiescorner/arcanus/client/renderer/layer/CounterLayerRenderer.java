package dev.cammiescorner.arcanus.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class CounterLayerRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/misc/white.png");
	private final EntityModel<T> model;

	public CounterLayerRenderer(RenderLayerParent<T, M> context) {
		super(context);
		model = context.getModel();
	}

	@Override
	public void render(PoseStack matrices, MultiBufferSource verticies, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if(ArcanusComponents.isCounterActive(entity)) {
			Color color = ArcanusComponents.getCounterColor(entity);
			long endTime = ArcanusComponents.getCounterEnd(entity);
			long timer = endTime - entity.level().getGameTime();
			float alpha = timer > 20 ? 1f : timer / 20f;
			float r = color.redF() * alpha;
			float g = color.greenF() * alpha;
			float b = color.blueF() * alpha;

			matrices.pushPose();
			matrices.scale(1.1f, 1.1f, 1.1f);
			model.renderToBuffer(matrices, verticies.getBuffer(ArcanusClient.getMagicCircles(TEXTURE)), light, OverlayTexture.NO_OVERLAY, Color.fromFloatsRGBA(r, g, b, 1f).asIntARGB());
			matrices.popPose();
		}
	}
}
