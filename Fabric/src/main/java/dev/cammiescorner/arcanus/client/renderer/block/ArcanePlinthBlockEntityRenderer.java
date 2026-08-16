package dev.cammiescorner.arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.common.block.entities.ArcanePlinthBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;

public class ArcanePlinthBlockEntityRenderer implements BlockEntityRenderer<ArcanePlinthBlockEntity> {
	private final BlockEntityRendererProvider.Context context;

	public ArcanePlinthBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}

	@Override
	public void render(ArcanePlinthBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		float spinTick = blockEntity.getLevel().getGameTime() + partialTick;

		poseStack.pushPose();
		poseStack.translate(0.5, 0.675 + Math.sin(spinTick * 0.1) * 0.05, 0.5);
		poseStack.mulPose(Axis.YN.rotationDegrees(spinTick * 2.5f));

		context.getItemRenderer().renderStatic(blockEntity.getItem(), ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);

		poseStack.popPose();
	}
}
