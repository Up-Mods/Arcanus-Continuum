package dev.cammiescorner.arcanus.client.renderer.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.common.entity.living.Cultist;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class CultistRenderer<T extends Cultist> extends HumanoidMobRenderer<T, PlayerModel<T>> {
	public CultistRenderer(EntityRendererProvider.Context context, boolean slim) {
		super(context, new PlayerModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim), 0.5f);
		addLayer(new HumanoidArmorLayer<>(
			this,
			new HumanoidArmorModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)),
			new HumanoidArmorModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR)),
			context.getModelManager()
		));
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	protected boolean shouldShowName(Cultist entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(Cultist entity) {
		return entity.getSkin().texture();
	}
}
