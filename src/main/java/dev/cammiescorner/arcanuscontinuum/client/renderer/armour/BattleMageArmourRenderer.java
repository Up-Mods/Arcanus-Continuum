package dev.cammiescorner.arcanuscontinuum.client.renderer.armour;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.models.armour.BattleMageArmourModel;
import dev.cammiescorner.arcanuscontinuum.common.items.BattleMageArmorItem;
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

public class BattleMageArmourRenderer implements ArmorRenderer {
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final Identifier[] mainTextures = {
		Arcanus.id("textures/entity/armor/battle_mage_armor_stage_0.png"),
		Arcanus.id("textures/entity/armor/battle_mage_armor_stage_1.png"),
		Arcanus.id("textures/entity/armor/battle_mage_armor_stage_2.png"),
		Arcanus.id("textures/entity/armor/battle_mage_armor_stage_3.png")
	};
	private final Identifier overlayTexture = Arcanus.id("textures/entity/armor/battle_mage_armor_overlay.png");
	private BattleMageArmourModel<LivingEntity> model;

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
		if(model == null)
			model = new BattleMageArmourModel<>(client.getEntityModelLoader().getModelPart(BattleMageArmourModel.MODEL_LAYER));

		if(stack.getItem() instanceof BattleMageArmorItem battleMageArmorItem) {
			Identifier mainTexture = mainTextures[battleMageArmorItem.getOxidation(stack).ordinal()];
			int hexColour = battleMageArmorItem.getColor(stack);
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
			model.armorHead.visible = slot == EquipmentSlot.HEAD;
			model.armorBody.visible = slot == EquipmentSlot.CHEST;
			model.surcoatFront.visible = slot == EquipmentSlot.CHEST;
			model.surcoatBack.visible = slot == EquipmentSlot.CHEST;
			model.armorRightArm.visible = slot == EquipmentSlot.CHEST;
			model.armorLeftArm.visible = slot == EquipmentSlot.CHEST;
			model.armorRightLeg.visible = slot == EquipmentSlot.LEGS;
			model.armorLeftLeg.visible = slot == EquipmentSlot.LEGS;
			model.armorRightBoot.visible = slot == EquipmentSlot.FEET;
			model.armorLeftBoot.visible = slot == EquipmentSlot.FEET;

			model.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(mainTexture), false, false), light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
			model.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(overlayTexture), false, false), light, OverlayTexture.DEFAULT_UV, r, g, b, 1f);
		}
	}
}
