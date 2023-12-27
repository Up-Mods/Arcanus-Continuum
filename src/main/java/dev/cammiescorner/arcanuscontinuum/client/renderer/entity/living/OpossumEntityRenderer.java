package dev.cammiescorner.arcanuscontinuum.client.renderer.entity.living;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.living.OpossumEntityModel;
import dev.cammiescorner.arcanuscontinuum.common.entities.living.OpossumEntity;
import dev.cammiescorner.arcanuscontinuum.common.items.WizardArmorItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class OpossumEntityRenderer extends MobEntityRenderer<OpossumEntity, OpossumEntityModel> {
	public static final Identifier TEXTURE = Arcanus.id("textures/entity/living/opossum.png");
	public static final Identifier HAT_TEXTURE = Arcanus.id("textures/entity/living/opossum_hat.png");

	public OpossumEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new OpossumEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(OpossumEntityModel.MODEL_LAYER)), 0.3F);
	}

	@Override
	public void render(OpossumEntity opossum, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider verteces, int i) {
		ItemStack hatStack = opossum.getEquippedStack(EquipmentSlot.HEAD);
		model.hat.visible = !hatStack.isEmpty();

		super.render(opossum, yaw, tickDelta, matrices, verteces, i);

		if(hatStack.getItem() instanceof WizardArmorItem wizardArmour) {
			int hexColour = wizardArmour.getColor(hatStack);
			float r = (hexColour >> 16 & 255) / 255F;
			float g = (hexColour >> 8 & 255) / 255F;
			float b = (hexColour & 255) / 255F;

			if(hatStack.hasCustomName() && hatStack.getName().getString().equals("jeb_")) {
				int m = 15;
				int n = opossum.age / m + opossum.getId();
				int o = DyeColor.values().length;
				float f = ((opossum.age % m) + tickDelta) / 15F;
				float[] fs = SheepEntity.getRgbColor(DyeColor.byId(n % o));
				float[] gs = SheepEntity.getRgbColor(DyeColor.byId((n + 1) % o));
				r = fs[0] * (1F - f) + gs[0] * f;
				g = fs[1] * (1F - f) + gs[1] * f;
				b = fs[2] * (1F - f) + gs[2] * f;
			}

			matrices.push();
			setupTransforms(opossum, matrices, 0, MathHelper.lerpAngleDegrees(tickDelta, opossum.prevBodyYaw, opossum.bodyYaw), tickDelta);
			matrices.scale(-1.0F, -1.0F, 1.0F);
			scale(opossum, matrices, tickDelta);
			matrices.translate(0.0, -1.5, 0.0);
			model.render(matrices, verteces.getBuffer(RenderLayer.getEntityCutout(HAT_TEXTURE)), i, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
			matrices.pop();
		}
	}

	@Override
	public Identifier getTexture(OpossumEntity entity) {
		return TEXTURE;
	}
}
