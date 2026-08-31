package dev.cammiescorner.arcanus.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class StunOverlay {
	private static final ResourceLocation OVERLAY_TEXTURE = Arcanus.id("textures/gui/hud/stunned_vignette.png");

	public static void render(GuiGraphics guiGraphics, DeltaTracker tickDelta, LocalPlayer player) {
		int stunTimer = ArcanusComponents.getStunTimer(player);

		if(ArcanusComponents.isStunned(player)) {
			if(stunTimer > 5)
				StunOverlay.renderOverlay(guiGraphics, Math.min(1f, 0.5f + (stunTimer % 5f) / 10f));
			else
				StunOverlay.renderOverlay(guiGraphics, Math.min(1f, stunTimer / 5f));
		}
	}


	public static void renderOverlay(GuiGraphics guiGraphics, float opacity) {
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1f, 1f, 1f, opacity);
		RenderSystem.setShaderTexture(0, OVERLAY_TEXTURE);

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

		bufferBuilder.addVertex(0f, guiGraphics.guiHeight(), -90f).setUv(0f, 1f);
		bufferBuilder.addVertex(guiGraphics.guiWidth(), guiGraphics.guiHeight(), -90f).setUv(1f, 1f);
		bufferBuilder.addVertex(guiGraphics.guiWidth(), 0f, -90f).setUv(1f, 0f);
		bufferBuilder.addVertex(0f, 0f, -90f).setUv(0f, 0f);

		BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
	}
}
