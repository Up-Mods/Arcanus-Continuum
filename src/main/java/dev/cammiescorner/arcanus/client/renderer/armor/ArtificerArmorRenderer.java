package dev.cammiescorner.arcanus.client.renderer.armor;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.model.armor.ArtificerArmorModel;
import dev.upcraft.sparkweave.api.client.render.CustomHumanoidModelArmorRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ArtificerArmorRenderer extends CustomHumanoidModelArmorRenderer<LivingEntity, HumanoidModel<LivingEntity>, ArtificerArmorModel<LivingEntity>> {
	private final ResourceLocation mainTexture = Arcanus.id("textures/entity/armor/artificer_armor.png");
	private final ArtificerArmorModel<LivingEntity> model;

	public ArtificerArmorRenderer(LivingEntity entity, EntityRendererProvider.Context context, RenderLayerParent<LivingEntity, ? extends EntityModel<?>> layerParent) {
		this.model = new ArtificerArmorModel<>(context.bakeLayer(ArtificerArmorModel.MODEL_LAYER));
	}

	@Override
	protected void setPartVisibility(ArtificerArmorModel<LivingEntity> model, HumanoidModel<LivingEntity> contextModel, LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		boolean slim = contextModel instanceof PlayerModel<LivingEntity> playerModel && playerModel.slim;

		model.setAllVisible(true);
		model.helmet.visible = slot == EquipmentSlot.HEAD;
		model.chestplate.visible = slot == EquipmentSlot.CHEST;
		model.rightSleeve.visible = slot == EquipmentSlot.CHEST && !slim;
		model.leftSleeve.visible = slot == EquipmentSlot.CHEST && !slim;
		model.rightSleeveSlim.visible = slot == EquipmentSlot.CHEST && slim;
		model.leftSleeveSlim.visible = slot == EquipmentSlot.CHEST && slim;
		model.rightPants.visible = slot == EquipmentSlot.LEGS;
		model.leftPants.visible = slot == EquipmentSlot.LEGS;
		model.rightBoots.visible = slot == EquipmentSlot.FEET;
		model.leftBoots.visible = slot == EquipmentSlot.FEET;
	}

	@Override
	protected ArtificerArmorModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		return model;
	}

	@Override
	protected ResourceLocation getTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		return mainTexture;
	}
}
