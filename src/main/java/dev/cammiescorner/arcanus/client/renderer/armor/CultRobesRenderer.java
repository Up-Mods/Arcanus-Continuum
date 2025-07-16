package dev.cammiescorner.arcanus.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.client.model.armor.CultRobesModel;
import dev.cammiescorner.arcanus.common.entity.living.Cultist;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.upcraft.sparkweave.api.client.render.CustomHumanoidModelArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CultRobesRenderer extends CustomHumanoidModelArmorRenderer<LivingEntity, HumanoidModel<LivingEntity>, CultRobesModel<LivingEntity>> {
	private final CultRobesModel<LivingEntity> model;
	private final ResourceLocation texture;

	public CultRobesRenderer(EntityRendererProvider.Context context, ResourceLocation texture) {
		this.model = new CultRobesModel<>(context.bakeLayer(CultRobesModel.MODEL_LAYER));
		this.texture = texture;
	}

	@Override
	protected void renderModelPart(PoseStack matrices, MultiBufferSource bufferSource, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, int dyeColor, HumanoidModel<LivingEntity> contextModel, CultRobesModel<LivingEntity> armorModel) {
		super.renderModelPart(matrices, bufferSource, stack, entity, slot, light, dyeColor, contextModel, armorModel);
	}

	@Override
	protected void setPartVisibility(CultRobesModel<LivingEntity> model, LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		boolean isDown = stack.getOrDefault(ArcanusDataComponents.HOOD_DOWN.get(), false);
		boolean slim = false;

		if(entity instanceof AbstractClientPlayer player)
			slim = player.getSkin().model() == PlayerSkin.Model.SLIM;
		if(entity instanceof Cultist cultist)
			slim = cultist.getSkin().model() == PlayerSkin.Model.SLIM;

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
	protected CultRobesModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		return model;
	}

	@Override
	protected ResourceLocation getTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		return texture;
	}
}
