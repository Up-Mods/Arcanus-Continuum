package dev.cammiescorner.arcanuscontinuum.client.renderer.entity.magic;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.MagicLobEntityModel;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.MagicProjectileEntityModel;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.MagicProjectileEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.MathHelper;

public class MagicProjectileEntityRenderer extends ProjectileEntityRenderer<MagicProjectileEntity> {
	private static final Identifier PROJECTILE_TEXTURE = Arcanus.id("textures/entity/magic/projectile.png");
	private static final Identifier LOB_TEXTURE = Arcanus.id("textures/entity/magic/lob.png");
	private final MagicLobEntityModel lobModel;
	private final MagicProjectileEntityModel projectileModel;

	public MagicProjectileEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		lobModel = new MagicLobEntityModel(context.getModelLoader().getModelPart(MagicLobEntityModel.MODEL_LAYER));
		projectileModel = new MagicProjectileEntityModel(context.getModelLoader().getModelPart(MagicProjectileEntityModel.MODEL_LAYER));
	}

	@Override
	public void render(MagicProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
		VertexConsumer consumer = vertices.getBuffer(ArcanusClient.getMagicCircles(getTexture(entity)));
		boolean isProjectile = entity.getShape() == ArcanusSpellComponents.PROJECTILE;
		int colour = entity.getColour();
		float r = (colour >> 16 & 255) / 255F;
		float g = (colour >> 8 & 255) / 255F;
		float b = (colour & 255) / 255F;

		matrices.push();

		if(isProjectile) {
			matrices.multiply(Axis.Y_POSITIVE.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 180));
			matrices.multiply(Axis.X_POSITIVE.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));
			matrices.translate(0, -1, 0);
			projectileModel.ring1.roll = (entity.age + tickDelta) * 0.1F;
			projectileModel.ring2.roll = -(entity.age + tickDelta) * 0.125F;
			projectileModel.ring3.roll = (entity.age + tickDelta) * 0.15F;
			projectileModel.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
		}
		else {
			matrices.translate(0, 0.3, 0);
			lobModel.cube1.pitch = (entity.age + tickDelta) * 0.1F;
			lobModel.cube1.yaw = (entity.age + tickDelta) * 0.1F;
			lobModel.cube2.yaw = -(entity.age + tickDelta) * 0.125F;
			lobModel.cube2.roll = -(entity.age + tickDelta) * 0.125F;
			lobModel.cube3.roll = (entity.age + tickDelta) * 0.15F;
			lobModel.cube3.pitch = (entity.age + tickDelta) * 0.15F;
			lobModel.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
		}

		matrices.pop();
	}

	@Override
	public Identifier getTexture(MagicProjectileEntity entity) {
		return entity.getShape() == ArcanusSpellComponents.PROJECTILE ? PROJECTILE_TEXTURE : LOB_TEXTURE;
	}
}
