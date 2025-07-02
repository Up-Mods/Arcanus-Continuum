package dev.cammiescorner.arcanus.client.models.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.entity.living.Opossum;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class OpossumModel extends EntityModel<Opossum> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("opossum"), "main");
	public final ModelPart head;
	public final ModelPart hat;
	public final ModelPart body;
	public final ModelPart leftForeleg;
	public final ModelPart rightForeleg;
	public final ModelPart leftHindleg;
	public final ModelPart rightHindleg;
	public final ModelPart tailBase;
	public final ModelPart tailEnd;

	public OpossumModel(ModelPart root) {
		this.head = root.getChild("head");
		this.hat = head.getChild("hat");
		this.body = root.getChild("body");
		this.leftForeleg = root.getChild("leftForeleg");
		this.rightForeleg = root.getChild("rightForeleg");
		this.leftHindleg = root.getChild("leftHindleg");
		this.rightHindleg = root.getChild("rightHindleg");
		this.tailBase = root.getChild("tailBase");
		this.tailEnd = tailBase.getChild("tailEnd");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition data = new MeshDefinition();
		PartDefinition root = data.getRoot();

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 2).addBox(-2f, -1f, -3f, 4f, 3f, 3f, new CubeDeformation(0f)).texOffs(1, 8).addBox(-1f, 0f, -6f, 2f, 2f, 3f, new CubeDeformation(0f)).texOffs(0, 0).addBox(-3f, -3f, -1f, 6f, 2f, 0f, new CubeDeformation(0f)), PartPose.offset(0f, 19f, -5f));
		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 7).addBox(-2f, -3.25f, -3f, 4f, 2f, 4f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition hatTip = hat.addOrReplaceChild("hatTip", CubeListBuilder.create().texOffs(48, 7).addBox(-1f, -3.75f, -1f, 2f, 2f, 4f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, -1f, -1f, -0.1745f, 0.1745f, 0f));
		PartDefinition hatBase = hat.addOrReplaceChild("hatBase", CubeListBuilder.create().texOffs(32, 0).addBox(-3f, -0.75f, -3f, 6f, 1f, 6f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, -1f, -1f, 0f, 0f, 0.0873f));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3f, -2f, -4f, 6f, 5f, 8f, new CubeDeformation(0.001f)), PartPose.offset(0f, 19f, -1f));

		PartDefinition leftForeleg = root.addOrReplaceChild("leftForeleg", CubeListBuilder.create().texOffs(14, 27).addBox(-1f, 0f, -1f, 2f, 3f, 2f, new CubeDeformation(0f)), PartPose.offset(2f, 21f, -4f));
		PartDefinition rightForeleg = root.addOrReplaceChild("rightForeleg", CubeListBuilder.create().texOffs(14, 27).addBox(-1f, 0f, -1f, 2f, 3f, 2f, new CubeDeformation(0f)), PartPose.offset(-2f, 21f, -4f));

		PartDefinition leftHindleg = root.addOrReplaceChild("leftHindleg", CubeListBuilder.create().texOffs(6, 27).addBox(-1f, 0f, -1f, 2f, 3f, 2f, new CubeDeformation(0f)), PartPose.offset(2f, 21f, 2f));
		PartDefinition rightHindleg = root.addOrReplaceChild("rightHindleg", CubeListBuilder.create().texOffs(6, 27).addBox(-1f, 0f, -1f, 2f, 3f, 2f, new CubeDeformation(0f)), PartPose.offset(-2f, 21f, 2f));

		PartDefinition tailBase = root.addOrReplaceChild("tailBase", CubeListBuilder.create().texOffs(20, 8).addBox(-1.5f, -1f, 0f, 3f, 3f, 3f, new CubeDeformation(0f)), PartPose.offset(0f, 19f, 3f));
		PartDefinition tailEnd = tailBase.addOrReplaceChild("tailEnd", CubeListBuilder.create().texOffs(16, 0).addBox(-1f, -1f, 0f, 2f, 2f, 6f, new CubeDeformation(-0.1f)), PartPose.offset(0f, 0.5f, 3f));

		return LayerDefinition.create(data, 64, 32);
	}

	@Override
	public void setupAnim(Opossum entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		head.xRot = headPitch * 0.017453292f;
		head.yRot = netHeadYaw * 0.017453292f;

		leftHindleg.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount;
		rightHindleg.xRot = Mth.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
		leftForeleg.xRot = Mth.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
		rightForeleg.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount;
		leftHindleg.visible = !entity.isOrderedToSit();
		rightHindleg.visible = !entity.isOrderedToSit();
		leftForeleg.visible = !entity.isOrderedToSit();
		rightForeleg.visible = !entity.isOrderedToSit();

		if(entity.isOrderedToSit()) {
			head.y = 21;
			body.y = 21;
			tailBase.y = 21;
			tailBase.xRot = (float) Math.toRadians(-15);
			tailEnd.xRot = (float) Math.toRadians(7);
		}
		else {
			head.y = 19;
			body.y = 19;
			tailBase.y = 19;
			tailBase.xRot = (float) Math.toRadians(-35);
			tailEnd.xRot = (float) Math.toRadians(15);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		head.render(poseStack, buffer, packedLight, packedOverlay, color);
		body.render(poseStack, buffer, packedLight, packedOverlay, color);
		leftForeleg.render(poseStack, buffer, packedLight, packedOverlay, color);
		rightForeleg.render(poseStack, buffer, packedLight, packedOverlay, color);
		leftHindleg.render(poseStack, buffer, packedLight, packedOverlay, color);
		rightHindleg.render(poseStack, buffer, packedLight, packedOverlay, color);
		tailBase.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
