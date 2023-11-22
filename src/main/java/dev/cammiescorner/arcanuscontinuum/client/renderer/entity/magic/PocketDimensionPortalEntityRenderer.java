package dev.cammiescorner.arcanuscontinuum.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.PocketDimensionPortalEntityModel;
import dev.cammiescorner.arcanuscontinuum.client.utils.StencilBuffer;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.PocketDimensionPortalEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class PocketDimensionPortalEntityRenderer extends EntityRenderer<PocketDimensionPortalEntity> {
	private static final Identifier TEXTURE = Arcanus.id("textures/entity/magic/pocket_dimension_portal.png");
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final Tessellator tessellator = Tessellator.getInstance();
	private final PocketDimensionPortalEntityModel model;

	public PocketDimensionPortalEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		model = new PocketDimensionPortalEntityModel(ctx.getModelLoader().getModelPart(PocketDimensionPortalEntityModel.MODEL_LAYER));
	}

	@Override
	public void render(PocketDimensionPortalEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
		super.render(entity, yaw, tickDelta, matrices, vertices, light);
		StencilBuffer stencilBuffer = ((StencilBuffer) client.getFramebuffer());
		RenderLayer layer = ArcanusClient.getMagicPortal(TEXTURE);
		int colour = entity.getColour();
		float r = (colour >> 16 & 255) / 255F;
		float g = (colour >> 8 & 255) / 255F;
		float b = (colour & 255) / 255F;
		float ageDelta = entity.getTrueAge() + tickDelta;
		float maxScale = 0.5f;
		float scale = entity.getTrueAge() <= 100 ? Math.min(maxScale, (ageDelta / 100f) * maxScale) : entity.getTrueAge() > 700 ? Math.max(0, (1 - (ageDelta - 700) / 20f) * maxScale) : maxScale;

		if(!stencilBuffer.arcanuscontinuum$isStencilBufferEnabled())
			stencilBuffer.arcanuscontinuum$enableStencilBufferAndReload(true);

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_STENCIL_TEST);

		GL11.glDepthMask(false);
		GL11.glColorMask(false, false, false, true);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_INCR);
		GL11.glStencilFunc(GL11.GL_EQUAL, 0, 0xFF);
		GL11.glStencilMask(0xFF);

		RenderLayer.getWaterMask().startDrawing();
		GameRenderer.getPositionShader().bind();
		GL11.glColorMask(true, false, false, true);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		drawStencil(matrices, client.gameRenderer.getCamera().getPos(), entity, tessellator);
		GameRenderer.getPositionShader().unbind();
		RenderLayer.getWaterMask().endDrawing();

		GL11.glColorMask(true, true, true, true);
		GL11.glStencilFunc(GL11.GL_NOTEQUAL, 0, 0xFF);
		GL11.glStencilMask(0x00);
		matrices.push();
		matrices.translate(-0.35f, -0, 0);
		matrices.multiply(Axis.Z_POSITIVE.rotationDegrees(90));
		matrices.scale(scale * 1.5f, scale * 1.5f, -scale * 1.5f);
		model.render(matrices, vertices.getBuffer(layer), light, OverlayTexture.DEFAULT_UV, r, g, b, 1f);
//		model.skybox.render(matrices, vertices.getBuffer(RenderLayer.getEntityAlpha(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
		matrices.pop();

		if (vertices instanceof VertexConsumerProvider.Immediate immediate) {
			immediate.draw();
			GL11.glDepthMask(true);
		}

		GL11.glColorMask(false, false, false, true);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_DECR);
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
		GL11.glStencilMask(0xFF);

		RenderLayer.getWaterMask().startDrawing();
		GameRenderer.getPositionShader().bind();
		GL11.glDepthFunc(GL11.GL_ALWAYS);
		drawStencil(matrices, client.gameRenderer.getCamera().getPos(), entity, tessellator);
		GameRenderer.getPositionShader().unbind();
		RenderLayer.getWaterMask().endDrawing();

		GL11.glStencilFunc(GL11.GL_EQUAL, 0, 0xFF);
		GL11.glStencilMask(0x00);

		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
	}

	@Override
	public Identifier getTexture(PocketDimensionPortalEntity entity) {
		return TEXTURE;
	}

	public static void drawStencil(MatrixStack matrices, Vec3d cameraPos, PocketDimensionPortalEntity portal, Tessellator tessellator) {
//		GL11.glDisable(GL11.GL_DEPTH_TEST);
		BufferBuilder builder = tessellator.getBufferBuilder();
		builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
		builder.vertex(
				matrices.peek().getModel(), (float) (0),
				(float) ( - 1),
				(float) ( - 1)
		).next();
		builder.vertex(
				matrices.peek().getModel(), (float) (0),
				(float) ( + 1),
				(float) ( - 1)
		).next();
		builder.vertex(
				matrices.peek().getModel(), (float) (0),
				(float) ( + 1),
				(float) ( + 1)
		).next();
		builder.vertex(
				matrices.peek().getModel(), (float) (0),
				(float) ( - 1),
				(float) (+ 1)
		).next();
		tessellator.draw();
	}
}
