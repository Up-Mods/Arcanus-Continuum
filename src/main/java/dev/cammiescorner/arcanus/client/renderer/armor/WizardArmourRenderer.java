package dev.cammiescorner.arcanus.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.models.armor.WizardArmourModel;
import dev.cammiescorner.arcanus.common.items.WizardRobesArmorItem;
import dev.upcraft.sparkweave.api.client.render.CustomHumanoidModelArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class WizardArmourRenderer extends CustomHumanoidModelArmorRenderer<LivingEntity, HumanoidModel<LivingEntity>, WizardArmourModel<LivingEntity>> {
	private final Minecraft client = Minecraft.getInstance();
	private final ResourceLocation mainTexture = Arcanus.id("textures/entity/armor/wizard_robes.png");
	private final ResourceLocation overlayTexture = Arcanus.id("textures/entity/armor/wizard_robes_overlay.png");
	private final WizardArmourModel<LivingEntity> model;

	public WizardArmourRenderer(LivingEntity entity, EntityRendererProvider.Context context, RenderLayerParent<LivingEntity, ? extends EntityModel<?>> layerParent) {
		this.model = new WizardArmourModel<>(context.bakeLayer(WizardArmourModel.MODEL_LAYER));
	}

	@Override
	protected void setPartVisibility(WizardArmourModel<LivingEntity> model, LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		model.setAllVisible(true);
		model.wizardHat.visible = slot == EquipmentSlot.HEAD;
		model.robes.visible = slot == EquipmentSlot.CHEST;
		model.rightSleeve.visible = slot == EquipmentSlot.CHEST;
		model.leftSleeve.visible = slot == EquipmentSlot.CHEST;
		model.rightPants.visible = slot == EquipmentSlot.LEGS;
		model.leftPants.visible = slot == EquipmentSlot.LEGS;
		model.rightBoot.visible = slot == EquipmentSlot.FEET;
		model.leftBoot.visible = slot == EquipmentSlot.FEET;
	}

	@Override
	protected WizardArmourModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		return model;
	}

	@Override
	protected void renderModelPart(PoseStack matrices, MultiBufferSource bufferSource, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, int dyeColor, HumanoidModel<LivingEntity> contextModel, WizardArmourModel<LivingEntity> armorModel) {
		if(stack.getItem() instanceof WizardRobesArmorItem wizardArmor) {
			int hexColor = wizardArmor.getColor(stack);

			if(stack.has(DataComponents.CUSTOM_NAME) && stack.getHoverName().getString().equals("jeb_")) {
				int interval = 15;
				int idfk = entity.tickCount / interval + entity.getId();
				int colorCount = DyeColor.values().length;
				float f = ((entity.tickCount % interval) + client.getFrameTimeNs()) / 15f;
				int color1 = Sheep.getColor(DyeColor.byId(idfk % colorCount));
				int color2 = Sheep.getColor(DyeColor.byId((idfk + 1) % colorCount));
				hexColor = FastColor.ARGB32.lerp(f, color1, color2);
			}

			model.renderToBuffer(matrices, ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.armorCutoutNoCull(mainTexture), false), light, OverlayTexture.NO_OVERLAY, hexColor);
			model.renderToBuffer(matrices, ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.armorCutoutNoCull(overlayTexture), false), light, OverlayTexture.NO_OVERLAY, 0xffffffff);
		}
	}

	@Override
	protected ResourceLocation getTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		return mainTexture;
	}
}
