package dev.cammiescorner.arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.model.block.SpellScrollModel;
import dev.upcraft.sparkweave.api.client.render.LecternItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LecternSpellScrollRenderer extends LecternItemRenderer {
	private static final ResourceLocation TEXTURE = Arcanus.id("textures/block/lectern_spell_scroll.png");
	private SpellScrollModel model;

	public LecternSpellScrollRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
		model = new SpellScrollModel(context.bakeLayer(SpellScrollModel.MODEL_LAYER));
	}

	@Override
	public void renderBook(LecternBlockEntity lecternBlockEntity, BlockState blockState, ItemStack itemStack, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		poseStack.pushPose();
		poseStack.translate(0f, -0.02f, 0f);
		poseStack.mulPose(Axis.YP.rotationDegrees(90));
		poseStack.mulPose(Axis.XP.rotationDegrees(22.5f));
		poseStack.mulPose(Axis.ZP.rotationDegrees(180));
		poseStack.translate(0f, 0f, 0.125f);

		model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), packedLight, packedOverlay, 0xffffffff);

		poseStack.popPose();
	}
}
