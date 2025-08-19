package dev.cammiescorner.arcanus.client.renderer.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.model.entity.living.ArcanistModel;
import dev.cammiescorner.arcanus.client.renderer.layer.ArcanistHeldItemLayerRenderer;
import dev.cammiescorner.arcanus.common.entity.living.Arcanist;
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

public class ArcanistRenderer extends MobRenderer<Arcanist, ArcanistModel> {
	public static final ResourceLocation TEXTURE = Arcanus.id("textures/entity/living/arcanist.png");
	public static final ResourceLocation ROBES_TEXTURE = Arcanus.id("textures/entity/living/arcanist_overlay.png");

	public ArcanistRenderer(EntityRendererProvider.Context context) {
		super(context, new ArcanistModel(Minecraft.getInstance().getEntityModels().bakeLayer(ArcanistModel.MODEL_LAYER)), 0.6f);
		addLayer(new ArcanistHeldItemLayerRenderer<>(this, context.getItemInHandRenderer()));
	}

	@Override
	public void render(Arcanist arcanist, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource vertices, int light) {
		super.render(arcanist, yaw, tickDelta, poseStack, vertices, light);

		int hexColor = arcanist.getRobeColor();

		if(arcanist.hasCustomName() && arcanist.getName().getString().equals("jeb_")) {
			int interval = 15;
			int idfk = arcanist.tickCount / interval + arcanist.getId();
			int colorCount = DyeColor.values().length;
			float f = ((arcanist.tickCount % interval) + tickDelta) / 15f;
			int color1 = Sheep.getColor(DyeColor.byId(idfk % colorCount));
			int color2 = Sheep.getColor(DyeColor.byId((idfk + 1) % colorCount));
			hexColor = FastColor.ARGB32.lerp(f, color1, color2);
		}

		poseStack.pushPose();
		setupRotations(arcanist, poseStack, 0, Mth.rotLerp(tickDelta, arcanist.yBodyRotO, arcanist.yBodyRot), tickDelta, 1f);
		poseStack.scale(-1f, -1f, 1f);
		scale(arcanist, poseStack, tickDelta);
		poseStack.translate(0.0, -1.5, 0.0);
		model.renderToBuffer(poseStack, vertices.getBuffer(RenderType.entityCutout(ROBES_TEXTURE)), light, OverlayTexture.NO_OVERLAY, hexColor);
		poseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(Arcanist entity) {
		return TEXTURE;
	}
}
