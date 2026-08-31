package dev.cammiescorner.arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.block.entities.PedestalBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
	private final BlockEntityRendererProvider.Context context;

	public PedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}

	@Override
	public void render(PedestalBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		float spinTick = blockEntity.getLevel().getGameTime() + partialTick;

		poseStack.pushPose();
		poseStack.translate(0.5, 1.175 + Math.sin(spinTick * 0.1) * 0.05, 0.5);
		poseStack.mulPose(Axis.YN.rotationDegrees(spinTick * 2.5f));

		context.getItemRenderer().renderStatic(blockEntity.getItem(), ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);

		poseStack.popPose();
	}
}
