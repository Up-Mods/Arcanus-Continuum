package dev.cammiescorner.arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.common.blocks.entities.AbstractMagicBlockEntity;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;

import java.util.function.Function;

import static dev.cammiescorner.arcanus.client.ArcanusClient.renderQuad;

public class MagicBlockEntityRenderer<T extends AbstractMagicBlockEntity> implements BlockEntityRenderer<T> {
	private static final RenderType LAYER = ArcanusClient.getMagicCircles(Arcanus.id("textures/block/magic_block.png"));
	private final Function<T, Color> colorGetter;

	public static <T extends AbstractMagicBlockEntity> BlockEntityRendererProvider<T> factory(Function<T, Color> colorGetter) {
		return ctx -> new MagicBlockEntityRenderer<>(ctx, colorGetter);
	}

	private MagicBlockEntityRenderer(BlockEntityRendererProvider.Context ctx, Function<T, Color> colorGetter) {
		this.colorGetter = colorGetter;
	}

	@Override
	public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertices, int light, int overlay) {
		if(entity.getLevel() != null) {
			VertexConsumer consumer = vertices.getBuffer(LAYER);
			Matrix4f matrix4f = matrices.last().pose();
			Color color = colorGetter.apply(entity);

			for(Direction direction : Direction.values()) {
				BlockPos blockToSide = entity.getBlockPos().relative(direction);
				BlockState stateToSide = entity.getLevel().getBlockState(blockToSide);

				if(stateToSide.isSolidRender(entity.getLevel(), blockToSide) || stateToSide.is(ArcanusBlocks.MAGIC_BLOCK.get()) || stateToSide.is(ArcanusBlocks.SPATIAL_RIFT_WALL.get()) || stateToSide.is(ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get()))
					continue;

				switch(direction) {
					case SOUTH ->
						renderQuad(matrix4f, consumer, 0f, 1f, 0f, 1f, 1f, 1f, 1f, 1f, color, light, overlay, matrices.last(), Direction.SOUTH); // south
					case NORTH ->
						renderQuad(matrix4f, consumer, 0f, 1f, 1f, 0f, 0f, 0f, 0f, 0f, color, light, overlay, matrices.last(), Direction.NORTH); // north
					case EAST ->
						renderQuad(matrix4f, consumer, 1f, 1f, 1f, 0f, 0f, 1f, 1f, 0f, color, light, overlay, matrices.last(), Direction.EAST); // east
					case WEST ->
						renderQuad(matrix4f, consumer, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, color, light, overlay, matrices.last(), Direction.WEST); // west
					case DOWN ->
						renderQuad(matrix4f, consumer, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, color, light, overlay, matrices.last(), Direction.DOWN); // down
					case UP ->
						renderQuad(matrix4f, consumer, 0f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, color, light, overlay, matrices.last(), Direction.UP); // up
				}
			}
		}
	}
}
