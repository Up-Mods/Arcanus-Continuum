package dev.cammiescorner.arcanus.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.common.item.StaffItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ArcanaBarOverlay {
	private static final ResourceLocation OVERLAY_TEXTURE = Arcanus.id("textures/gui/hud/arcana_bars.png");
	private static int hudTimer;

	public static void render(GuiGraphics guiGraphics, DeltaTracker tickDelta, LocalPlayer player) {
		if(player.getMainHandItem().getItem() instanceof StaffItem)
			hudTimer = Math.min(hudTimer + 1, 40);
		else
			hudTimer = Math.max(hudTimer - 1, 0);

		if(hudTimer > 0) {
			Minecraft client = Minecraft.getInstance();
			PoseStack poseStack = guiGraphics.pose();
			int scaledHeight = client.getWindow().getGuiScaledHeight();
			int scaledWidth = client.getWindow().getGuiScaledWidth();
			float alpha = hudTimer > 20 ? 1f : ((hudTimer + tickDelta.getRealtimeDeltaTicks()) / 20f);

			RenderSystem.enableBlend();

			poseStack.pushPose();
			poseStack.translate(0, 11, 0);

			if(ArcanusConfig.ClientStuff.rightSideArcanaBars.mirror())
				poseStack.translate(scaledWidth - 29, 0, 0);
			if(!ArcanusConfig.ClientStuff.arcanaBarsOnTop)
				poseStack.translate(0, scaledHeight - 29, 0);

			// render frame
			poseStack.pushPose();

			var scale = 0.225f;
			poseStack.scale(scale, scale, 1f);
			RenderSystem.setShaderColor(1f, 1f, 1f, alpha);
			guiGraphics.blit(OVERLAY_TEXTURE, 0, -48, 0, 0, 128, 128);
			poseStack.popPose();

			// render pouch
			ItemStack spellBook = Arcanus.getActiveSpellBook(player);

			if(!spellBook.isEmpty()) {
				poseStack.pushPose();
				poseStack.translate(8, -2, 0);
				poseStack.scale(0.8f, 0.8f, 1f);

				RenderSystem.setShaderColor(1f, 1f, 1f, alpha);
				guiGraphics.renderItem(spellBook, 0, 0);

				poseStack.popPose();
			}

			// render arcana bars
			List<PrimalArcana> list = ArcanusArcana.primalArcana().toList();
			float angleOffsetDegrees = ArcanusConfig.ClientStuff.rightSideArcanaBars.mirror() ? -27f : 27f; // TODO base off of size of primal arcana list
			float startingAngleDegrees;

			if(ArcanusConfig.ClientStuff.arcanaBarsOnTop) {
				if(ArcanusConfig.ClientStuff.rightSideArcanaBars.mirror())
					startingAngleDegrees = 189;
				else
					startingAngleDegrees = -9f;
			}
			else {
				if(ArcanusConfig.ClientStuff.rightSideArcanaBars.mirror())
					startingAngleDegrees = -81f;
				else
					startingAngleDegrees = -99f;
			}

			for(int i = 0; i < list.size(); i++) {
				PrimalArcana arcanaType = list.get(i);
				Color color = arcanaType.color();
				double maxArcana = ArcanusComponents.getMaxArcana(player, arcanaType);
				double arcana = ArcanusComponents.getArcana(player, arcanaType);

				int x = ArcanusConfig.ClientStuff.rightSideArcanaBars.mirror() ? 68 : 60;
				int y = ArcanusConfig.ClientStuff.arcanaBarsOnTop ? 12 : 20;
				var angle = (float) Math.toRadians(startingAngleDegrees + angleOffsetDegrees * i);

				poseStack.pushPose();
				poseStack.scale(scale, scale, 1f);
				poseStack.translate(x, y, 0);

				poseStack.mulPose(Axis.ZP.rotation(angle));
				poseStack.translate(-8, -8, 0);

				double ratio = Math.min(1f, maxArcana <= 0f ? 0f : (arcana / maxArcana));
				double halfNHalf = ArcanusConfig.ClientStuff.scaleArcanaBarsWithMaxArcana ? (Math.clamp(maxArcana, Math.min(ArcanusConfig.ClientStuff.arcanaBarsMinLength, ArcanusConfig.ClientStuff.arcanaBarsMaxLength), ArcanusConfig.ClientStuff.arcanaBarsMaxLength) - 12) / 2f : (35 - 6);
				int bottomArcana = (int) (halfNHalf * Math.clamp(ratio / 0.44f, 0f, 1f));
				int switchArcana = (int) (12 * (ratio <= 0.56f ? Math.clamp((ratio - 0.44f) / 0.12f, 0f, 1f) : 1f)); // FIXME fills at a different speed than the rest of the bar
				int topArcana = (int) (halfNHalf * Math.clamp((ratio - 0.56f) / 0.44f, 0f, 1f));

				RenderSystem.setShaderColor(color.redF(), color.greenF(), color.blueF(), alpha);
				guiGraphics.blit(OVERLAY_TEXTURE, 85, 0, 0, 200, bottomArcana, 16);
				guiGraphics.blit(OVERLAY_TEXTURE, (int) (85 + halfNHalf), 0, 128, 32, switchArcana, 16);
				guiGraphics.blit(OVERLAY_TEXTURE, (int) (85 + halfNHalf + 12), 0, (int) (256 - halfNHalf), 216, topArcana, 16);

				poseStack.translate(-8, -8, 0);
				halfNHalf = 14 + halfNHalf;

				RenderSystem.setShaderColor(1f, 1f, 1f, alpha);
				guiGraphics.blit(OVERLAY_TEXTURE, 80, 0, 0, 128, (int) halfNHalf, 32);
				guiGraphics.blit(OVERLAY_TEXTURE, (int) (80 + halfNHalf), 0, 128, 0, 10, 32);
				guiGraphics.blit(OVERLAY_TEXTURE, (int) (80 + halfNHalf + 10), 0, (int) (256 - halfNHalf), 160, (int) halfNHalf, 32);

				poseStack.popPose();

				if(ArcanusConfig.ClientStuff.numericalArcanaDisplay) {
					poseStack.pushPose();

					double offset = 24 + Math.min(ArcanusConfig.ClientStuff.arcanaBarsMaxLength, maxArcana) * scale;
					poseStack.translate(2f, -2f, 0f);
					poseStack.translate(x * scale + Mth.cos(angle) * offset, y * scale + Mth.sin(angle) * offset, 0);
					poseStack.scale(0.5f, 0.5f, 1f);

					guiGraphics.drawCenteredString(client.font, String.valueOf(Mth.floor(arcana)), 0, 0, color.asIntARGB());
					poseStack.popPose();
				}
			}

			poseStack.popPose();

			RenderSystem.disableBlend();
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		}
	}
}
