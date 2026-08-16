package dev.cammiescorner.arcanus.client.model.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.entity.magic.Missile;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MagicLobModel extends EntityModel<Missile> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("magic_lob"), "main");
	public final ModelPart cube1;
	public final ModelPart cube2;
	public final ModelPart cube3;

	public MagicLobModel(ModelPart root) {
		this.cube1 = root.getChild("cube1");
		this.cube2 = root.getChild("cube2");
		this.cube3 = root.getChild("cube3");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		PartDefinition cube1 = root.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(0, 0).addBox(-2f, -2f, -2f, 4f, 4f, 4f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition cube2 = root.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(0, 0).addBox(-2f, -2f, -2f, 4f, 4f, 4f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition cube3 = root.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(0, 0).addBox(-2f, -2f, -2f, 4f, 4f, 4f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));

		return LayerDefinition.create(modelData, 16, 16);
	}

	@Override
	public void setupAnim(Missile entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		cube1.render(poseStack, buffer, packedLight, packedOverlay, color);
		cube2.render(poseStack, buffer, packedLight, packedOverlay, color);
		cube3.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
