package dev.cammiescorner.arcanuscontinuum.common.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.RaycastContext;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class ArcanusHelper {
	public static HitResult raycast(Entity origin, double maxDistance, boolean includeEntities, boolean includeFluids) {
		Vec3d startPos = origin.getCameraPosVec(1F);
		Vec3d rotation = origin.getRotationVec(1F);
		Vec3d endPos = startPos.add(rotation.multiply(maxDistance));
		HitResult hitResult = origin.world.raycast(new RaycastContext(startPos, endPos, RaycastContext.ShapeType.COLLIDER, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, origin));

		if(hitResult.getType() != HitResult.Type.MISS)
			endPos = hitResult.getPos();

		maxDistance *= maxDistance;
		HitResult entityHitResult = ProjectileUtil.raycast(origin, startPos, endPos, origin.getBoundingBox().stretch(rotation.multiply(maxDistance)).expand(1.0D, 1D, 1D), entity -> !entity.isSpectator(), maxDistance);

		if(includeEntities && entityHitResult != null)
			hitResult = entityHitResult;

		return hitResult;
	}

	@ClientOnly
	public static void renderBolts(LivingEntity entity, Vec3d startPos, MatrixStack matrices, VertexConsumerProvider vertices) {
		if(ArcanusComponents.shouldRenderBolt(entity)) {
			VertexConsumer vertex = vertices.getBuffer(RenderLayer.getLines());
			RandomGenerator random = RandomGenerator.createLegacy((entity.age + entity.getId()) / 2);
			Vec3d endPos = ArcanusComponents.getBoltPos(entity);
			int colour = entity instanceof PlayerEntity player ? Arcanus.getMagicColour(player.getGameProfile().getId()) : Arcanus.DEFAULT_MAGIC_COLOUR;
			float r = (colour >> 16 & 255) / 255F;
			float g = (colour >> 8 & 255) / 255F;
			float b = (colour & 255) / 255F;
			int steps = (int) (startPos.distanceTo(endPos) * 5);

			renderBolt(matrices, vertex, random, startPos, endPos, steps, 0, true, r, g, b);
		}
	}

	@ClientOnly
	private static void renderBolt(MatrixStack matrices, VertexConsumer vertex, RandomGenerator random, Vec3d startPos, Vec3d endPos, int steps, int currentStep, boolean recurse, float r, float g, float b) {
		Vec3d direction = endPos.subtract(startPos);
		Vec3d lastPos = startPos;
		Matrix4f modelMatrix = matrices.peek().getModel();
		Matrix3f normalMatrix = matrices.peek().getNormal();

		for(int i = currentStep; i < steps; i++) {
			Vec3d randomOffset = new Vec3d(random.nextGaussian(), random.range(-1 / (steps * 2), 1 / (steps * 2)), random.nextGaussian());
			Vec3d nextPos = startPos.add(direction.multiply((i + 1) / (float) steps)).add(randomOffset.multiply(1 / 12F));

			vertex.vertex(modelMatrix, (float) lastPos.getX(), (float) lastPos.getY(), (float) lastPos.getZ()).color(r, g, b, 0.6F).normal(normalMatrix, 0, 1, 0).next();
			vertex.vertex(modelMatrix, (float) nextPos.getX(), (float) nextPos.getY(), (float) nextPos.getZ()).color(r, g, b, 0.6F).normal(normalMatrix, 0, 1, 0).next();

			while(recurse && random.nextFloat() < 0.02F) {
				Vec3d randomOffset1 = new Vec3d(random.nextGaussian(), random.nextGaussian(), random.nextGaussian());
				renderBolt(matrices, vertex, random, lastPos, endPos.add(randomOffset1.multiply(Math.min(random.nextFloat(), 0.6F))), steps, i + 1, false, r, g, b);
			}

			lastPos = nextPos;
		}
	}
}
