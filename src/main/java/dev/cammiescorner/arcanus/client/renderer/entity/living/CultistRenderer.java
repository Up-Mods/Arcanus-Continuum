package dev.cammiescorner.arcanus.client.renderer.entity.living;

import dev.cammiescorner.arcanus.common.entity.living.Cultist;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;

// TODO this doesnt work and i dont know how to make it work mojang why does this have to be so complicated bruh
public class CultistRenderer<T extends Cultist> extends HumanoidMobRenderer<T, PlayerModel<T>> {
	public CultistRenderer(EntityRendererProvider.Context context, boolean slim) {
		super(context, new PlayerModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim), 0.5f);
		addLayer(new HumanoidArmorLayer<>(
			this,
			new HumanoidArmorModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)),
			new HumanoidArmorModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR)),
			context.getModelManager()
		));
		addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
		addLayer(new ArrowLayer<>(context, this));
		addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
	}

	@Override
	protected boolean shouldShowName(T entity) {
		return false;
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return DefaultPlayerSkin.get(entity.getUUID()).texture();
	}
}
