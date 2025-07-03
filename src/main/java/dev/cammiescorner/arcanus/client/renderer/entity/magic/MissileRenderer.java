package dev.cammiescorner.arcanus.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.client.model.entity.magic.MagicProjectileModel;
import dev.cammiescorner.arcanus.common.entity.magic.Missile;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MissileRenderer extends ArrowRenderer<Missile> {
	private static final ResourceLocation PROJECTILE_TEXTURE = Arcanus.id("textures/entity/magic/projectile.png");
	private final MagicProjectileModel projectileModel;

	public MissileRenderer(EntityRendererProvider.Context context) {
		super(context);
		projectileModel = new MagicProjectileModel(context.getModelSet().bakeLayer(MagicProjectileModel.MODEL_LAYER));
	}

	@Override
	public void render(Missile entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertices, int light) {
		VertexConsumer consumer = vertices.getBuffer(ArcanusClient.getMagicCircles(getTextureLocation(entity)));
		Color color = ArcanusHelper.getMagicColor(entity);

		matrices.pushPose();

		matrices.mulPose(Axis.YP.rotationDegrees(Mth.lerp(tickDelta, entity.yRotO, entity.getYRot()) - 180f));
		matrices.mulPose(Axis.XP.rotationDegrees(Mth.lerp(tickDelta, entity.xRotO, entity.getXRot())));
		matrices.translate(0f, -1f, 0f);
		projectileModel.ring1.zRot = (entity.tickCount + tickDelta) * 0.1f;
		projectileModel.ring2.zRot = -(entity.tickCount + tickDelta) * 0.125f;
		projectileModel.ring3.zRot = (entity.tickCount + tickDelta) * 0.15f;
		projectileModel.renderToBuffer(matrices, consumer, light, OverlayTexture.NO_OVERLAY, color.asIntARGB());

		matrices.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(Missile entity) {
		return PROJECTILE_TEXTURE;
	}
}
