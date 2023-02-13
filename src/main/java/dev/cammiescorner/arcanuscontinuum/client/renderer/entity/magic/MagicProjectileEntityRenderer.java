package dev.cammiescorner.arcanuscontinuum.client.renderer.entity.magic;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.MagicLobEntityModel;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.MagicProjectileEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MagicProjectileEntityRenderer extends ProjectileEntityRenderer<MagicProjectileEntity> {
	private static final Identifier PROJECTILE_TEXTURE = Arcanus.id("textures/entity/magic/projectile.png");
	private static final Identifier LOB_TEXTURE = Arcanus.id("textures/entity/magic/lob.png");
	private final MagicLobEntityModel lobModel;

	public MagicProjectileEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		lobModel = new MagicLobEntityModel(context.getModelLoader().getModelPart(MagicLobEntityModel.MODEL_LAYER));
	}

	@Override
	public void render(MagicProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
		boolean isProjectile = entity.getShape() == ArcanusSpellComponents.PROJECTILE;
		int colour = entity.getColour();
		float r = (colour >> 16 & 255) / 255F;
		float g = (colour >> 8 & 255) / 255F;
		float b = (colour & 255) / 255F;

		if(isProjectile) {
			super.render(entity, yaw, tickDelta, matrices, vertices, light);
		}
		else {
			lobModel.cube1.pitch += 0.25;
			lobModel.cube2.yaw -= 0.25;
			lobModel.cube3.roll += 0.25;
			lobModel.render(matrices, vertices.getBuffer(ArcanusClient.getMagicCircles(getTexture(entity))), light, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
		}
	}

	@Override
	public Identifier getTexture(MagicProjectileEntity entity) {
		return entity.getShape() == ArcanusSpellComponents.PROJECTILE ? PROJECTILE_TEXTURE : LOB_TEXTURE;
	}
}
