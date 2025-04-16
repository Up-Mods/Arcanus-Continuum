package dev.cammiescorner.arcanus.client.renderer.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.models.entity.living.OpossumModel;
import dev.cammiescorner.arcanus.common.entities.living.Opossum;
import dev.cammiescorner.arcanus.common.items.WizardRobesArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class OpossumRenderer extends MobRenderer<Opossum, OpossumModel> {
	public static final ResourceLocation TEXTURE = Arcanus.id("textures/entity/living/opossum.png");
	public static final ResourceLocation HAT_TEXTURE = Arcanus.id("textures/entity/living/opossum_hat.png");

	public OpossumRenderer(EntityRendererProvider.Context context) {
		super(context, new OpossumModel(Minecraft.getInstance().getEntityModels().bakeLayer(OpossumModel.MODEL_LAYER)), 0.3F);
	}

	@Override
	public void render(Opossum opossum, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource verteces, int i) {
		ItemStack hatStack = opossum.getItemBySlot(EquipmentSlot.HEAD);
		model.hat.visible = !hatStack.isEmpty();

		super.render(opossum, yaw, tickDelta, matrices, verteces, i);

		if(hatStack.getItem() instanceof WizardRobesArmorItem wizardArmour) {
			int hexColor = wizardArmour.getColor(hatStack); // TODO probably need to add alpha

			if(hatStack.has(DataComponents.CUSTOM_NAME) && hatStack.getHoverName().getString().equals("jeb_")) {
				int interval = 15;
				int idfk = opossum.tickCount / interval + opossum.getId();
				int colorCount = DyeColor.values().length;
				float f = ((opossum.tickCount % interval) + tickDelta) / 15f;
				int color1 = Sheep.getColor(DyeColor.byId(idfk % colorCount));
				int color2 = Sheep.getColor(DyeColor.byId((idfk + 1) % colorCount));
				hexColor = FastColor.ARGB32.lerp(f, color1, color2);
			}

			matrices.pushPose();
			setupRotations(opossum, matrices, 0, Mth.rotLerp(tickDelta, opossum.yBodyRotO, opossum.yBodyRot), tickDelta, 1f);
			matrices.scale(-1.0F, -1.0F, 1.0F);
			scale(opossum, matrices, tickDelta);
			matrices.translate(0.0, -1.5, 0.0);
			model.renderToBuffer(matrices, verteces.getBuffer(RenderType.entityCutout(HAT_TEXTURE)), i, OverlayTexture.NO_OVERLAY, hexColor);
			matrices.popPose();
		}
	}

	@Override
	public ResourceLocation getTextureLocation(Opossum entity) {
		return TEXTURE;
	}
}
