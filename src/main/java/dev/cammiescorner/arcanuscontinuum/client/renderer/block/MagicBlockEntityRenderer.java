package dev.cammiescorner.arcanuscontinuum.client.renderer.block;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class MagicBlockEntityRenderer implements BlockEntityRenderer<MagicBlockEntity> {
	private static final RenderLayer LAYER = ArcanusClient.getMagicCircles(Arcanus.id("textures/block/magic_block.png"));
	private final MinecraftClient client = MinecraftClient.getInstance();

	public MagicBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

	}

	@Override
	public void render(MagicBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
		if(entity.getWorld() != null) {
			VertexConsumer consumer = vertices.getBuffer(LAYER);
			Matrix4f matrix4f = matrices.peek().getModel();
			Matrix3f matrix3f = matrices.peek().getNormal();
			int colour = entity.getColour();

			for(Direction direction : Direction.values()) {
				BlockPos blockToSide = entity.getPos().offset(direction);
				BlockState stateToSide = entity.getWorld().getBlockState(blockToSide);

				if(stateToSide.isOpaqueFullCube(entity.getWorld(), blockToSide) || stateToSide.isOf(ArcanusBlocks.MAGIC_BLOCK.get()) || stateToSide.isOf(ArcanusBlocks.UNBREAKABLE_MAGIC_BLOCK.get()) || stateToSide.isOf(ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get()))
					continue;

				switch(direction) {
					case SOUTH ->
						renderSide(matrix4f, consumer, 0F, 1F, 0F, 1F, 1F, 1F, 1F, 1F, colour, light, overlay, matrix3f, Direction.SOUTH); // south
					case NORTH ->
						renderSide(matrix4f, consumer, 0F, 1F, 1F, 0F, 0F, 0F, 0F, 0F, colour, light, overlay, matrix3f, Direction.NORTH); // north
					case EAST ->
						renderSide(matrix4f, consumer, 1F, 1F, 1F, 0F, 0F, 1F, 1F, 0F, colour, light, overlay, matrix3f, Direction.EAST); // east
					case WEST ->
						renderSide(matrix4f, consumer, 0F, 0F, 0F, 1F, 0F, 1F, 1F, 0F, colour, light, overlay, matrix3f, Direction.WEST); // west
					case DOWN ->
						renderSide(matrix4f, consumer, 0F, 1F, 0F, 0F, 0F, 0F, 1F, 1F, colour, light, overlay, matrix3f, Direction.DOWN); // down
					case UP ->
						renderSide(matrix4f, consumer, 0F, 1F, 1F, 1F, 1F, 1F, 0F, 0F, colour, light, overlay, matrix3f, Direction.UP); // up
				}
			}
		}
	}

	private void renderSide(Matrix4f matrix4f, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, int colour, int light, int overlay, Matrix3f normal, Direction direction) {
		vertices.vertex(matrix4f, x1, y1, z1).color(colour).uv(0, 1).overlay(overlay).light(light).normal(normal, direction.getVector().getX(), direction.getVector().getY(), direction.getVector().getZ()).next();
		vertices.vertex(matrix4f, x2, y1, z2).color(colour).uv(1, 1).overlay(overlay).light(light).normal(normal, direction.getVector().getX(), direction.getVector().getY(), direction.getVector().getZ()).next();
		vertices.vertex(matrix4f, x2, y2, z3).color(colour).uv(1, 0).overlay(overlay).light(light).normal(normal, direction.getVector().getX(), direction.getVector().getY(), direction.getVector().getZ()).next();
		vertices.vertex(matrix4f, x1, y2, z4).color(colour).uv(0, 0).overlay(overlay).light(light).normal(normal, direction.getVector().getX(), direction.getVector().getY(), direction.getVector().getZ()).next();
	}
}
