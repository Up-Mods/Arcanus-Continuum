package dev.cammiescorner.arcanuscontinuum.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.models.armour.WizardArmourModel;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.OpossumEntityModel;
import dev.cammiescorner.arcanuscontinuum.client.models.feature.OpossumHatModel;
import dev.cammiescorner.arcanuscontinuum.client.renderer.armour.WizardArmourRenderer;
import dev.cammiescorner.arcanuscontinuum.client.renderer.entity.OpossumEntityRenderer;
import dev.cammiescorner.arcanuscontinuum.client.screens.SpellcraftScreen;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusScreenHandlers;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ArcanusClient implements ClientModInitializer {
	private static final Identifier HUD_ELEMENTS = Arcanus.id("textures/gui/hud/mana_bar.png");

	@Override
	public void onInitializeClient(ModContainer mod) {
		HandledScreens.register(ArcanusScreenHandlers.SPELLCRAFT_SCREEN_HANDLER, SpellcraftScreen::new);

		EntityModelLayerRegistry.registerModelLayer(WizardArmourModel.MODEL_LAYER, WizardArmourModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(OpossumEntityModel.MODEL_LAYER, OpossumEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(OpossumHatModel.MODEL_LAYER, OpossumHatModel::getTexturedModelData);

		ArmorRenderer.register(new WizardArmourRenderer(), ArcanusItems.WIZARD_HAT, ArcanusItems.WIZARD_ROBES, ArcanusItems.WIZARD_PANTS, ArcanusItems.WIZARD_BOOTS);
		EntityRendererRegistry.register(ArcanusEntities.OPOSSUM, OpossumEntityRenderer::new);

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
}
