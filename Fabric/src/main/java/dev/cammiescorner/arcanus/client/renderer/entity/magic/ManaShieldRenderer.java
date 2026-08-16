package dev.cammiescorner.arcanus.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.common.entity.magic.ManaShield;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.List;

public class ManaShieldRenderer extends EntityRenderer<ManaShield> {
	private static final ResourceLocation TEXTURE = Arcanus.id("textures/block/magic_block.png");
	private static final RenderType LAYER = ArcanusClient.getMagicCirclesTri(TEXTURE);
	public static final List<Vector3f> VERTICES = List.of(
		new Vector3f(0, 0, 1), new Vector3f(0.894f, 0f, 0.447f), new Vector3f(0.276f, 0.851f, 0.447f),
		new Vector3f(-0.724f, 0.526f, 0.447f), new Vector3f(-0.724f, -0.526f, 0.447f), new Vector3f(0.276f, -0.851f, 0.447f),
		new Vector3f(0.724f, 0.526f, -0.447f), new Vector3f(-0.276f, 0.851f, -0.447f), new Vector3f(-0.894f, 0f, -0.447f),
		new Vector3f(-0.276f, -0.851f, -0.447f), new Vector3f(0.724f, -0.526f, -0.447f), new Vector3f(0f, 0f, -1f)
	);
	public static final List<Vector3i> FACES = List.of(
		new Vector3i(0, 1, 2), new Vector3i(0, 2, 3), new Vector3i(0, 3, 4), new Vector3i(0, 4, 5), new Vector3i(0, 5, 1),
		new Vector3i(11, 6, 7), new Vector3i(11, 7, 8), new Vector3i(11, 8, 9), new Vector3i(11, 9, 10), new Vector3i(11, 10, 6),
		new Vector3i(1, 2, 6), new Vector3i(2, 3, 7), new Vector3i(3, 4, 8), new Vector3i(4, 5, 9), new Vector3i(5, 1, 10),
		new Vector3i(6, 7, 2), new Vector3i(7, 8, 3), new Vector3i(8, 9, 4), new Vector3i(9, 10, 5), new Vector3i(10, 6, 1)
	);

	public ManaShieldRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	@Override
	public void render(ManaShield entity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource vertices, int light) {
		float alpha = Mth.clamp(((entity.getMaxAge() - entity.getTrueAge()) - tickDelta) / 20f, 0, 1);

		poseStack.pushPose();
		poseStack.translate(0, 2, 0);
		poseStack.mulPose(Axis.XN.rotationDegrees(90));
		poseStack.mulPose(Axis.ZP.rotationDegrees((entity.tickCount + tickDelta) * 0.25f));
		poseStack.scale(3 * alpha, 3 * alpha, 3 * alpha);
		drawIcosahedron(poseStack, vertices.getBuffer(LAYER), ArcanusHelper.getMagicColor(entity), alpha, light, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(ManaShield entity) {
		return TEXTURE;
	}

	public static void drawIcosahedron(PoseStack poseStack, VertexConsumer consumer, Color color, float alpha, int light, int overlay) {
		Matrix4f matrix4f = poseStack.last().pose();
		float r = color.redF() * alpha;
		float g = color.greenF() * alpha;
		float b = color.blueF() * alpha;

		for(Vector3i face : FACES) {
			Vector3f vert1 = ManaShieldRenderer.VERTICES.get(face.x);
			Vector3f vert2 = ManaShieldRenderer.VERTICES.get(face.y);
			Vector3f vert3 = ManaShieldRenderer.VERTICES.get(face.z);
			Vector3f u = new Vector3f(vert2.x - vert1.x, vert2.y - vert1.y, vert2.z - vert1.z);
			Vector3f v = new Vector3f(vert3.x - vert1.x, vert3.y - vert1.y, vert3.z - vert1.z);
			Vector3f normal = u.cross(v);

			consumer.addVertex(matrix4f, vert1.x, vert1.y, vert1.z).setColor(r, g, b, 1f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(poseStack.last(), normal.x, normal.y, normal.z);
			consumer.addVertex(matrix4f, vert2.x, vert2.y, vert2.z).setColor(r, g, b, 1f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(poseStack.last(), normal.x, normal.y, normal.z);
			consumer.addVertex(matrix4f, vert3.x, vert3.y, vert3.z).setColor(r, g, b, 1f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(poseStack.last(), normal.x, normal.y, normal.z);
		}
	}
}
