package dev.cammiescorner.arcanuscontinuum.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.SpellcraftScreen;
import dev.cammiescorner.arcanuscontinuum.client.models.armour.WizardArmourModel;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.OpossumEntityModel;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.WizardEntityModel;
import dev.cammiescorner.arcanuscontinuum.client.models.feature.SpellPatternModel;
import dev.cammiescorner.arcanuscontinuum.client.renderer.armour.WizardArmourRenderer;
import dev.cammiescorner.arcanuscontinuum.client.renderer.entity.OpossumEntityRenderer;
import dev.cammiescorner.arcanuscontinuum.client.renderer.entity.WizardEntityRenderer;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.joml.Vector3f;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

import java.util.function.Function;

public class ArcanusClient implements ClientModInitializer {
	private static final Identifier HUD_ELEMENTS = Arcanus.id("textures/gui/hud/mana_bar.png");
	private static final Function<Identifier, RenderLayer> MAGIC_CIRCLES = Util.memoize(texture -> {
		RenderPhase.Texture texturing = new RenderPhase.Texture(texture, false, false);

		return RenderLayer.of(
				Arcanus.id("magic_circle").toString(),
				VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
				VertexFormat.DrawMode.QUADS,
				256,
				false,
				true,
				RenderLayer.MultiPhaseParameters.builder()
						.shader(RenderLayer.ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
						.texture(texturing)
						.overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
						.transparency(RenderLayer.ADDITIVE_TRANSPARENCY)
						.writeMaskState(RenderLayer.ALL_MASK)
						.build(false)
		);
	});

	@Override
	public void onInitializeClient(ModContainer mod) {
		HandledScreens.register(ArcanusScreenHandlers.SPELLCRAFT_SCREEN_HANDLER, SpellcraftScreen::new);

		EntityModelLayerRegistry.registerModelLayer(WizardArmourModel.MODEL_LAYER, WizardArmourModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(WizardEntityModel.MODEL_LAYER, WizardEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(OpossumEntityModel.MODEL_LAYER, OpossumEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SpellPatternModel.MODEL_LAYER, SpellPatternModel::getTexturedModelData);

		ArmorRenderer.register(new WizardArmourRenderer(), ArcanusItems.WIZARD_HAT, ArcanusItems.WIZARD_ROBES, ArcanusItems.WIZARD_PANTS, ArcanusItems.WIZARD_BOOTS);
		EntityRendererRegistry.register(ArcanusEntities.WIZARD, WizardEntityRenderer::new);
		EntityRendererRegistry.register(ArcanusEntities.OPOSSUM, OpossumEntityRenderer::new);

		BlockRenderLayerMap.put(RenderLayer.getCutout(), ArcanusBlocks.MAGIC_DOOR);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableItem) stack.getItem()).getColor(stack) : 0xffffff,
				ArcanusItems.WOODEN_STAFF, ArcanusItems.AMETHYST_SHARD_STAFF, ArcanusItems.QUARTZ_SHARD_STAFF,
				ArcanusItems.ENDER_SHARD_STAFF, ArcanusItems.ECHO_SHARD_STAFF
		);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableArmorItem) stack.getItem()).getColor(stack) : 0xffffff,
				ArcanusItems.WIZARD_HAT, ArcanusItems.WIZARD_ROBES, ArcanusItems.WIZARD_PANTS, ArcanusItems.WIZARD_BOOTS
		);

		final MinecraftClient client = MinecraftClient.getInstance();
		var obj = new Object() { int timer; };

		HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
			PlayerEntity player = client.player;

			if(player != null && !player.isSpectator()) {
				double maxMana = ArcanusComponents.getMaxMana(player);
				double mana = ArcanusComponents.getMana(player);
				double burnout = ArcanusComponents.getBurnout(player);
				double manaLock = ArcanusComponents.getManaLock(player);

				if(player.getMainHandStack().getItem() instanceof StaffItem || mana < maxMana)
					obj.timer = Math.min(obj.timer + 1, 40);
				else
					obj.timer = Math.max(obj.timer - 1, 0);

				if(obj.timer > 0) {
					int x = 0;
					int y = client.getWindow().getScaledHeight() - 28;
					int width = 96;
					float alpha = obj.timer > 20 ? 1F : obj.timer / 20F;

					RenderSystem.enableBlend();
					RenderSystem.setShaderTexture(0, HUD_ELEMENTS);
					RenderSystem.setShaderColor(1F, 1F, 1F, alpha);

					// render mana
					DrawableHelper.drawTexture(matrices, x, y + 5, 0, 32, (int) (width * (mana / maxMana)), 23, 256, 256);

					// render burnout
					int i = (int) Math.ceil(width * ((burnout + manaLock) / maxMana));
					DrawableHelper.drawTexture(matrices, x + (width - i), y + 5, width - i, 56, i, 23, 256, 256);

					// render mana lock
					i = (int) Math.ceil(width * (manaLock / maxMana));
					DrawableHelper.drawTexture(matrices, x + (width - i), y + 5, width - i, 80, i, 23, 256, 256);

					// render frame
					DrawableHelper.drawTexture(matrices, x, y, 0, 0, 101, 28, 256, 256);
				}
			}
		});
	}

	public static RenderLayer getMagicCircles(Identifier texture) {
		return MAGIC_CIRCLES.apply(texture);
	}

	public static Vector3f RGBtoHSB(int r, int g, int b) {
		float hue, saturation, brightness;
		int cmax = Math.max(r, g);
		if (b > cmax) cmax = b;
		int cmin = Math.min(r, g);
		if (b < cmin) cmin = b;

		brightness = ((float) cmax) / 255.0f;
		if (cmax != 0)
			saturation = ((float) (cmax - cmin)) / ((float) cmax);
		else
			saturation = 0;
		if (saturation == 0)
			hue = 0;
		else {
			float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
			float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
			float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
			if (r == cmax)
				hue = bluec - greenc;
			else if (g == cmax)
				hue = 2.0f + redc - bluec;
			else
				hue = 4.0f + greenc - redc;
			hue = hue / 6.0f;
			if (hue < 0)
				hue = hue + 1.0f;
		}

		return new Vector3f(hue, saturation, brightness);
	}
}
