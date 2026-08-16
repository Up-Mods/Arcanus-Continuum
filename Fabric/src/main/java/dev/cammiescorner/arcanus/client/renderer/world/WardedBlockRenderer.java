package dev.cammiescorner.arcanus.client.renderer.world;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.common.data.ArcanusItemTags;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class WardedBlockRenderer {
	public static final RenderType WARDED_BLOCK = ArcanusClient.getMagicCircles(Arcanus.id("textures/block/warded_block.png"));
	private static final Minecraft client = Minecraft.getInstance();
	private static int hitTimer;

	public static void render(PoseStack poseStack, MultiBufferSource bufferSource, LocalPlayer player, Vec3 cameraPos) {
		if(client.hitResult instanceof BlockHitResult hitResult && ArcanusComponents.isBlockWarded(player.clientLevel, hitResult.getBlockPos()) && player.swinging) {
			BlockPos blockPos = hitResult.getBlockPos();

			if(client.options.keyAttack.isDown() && player.attackAnim == 0)
				hitTimer = 20;

			if(!ArcanusComponents.isOwnerOfBlock(player, blockPos) || hitTimer > 0) {
				float alpha = Mth.clamp(hitTimer / 20f, 0f, 1f);

				renderWardedBlock(poseStack, bufferSource, player.clientLevel, cameraPos, blockPos, alpha);
				player.displayClientMessage(Component.translatable(TranslationKeys.BLOCK_IS_WARDED).withStyle(ChatFormatting.RED), true);
			}

			if(hitTimer > 0)
				hitTimer -= 1;
		}

		if(player.getMainHandItem().is(ArcanusItemTags.STAVES) || player.getOffhandItem().is(ArcanusItemTags.STAVES)) {
			AtomicReferenceArray<LevelChunk> chunks = player.clientLevel.getChunkSource().storage.chunks;
			float alpha = Mth.sin(player.clientLevel.getGameTime() * 0.06f) * 0.4f + 0.6f;

			for(int i = 0; i < chunks.length(); i++) {
				ChunkAccess chunk = chunks.get(i);

				if(chunk != null) {
					ArcanusComponents.getWardedBlocks(chunk).forEach((blockPos, uuid) -> {
						if(blockPos.distSqr(BlockPos.containing(cameraPos)) < 256)
							renderWardedBlock(poseStack, bufferSource, player.clientLevel, cameraPos, blockPos, alpha);
					});
				}
			}
		}
	}

	private static void renderWardedBlock(PoseStack poseStack, MultiBufferSource buffer, Level level, Vec3 cameraPos, BlockPos blockPos, float alpha) {
		VertexConsumer consumer = buffer.getBuffer(WARDED_BLOCK);
		Vec3 pos = Vec3.atCenterOf(blockPos);

		poseStack.pushPose();
		poseStack.translate(pos.x() - cameraPos.x(), pos.y() - cameraPos.y(), pos.z() - cameraPos.z());
		poseStack.scale(1.001f, 1.001f, 1.001f);
		poseStack.translate(-0.5, -0.5, -0.5);

		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix4f = pose.pose();
		Color color = ArcanusHelper.getMagicColor(ArcanusComponents.getWardedBlocks(level.getChunk(blockPos)).get(blockPos));
		int light = level.getMaxLocalRawBrightness(blockPos);
		int overlay = OverlayTexture.NO_OVERLAY;
		float r = Mth.clamp(color.redF() * alpha, 0f, 1f);
		float g = Mth.clamp(color.greenF() * alpha, 0f, 1f);
		float b = Mth.clamp(color.blueF() * alpha, 0f, 1f);

		color = Color.fromFloatsRGB(r, g, b);

		for(Direction direction : Direction.values()) {
			BlockPos posToSide = blockPos.relative(direction);
			BlockState stateToSide = level.getBlockState(posToSide);

			if(stateToSide.isFaceSturdy(level, posToSide, direction.getOpposite(), SupportType.FULL) || ArcanusComponents.isBlockWarded(level, posToSide))
				continue;

			switch(direction) {
				case SOUTH ->
					ArcanusClient.renderQuad(matrix4f, consumer, 0f, 1f, 0f, 1f, 1f, 1f, 1f, 1f, color, light, overlay, pose, Direction.SOUTH);
				case NORTH ->
					ArcanusClient.renderQuad(matrix4f, consumer, 0f, 1f, 1f, 0f, 0f, 0f, 0f, 0f, color, light, overlay, pose, Direction.NORTH);
				case EAST ->
					ArcanusClient.renderQuad(matrix4f, consumer, 1f, 1f, 1f, 0f, 0f, 1f, 1f, 0f, color, light, overlay, pose, Direction.EAST);
				case WEST ->
					ArcanusClient.renderQuad(matrix4f, consumer, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, color, light, overlay, pose, Direction.WEST);
				case DOWN ->
					ArcanusClient.renderQuad(matrix4f, consumer, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, color, light, overlay, pose, Direction.DOWN);
				case UP ->
					ArcanusClient.renderQuad(matrix4f, consumer, 0f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, color, light, overlay, pose, Direction.UP);
			}
		}

		poseStack.popPose();
	}
}
