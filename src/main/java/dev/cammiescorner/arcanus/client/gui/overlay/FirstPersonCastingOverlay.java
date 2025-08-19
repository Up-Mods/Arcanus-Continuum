package dev.cammiescorner.arcanus.client.gui.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.Pattern;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.List;

public class FirstPersonCastingOverlay {
	private static final ResourceLocation MAGIC_CIRCLES = Arcanus.id("textures/entity/feature/magic_circles.png");

	public static void render(GuiGraphics guiGraphics, DeltaTracker tickDelta, LocalPlayer player) {
		Minecraft client = Minecraft.getInstance();
		if(!client.gameRenderer.getMainCamera().isDetached() && !ArcanusClient.FIRST_PERSON_MODEL_ENABLED.getAsBoolean()) {
			PoseStack poseStack = guiGraphics.pose();
			List<Pattern> list = ArcanusComponents.getPattern(player);

			if(!list.isEmpty()) {
				MultiBufferSource.BufferSource vertices = client.renderBuffers().bufferSource();
				RenderType renderLayer = ArcanusClient.getMagicCircles(MAGIC_CIRCLES);
				VertexConsumer vertex = vertices.getBuffer(renderLayer);
				Color color = ArcanusHelper.getMagicColor(player);
				float x = client.getWindow().getGuiScaledWidth() / 2f;
				float y = client.getWindow().getGuiScaledHeight() / 2f;
				float scale = 3f;

				poseStack.pushPose();
				poseStack.translate(x, y, 0);

				for(int i = 0; i < list.size(); i++) {
					Pattern pattern = list.get(i);
					poseStack.pushPose();

					if(i == 1)
						poseStack.mulPose(Axis.ZP.rotationDegrees((player.tickCount + player.getId() + tickDelta.getGameTimeDeltaTicks()) * (5 + (2.5f * i))));
					else
						poseStack.mulPose(Axis.ZN.rotationDegrees((player.tickCount + player.getId() + tickDelta.getGameTimeDeltaTicks()) * (5 + (2.5f * i))));

					poseStack.scale(scale, scale, 0);
					poseStack.translate(-8.5, -8.5, 0);
					// TODO inline these draw calls
					drawTexture(vertex, poseStack, color, 0, 0, i * 34, pattern == Pattern.LEFT ? 0 : 24, 17, 17, 128, 48);
					poseStack.popPose();
				}

				poseStack.popPose();
				vertices.endLastBatch();
			}
		}
	}

	private static void drawTexture(VertexConsumer vertex, PoseStack poseStack, Color color, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
		drawTexturedQuad(vertex, poseStack.last().pose(), color, x, x + width, y, y + height, u / (float) textureWidth, (u + width) / (float) textureWidth, v / (float) textureHeight, (v + height) / (float) textureHeight);
	}

	private static void drawTexturedQuad(VertexConsumer vertex, Matrix4f matrix, Color color, int x0, int x1, int y0, int y1, float u0, float u1, float v0, float v1) {
		vertex.addVertex(matrix, x0, y1, 0).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(u0, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
		vertex.addVertex(matrix, x1, y1, 0).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(u1, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
		vertex.addVertex(matrix, x1, y0, 0).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(u1, v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
		vertex.addVertex(matrix, x0, y0, 0).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(u0, v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
	}
}
