package dev.cammiescorner.arcanus.client.renderer.armor;

import dev.cammiescorner.arcanus.client.model.armor.CultistRobesModel;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.upcraft.sparkweave.api.client.render.CustomHumanoidModelArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CultRobesRenderer extends CustomHumanoidModelArmorRenderer<LivingEntity, HumanoidModel<LivingEntity>, CultistRobesModel<LivingEntity>> {
	private final CultistRobesModel<LivingEntity> model;
	private final ResourceLocation texture;

	public CultRobesRenderer(EntityRendererProvider.Context context, ResourceLocation texture) {
		this.model = new CultistRobesModel<>(context.bakeLayer(CultistRobesModel.MODEL_LAYER));
		this.texture = texture;
	}

	@Override
	protected void setPartVisibility(CultistRobesModel<LivingEntity> model, HumanoidModel<LivingEntity> contextModel, LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		boolean isDown = stack.getOrDefault(ArcanusDataComponents.HOOD_DOWN.get(), false);
		boolean slim = contextModel instanceof PlayerModel<LivingEntity> playerModel && playerModel.slim;

		model.setAllVisible(true);
		model.closedHood.visible = !isDown && slot == EquipmentSlot.HEAD;
		model.openHood.visible = isDown && slot == EquipmentSlot.HEAD;
		model.cloak.visible = slot == EquipmentSlot.HEAD;
		model.undershirt.visible = slot == EquipmentSlot.CHEST;
		model.garb.visible = slot == EquipmentSlot.CHEST;
		model.rightSleeve.visible = slot == EquipmentSlot.CHEST && !slim;
		model.leftSleeve.visible = slot == EquipmentSlot.CHEST && !slim;
		model.rightSleeveSlim.visible = slot == EquipmentSlot.CHEST && slim;
		model.leftSleeveSlim.visible = slot == EquipmentSlot.CHEST && slim;
		model.belt.visible = slot == EquipmentSlot.LEGS;
		model.rightLegSleeve.visible = slot == EquipmentSlot.LEGS;
		model.leftLegSleeve.visible = slot == EquipmentSlot.LEGS;
		model.rightShoe.visible = slot == EquipmentSlot.FEET;
		model.leftShoe.visible = slot == EquipmentSlot.FEET;
	}

	@Override
	protected CultistRobesModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		return model;
	}

	@Override
	protected ResourceLocation getTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		return texture;
	}
}
