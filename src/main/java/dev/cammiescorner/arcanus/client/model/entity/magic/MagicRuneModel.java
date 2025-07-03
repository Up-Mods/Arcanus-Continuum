package dev.cammiescorner.arcanus.client.model.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.entity.magic.MagicRune;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MagicRuneModel extends EntityModel<MagicRune> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("magic_rune"), "main");
	public final ModelPart rune;

	public MagicRuneModel(ModelPart root) {
		this.rune = root.getChild("rune");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		modelPartData.addOrReplaceChild("rune", CubeListBuilder.create().texOffs(-15, 0).addBox(-7.5f, 0f, -7.5f, 15f, 0f, 15f, new CubeDeformation(0f)), PartPose.offset(0f, 1f, 0f));

		return LayerDefinition.create(modelData, 32, 16);
	}

	@Override
	public void setupAnim(MagicRune entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		rune.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
