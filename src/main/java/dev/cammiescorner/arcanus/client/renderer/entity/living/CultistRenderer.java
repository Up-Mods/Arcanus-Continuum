package dev.cammiescorner.arcanus.client.renderer.entity.living;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.common.entity.living.Cultist;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;

public class CultistRenderer<T extends Cultist> extends LivingEntityRenderer<T, PlayerModel<T>> {
	private final Minecraft client = Minecraft.getInstance();
	private GameProfile gameProfile;

	public CultistRenderer(EntityRendererProvider.Context context) {
		super(context, null, 0.5f);
		addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
		addLayer(new ArrowLayer<>(context, this));
		addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		if(model == null) {
			boolean slim = client.getSkinManager().getInsecureSkin(getGameProfile(entity)).model() == PlayerSkin.Model.SLIM;
			model = new PlayerModel<>(client.getEntityModels().bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim);
		}

		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return client.getSkinManager().getInsecureSkin(getGameProfile(entity)).texture();
	}

	public GameProfile getGameProfile(T entity) {
		return new GameProfile(entity.getUUID(), "Cultist");
	}
}
