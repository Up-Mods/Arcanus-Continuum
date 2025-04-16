package dev.cammiescorner.arcanus.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.common.entities.magic.Smite;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SmiteRenderer extends EntityRenderer<Smite> {
	private static final ResourceLocation TEXTURE = Arcanus.id("textures/block/magic_block.png");

	public SmiteRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	@Override
	public void render(Smite entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource provider, int light) {
		super.render(entity, yaw, tickDelta, matrices, provider, light);
		renderBeam(entity, matrices, provider, 0, (float) ((entity.level().getHeight() + 2048) - entity.getY()), 0, tickDelta, OverlayTexture.NO_OVERLAY, light);
	}

	public void renderBeam(Smite entity, PoseStack matrices, MultiBufferSource provider, float x, float y, float z, float tickDelta, int overlay, int light) {
		int maxQuads = 16;
		float radius = 2.25F;
		Color color = ArcanusHelper.getMagicColor(entity);
		float squaredLength = x * x + y * y + z * z;
		float length = Mth.sqrt(squaredLength);
		float ageDelta = (entity.tickCount - 1) + tickDelta;
		float scale = Mth.clamp(ageDelta < 3 ? (ageDelta) / 3F : ageDelta > 9 ? 1 - ((ageDelta - 9F) / 15F) : 1F, 0F, 1F);
		float alpha = ageDelta < 3 ? 1 : Mth.clamp(1 - ((ageDelta - 3) / 23F), 0, 1);
		float r = color.redF() * alpha;
		float g = color.greenF() * alpha;
		float b = color.blueF() * alpha;

		matrices.pushPose();
		matrices.mulPose(Axis.XP.rotationDegrees(-90));
		matrices.scale(scale, scale, 1);

		VertexConsumer vertexConsumer = provider.getBuffer(ArcanusClient.getMagicCircles(TEXTURE));
		PoseStack.Pose entry = matrices.last();
		Matrix4f matrix4f = entry.pose();
		float vertX1 = 0F;
		float vertY1 = radius;

		for(int i = 1; i <= maxQuads; i++) {
			float vertX2 = Mth.sin(i * 6.2831855F / maxQuads) * radius;
			float vertY2 = Mth.cos(i * 6.2831855F / maxQuads) * radius;
			Vector3f u = new Vector3f(vertX2 - vertX1, vertY2 - vertY1, length);
			Vector3f v = new Vector3f(vertX1 - vertX2, vertY1 - vertY2, -length);
			Vector3f normal = u.cross(v);

			vertexConsumer.addVertex(matrix4f, vertX1, vertY1, 0F).setColor(r, g, b, 1f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(entry, normal.x, normal.y, normal.z);
			vertexConsumer.addVertex(matrix4f, vertX1, vertY1, length).setColor(r, g, b, 1f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(entry, normal.x, normal.y, normal.z);
			vertexConsumer.addVertex(matrix4f, vertX2, vertY2, length).setColor(r, g, b, 1f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(entry, normal.x, normal.y, normal.z);
			vertexConsumer.addVertex(matrix4f, vertX2, vertY2, 0F).setColor(r, g, g, 1f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(entry, normal.x, normal.y, normal.z);

			vertX1 = vertX2;
			vertY1 = vertY2;
		}

		matrices.popPose();
	}

	@Override
	public boolean shouldRender(Smite entity, Frustum frustum, double x, double y, double z) {
		return true;
	}

	@Override
	public ResourceLocation getTextureLocation(Smite entity) {
		return TEXTURE;
	}
}
