package dev.cammiescorner.arcanuscontinuum.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.PocketDimensionPortalEntityModel;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.PocketDimensionPortalEntity;
import net.minecraft.client.MinecraftClient;
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
		RenderLayer layer = ArcanusClient.getMagicCircles(TEXTURE);
		int colour = entity.getColour();
		float r = (colour >> 16 & 255) / 255F;
		float g = (colour >> 8 & 255) / 255F;
		float b = (colour & 255) / 255F;
		float maxScale = 0.3f;
		float scale = entity.getTrueAge() <= 100 ? (entity.getTrueAge() / 100f) * maxScale : entity.getTrueAge() > 700 ? (1 - (entity.getTrueAge() - 700) / 20f) * maxScale : maxScale;

		//\\ Thank you Lyzantra! This entire effect was thanks to him! \\//
//		StencilBuffer stencilBuffer = ((StencilBuffer) client.getFramebuffer());

		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_INCR);
		GL11.glStencilFunc(GL11.GL_EQUAL, 0, 0xFF);
		GL11.glStencilMask(0xFF);
		drawStencil(matrices, client.gameRenderer.getCamera().getPos(), entity, tessellator);
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
		GL11.glStencilMask(0x00);

//		if(!stencilBuffer.arcanuscontinuum$isStencilBufferEnabled())
//			stencilBuffer.arcanuscontinuum$enableStencilBufferAndReload(true);

		matrices.push();
//		RenderSystem.disableDepthTest();
		matrices.multiply(Axis.X_POSITIVE.rotationDegrees(180));
		matrices.translate(0, -0.3f, 0);
		matrices.scale(scale, scale, scale);
		model.render(matrices, vertices.getBuffer(layer), light, OverlayTexture.DEFAULT_UV, r, g, b, 1f);
//		RenderSystem.enableDepthTest();
		matrices.pop();

		GL11.glClearStencil(GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_DECR);
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
		GL11.glStencilMask(0xFF);
		drawStencil(matrices, client.gameRenderer.getCamera().getPos(), entity, tessellator);
		GL11.glStencilFunc(GL11.GL_EQUAL, 0, 0xFF);
		GL11.glStencilMask(0x00);
		GL11.glDisable(GL11.GL_STENCIL_TEST);
	}

	@Override
	public Identifier getTexture(PocketDimensionPortalEntity entity) {
		return TEXTURE;
	}

	public static void drawStencil(MatrixStack matrices, Vec3d cameraPos, PocketDimensionPortalEntity portal, Tessellator tessellator) {
		BufferBuilder builder = tessellator.getBufferBuilder();
		builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
		builder.vertex(
				matrices.peek().getModel(), (float) (portal.getPos().x - cameraPos.x),
				(float) (portal.getPos().y - cameraPos.y - 1),
				(float) (portal.getPos().z - cameraPos.z - 1)
		).next();
		builder.vertex(
				matrices.peek().getModel(), (float) (portal.getPos().x - cameraPos.x),
				(float) (portal.getPos().y - cameraPos.y + 1),
				(float) (portal.getPos().z - cameraPos.z - 1)
		).next();
		builder.vertex(
				matrices.peek().getModel(), (float) (portal.getPos().x - cameraPos.x),
				(float) (portal.getPos().y - cameraPos.y + 1),
				(float) (portal.getPos().z - cameraPos.z + 1)
		).next();
		builder.vertex(
				matrices.peek().getModel(), (float) (portal.getPos().x - cameraPos.x),
				(float) (portal.getPos().y - cameraPos.y - 1),
				(float) (portal.getPos().z - cameraPos.z + 1)
		).next();
		tessellator.draw();
	}
}
