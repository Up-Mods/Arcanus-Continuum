package dev.cammiescorner.arcanus.client.renderer.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.models.entity.living.WizardModel;
import dev.cammiescorner.arcanus.client.renderer.feature.WizardHeldItemFeatureRenderer;
import dev.cammiescorner.arcanus.common.entities.living.Wizard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class WizardRenderer extends MobRenderer<Wizard, WizardModel> {
	public static final ResourceLocation TEXTURE = Arcanus.id("textures/entity/living/wizard.png");
	public static final ResourceLocation ROBES_TEXTURE = Arcanus.id("textures/entity/living/wizard_overlay.png");

	public WizardRenderer(EntityRendererProvider.Context context) {
		super(context, new WizardModel(Minecraft.getInstance().getEntityModels().bakeLayer(WizardModel.MODEL_LAYER)), 0.6F);
		addLayer(new WizardHeldItemFeatureRenderer<>(this, context.getItemInHandRenderer()));
	}

	@Override
	public void render(Wizard wizard, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertices, int light) {
		super.render(wizard, yaw, tickDelta, matrices, vertices, light);

		int hexColor = wizard.getRobeColor(); // TODO probably need to add alpha

		if(wizard.hasCustomName() && wizard.getName().getString().equals("jeb_")) {
			int interval = 15;
			int idfk = wizard.tickCount / interval + wizard.getId();
			int colorCount = DyeColor.values().length;
			float f = ((wizard.tickCount % interval) + tickDelta) / 15f;
			int color1 = Sheep.getColor(DyeColor.byId(idfk % colorCount));
			int color2 = Sheep.getColor(DyeColor.byId((idfk + 1) % colorCount));
			hexColor = FastColor.ARGB32.lerp(f, color1, color2);
		}

		matrices.pushPose();
		setupRotations(wizard, matrices, 0, Mth.rotLerp(tickDelta, wizard.yBodyRotO, wizard.yBodyRot), tickDelta, 1f);
		matrices.scale(-1.0F, -1.0F, 1.0F);
		scale(wizard, matrices, tickDelta);
		matrices.translate(0.0, -1.5, 0.0);
		model.renderToBuffer(matrices, vertices.getBuffer(RenderType.entityCutout(ROBES_TEXTURE)), light, OverlayTexture.NO_OVERLAY, hexColor);
		matrices.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(Wizard entity) {
		return TEXTURE;
	}
}
