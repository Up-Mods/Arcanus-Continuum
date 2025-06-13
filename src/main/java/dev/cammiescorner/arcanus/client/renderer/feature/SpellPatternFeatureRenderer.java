package dev.cammiescorner.arcanus.client.renderer.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.client.models.feature.SpellPatternModel;
import dev.cammiescorner.arcanus.common.items.StaffItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import dev.cammiescorner.arcanus.common.util.StaffType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SpellPatternFeatureRenderer<T extends Player, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private static final ResourceLocation TEXTURE = Arcanus.id("textures/entity/feature/magic_circles.png");
	private final Minecraft client = Minecraft.getInstance();
	private final SpellPatternModel<Player> model;

	public SpellPatternFeatureRenderer(RenderLayerParent<T, M> context) {
		super(context);
		model = new SpellPatternModel<>(client.getEntityModels().bakeLayer(SpellPatternModel.MODEL_LAYER));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Player player, float limbAngle, float limbSwingAmount, float partialTick, float ageInTicks, float headYaw, float headPitch) {
		ItemStack stack = player.getMainHandItem();
		Color color = ArcanusHelper.getMagicColor(player);

		model.showMagicCircles(ArcanusComponents.getPattern(player));

		poseStack.pushPose();
		poseStack.translate(0, 0.65, -0.35);

		model.setupAnim(player, limbAngle, limbSwingAmount, ageInTicks, headYaw, headPitch);
		model.showMagicCircles(ArcanusComponents.getPattern(player));

		if(ArcanusComponents.isCasting(player) && stack.getItem() instanceof StaffItem item) {
			if(item.staffType == StaffType.STAFF) {
				poseStack.translate(player.getMainArm() == HumanoidArm.RIGHT ? -0.35 : 0.35, 0, 0.05);
				poseStack.mulPose(Axis.YP.rotationDegrees(player.getMainArm() == HumanoidArm.RIGHT ? 65 : -65));
			}
			else if(item.staffType == StaffType.WAND) {
				poseStack.translate(player.getMainArm() == HumanoidArm.RIGHT ? -0.35 : 0.35, -0.05, -0.1);
			}
			else if(item.staffType == StaffType.GAUNTLET) {
				poseStack.translate(player.getMainArm() == HumanoidArm.RIGHT ? -0.35 : 0.35, -0.05, 0.4);
			}
			else if(item.staffType == StaffType.BOOK) {
				poseStack.translate(player.getMainArm() == HumanoidArm.RIGHT ? 0.35 : -0.35, -0.05, 0.4);
			}
			else if(item.staffType == StaffType.GUN) {
				poseStack.translate(0, -0.15, 0.15);
			}
		}

		poseStack.pushPose();
		poseStack.translate(0, 0, Mth.sin((player.tickCount + player.getId() + partialTick) / (Mth.PI * 2)) * 0.05f);
		model.first.render(poseStack, bufferSource.getBuffer(ArcanusClient.getMagicCircles(TEXTURE)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, color.asIntARGB());
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(0, 0, Mth.cos((player.tickCount + player.getId() + partialTick) / (Mth.PI * 2)) * 0.05f);
		model.second.render(poseStack, bufferSource.getBuffer(ArcanusClient.getMagicCircles(TEXTURE)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, color.asIntARGB());
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(0, 0, Mth.sin((player.tickCount + player.getId() + partialTick) / (Mth.PI * 2)) * 0.05f);
		model.third.render(poseStack, bufferSource.getBuffer(ArcanusClient.getMagicCircles(TEXTURE)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, color.asIntARGB());
		poseStack.popPose();

		poseStack.popPose();
	}
}
