package dev.cammiescorner.arcanus.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.ManaType;
import dev.cammiescorner.arcanus.common.items.StaffItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

public class ManaBarOverlay {

	private static final ResourceLocation OVERLAY_TEXTURE = Arcanus.id("textures/gui/hud/mana_bars.png");
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
			int x = ArcanusConfig.rightSideManaBars.mirror() ? scaledWidth - 29 : 0;
			int y = ArcanusConfig.manaBarsOnTop ? 11 : scaledHeight - 18;
			float startingAngle;

			if(ArcanusConfig.manaBarsOnTop) {
				if(ArcanusConfig.rightSideManaBars.mirror())
					startingAngle = 189;
				else
					startingAngle = -9f;
			}
			else {
				if(ArcanusConfig.rightSideManaBars.mirror())
					startingAngle = -81f;
				else
					startingAngle = -99f;
			}

			float angleOffset = ArcanusConfig.rightSideManaBars.mirror() ? -27f : 27f;
			poseStack.translate(x, y, 0);
			poseStack.scale(0.225f, 0.225f, 1f);

			// render mana bars
			for(var manaType : ManaType.values()) {
				Color color = manaType.getColor();

				poseStack.pushPose();
				x = ArcanusConfig.rightSideManaBars.mirror() ? 68 : 60;
				y = ArcanusConfig.manaBarsOnTop ? 12 : 20;
				poseStack.translate(x, y, 0);
				poseStack.mulPose(Axis.ZP.rotationDegrees(startingAngle + angleOffset * manaType.ordinal()));
				poseStack.translate(-8, -8, 0);

				double maxMana = ArcanusComponents.getMaxMana(player, manaType);
				double mana = ArcanusComponents.getMana(player, manaType);
				double ratio = Math.min(1f, maxMana <= 0f ? 0f : (mana / maxMana));
				double halfNHalf = ArcanusConfig.scaleManaBarsWithMaxMana ? (Math.min(maxMana, ArcanusConfig.manaBarsMaxLength) - 12) / 2f : (35 - 6);
				int bottomMana = (int) (halfNHalf * Math.clamp(ratio / 0.44f, 0f, 1f));
				int switchMana = (int) (12 * (ratio <= 0.56f ? Math.clamp((ratio - 0.44f) / 0.12f, 0f, 1f) : 1f));
				int topMana = (int) (halfNHalf * Math.clamp((ratio - 0.56f) / 0.44f, 0f, 1f));

				RenderSystem.setShaderColor(color.redF(), color.greenF(), color.blueF(), alpha);
				guiGraphics.blit(OVERLAY_TEXTURE, 85, 0, 0, 200, bottomMana, 16);
				guiGraphics.blit(OVERLAY_TEXTURE, (int) (85 + halfNHalf), 0, 128, 32, switchMana, 16);
				guiGraphics.blit(OVERLAY_TEXTURE, (int) (85 + halfNHalf + 12), 0, (int) (256 - halfNHalf), 216, topMana, 16);

				poseStack.translate(-8, -8, 0);
				halfNHalf = 14 + halfNHalf;

				RenderSystem.setShaderColor(1f, 1f, 1f, alpha);
				guiGraphics.blit(OVERLAY_TEXTURE, 80, 0, 0, 128, (int) halfNHalf, 32);
				guiGraphics.blit(OVERLAY_TEXTURE, (int) (80 + halfNHalf), 0, 128, 0, 10, 32);
				guiGraphics.blit(OVERLAY_TEXTURE, (int) (80 + halfNHalf + 10), 0, (int) (256 - halfNHalf), 160, (int) halfNHalf, 32);

				poseStack.popPose();
			}

			// render frame
			guiGraphics.blit(OVERLAY_TEXTURE, 0, -48, 0, 0, 128, 128);

			poseStack.popPose();

			x = ArcanusConfig.rightSideManaBars.mirror() ? scaledWidth - 21 : 8;
			y = ArcanusConfig.manaBarsOnTop ? 8 : scaledHeight - 21;

			poseStack.pushPose();
			poseStack.translate(x, y, 0);
			poseStack.scale(0.8f, 0.8f, 1f);

			guiGraphics.renderItem(Arcanus.getActiveSpellBook(player), 0, 0);

			poseStack.popPose();

			RenderSystem.disableBlend();
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		}
	}
}
