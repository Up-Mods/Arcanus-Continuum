package dev.cammiescorner.arcanuscontinuum.client.renderer.armour;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.models.armour.WizardArmourModel;
import dev.cammiescorner.arcanuscontinuum.common.items.WizardArmorItem;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class WizardArmourRenderer implements ArmorRenderer {
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final Identifier mainTexture = Arcanus.id("textures/entity/armor/wizard_robes.png");
	private final Identifier overlayTexture = Arcanus.id("textures/entity/armor/wizard_robes_overlay.png");
	private WizardArmourModel<LivingEntity> model;

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
		if(model == null)
			model = new WizardArmourModel<>(client.getEntityModelLoader().getModelPart(WizardArmourModel.MODEL_LAYER));

		if(stack.getItem() instanceof WizardArmorItem wizardArmour) {
			int hexColour = wizardArmour.getColor(stack);
			float r = (hexColour >> 16 & 255) / 255F;
			float g = (hexColour >> 8 & 255) / 255F;
			float b = (hexColour & 255) / 255F;

			if(stack.hasCustomName() && stack.getName().getString().equals("jeb_")) {
				int m = 15;
				int n = entity.age / m + entity.getId();
				int o = DyeColor.values().length;
				float f = ((entity.age % m) + client.getTickDelta()) / 15F;
				float[] fs = SheepEntity.getRgbColor(DyeColor.byId(n % o));
				float[] gs = SheepEntity.getRgbColor(DyeColor.byId((n + 1) % o));
				r = fs[0] * (1F - f) + gs[0] * f;
				g = fs[1] * (1F - f) + gs[1] * f;
				b = fs[2] * (1F - f) + gs[2] * f;
			}

			contextModel.setAttributes(model);
			model.setVisible(true);
			model.wizardHat.visible = slot == EquipmentSlot.HEAD;
			model.robes.visible = slot == EquipmentSlot.CHEST;
			model.rightSleeve.visible = slot == EquipmentSlot.CHEST;
			model.leftSleeve.visible = slot == EquipmentSlot.CHEST;
			model.rightPants.visible = slot == EquipmentSlot.LEGS;
			model.leftPants.visible = slot == EquipmentSlot.LEGS;
			model.rightBoot.visible = slot == EquipmentSlot.FEET;
			model.leftBoot.visible = slot == EquipmentSlot.FEET;

			model.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(mainTexture), false, false), light, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
			model.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(overlayTexture), false, false), light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1F);
		}
	}
}
