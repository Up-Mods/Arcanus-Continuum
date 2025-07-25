package dev.cammiescorner.arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.model.block.JarFluidModel;
import dev.cammiescorner.arcanus.common.block.entities.JarBlockEntity;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class JarBlockEntityRenderer implements BlockEntityRenderer<JarBlockEntity> {
	private static final ResourceLocation TEXTURE = Arcanus.id("textures/block/jar_fluid.png");
	private final BlockEntityRendererProvider.Context context;
	private final JarFluidModel model;

	public JarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
		this.model = new JarFluidModel(context.getModelSet().bakeLayer(JarFluidModel.MODEL_LAYER));
	}

	@Override
	public void render(JarBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		poseStack.pushPose();
		poseStack.translate(0.25f, 0.0625f, 0.25F);
		poseStack.scale(1f, 1.25f, 1f);

		if(blockEntity.getManaType() != null && blockEntity.getMana() > 0) {
			Color color = blockEntity.getManaType().getColor();
			double mana = blockEntity.getMana();
			int index = (int) Math.ceil(mana / 8) - 1;

			for(int i = 0; i < model.levels.length; i++) {
				ModelPart part = model.levels[i];

				part.visible = i == index;
			}

			TextureAtlas texture = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
			TextureAtlasSprite sprite = texture.getSprite(TEXTURE);

			model.renderToBuffer(poseStack, sprite.wrap(bufferSource.getBuffer(RenderType.entityCutout(InventoryMenu.BLOCK_ATLAS))), packedLight, packedOverlay, color.asIntARGB());
		}

		poseStack.popPose();
	}
}
