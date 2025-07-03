package dev.cammiescorner.arcanus.client.model.entity.living;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.entity.living.Wizard;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class WizardModel extends EntityModel<Wizard> implements ArmedModel, HeadedModel {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("wizard"), "main");
	public final ModelPart head;
	public final ModelPart leftArm;
	public final ModelPart rightArm;
	public final ModelPart body;
	public final ModelPart leftLeg;
	public final ModelPart rightLeg;

	public WizardModel(ModelPart root) {
		this.head = root.getChild("head");
		this.leftArm = root.getChild("leftArm");
		this.rightArm = root.getChild("rightArm");
		this.body = root.getChild("body");
		this.leftLeg = root.getChild("leftLeg");
		this.rightLeg = root.getChild("rightLeg");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4f, -6f, -8f, 8f, 10f, 8f, new CubeDeformation(0f))
			.texOffs(32, 0).addBox(-4f, 4f, -8f, 8f, 10f, 8f, new CubeDeformation(0f))
			.texOffs(64, 0).addBox(-2f, 1f, -12f, 4f, 6f, 4f, new CubeDeformation(0f))
			.texOffs(92, 30).addBox(-4.5f, -7f, -8.5f, 9f, 6f, 9f, new CubeDeformation(0.05f)), PartPose.offset(0f, 2f, -3f));

		PartDefinition hatTip = head.addOrReplaceChild("hatTip", CubeListBuilder.create().texOffs(100, 2).addBox(-2.5f, -13.2f, -4.7f, 5f, 4f, 9f, new CubeDeformation(0.05f)), PartPose.offsetAndRotation(0f, 4f, -4f, -0.6545f, 0f, 0f));
		PartDefinition hatStalk = head.addOrReplaceChild("hatStalk", CubeListBuilder.create().texOffs(96, 15).addBox(-3.5f, -11.8f, -1.5f, 7f, 6f, 9f, new CubeDeformation(0.05f)), PartPose.offsetAndRotation(0f, 4f, -4f, 0.2618f, 0f, 0f));
		PartDefinition hatRim = head.addOrReplaceChild("hatRim", CubeListBuilder.create().texOffs(60, 45).addBox(-9f, -6f, -8.5f, 17f, 2f, 17f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 4f, -4f, 0f, 0f, 0.1309f));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20).addBox(-4f, 0f, -3f, 8f, 12f, 6f, new CubeDeformation(0f))
			.texOffs(0, 38).addBox(-4.5f, 0f, -3f, 9f, 20f, 6f, new CubeDeformation(0.5f)), PartPose.offset(0f, 2f, 0f));

		PartDefinition leftArm = root.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(28, 22).mirror().addBox(-1f, -1f, -2f, 4f, 12f, 4f, new CubeDeformation(0.01f)).mirror(false)
			.texOffs(30, 38).mirror().addBox(-1.5f, -1.5f, -2.5f, 5f, 12f, 5f, new CubeDeformation(0.01f)).mirror(false), PartPose.offset(5f, 4f, -1f));
		PartDefinition leftCuff = leftArm.addOrReplaceChild("leftCuff", CubeListBuilder.create().texOffs(30, 55).mirror().addBox(-2.5f, -4.6f, -0.4f, 5f, 5f, 4f, new CubeDeformation(0f)).mirror(false), PartPose.offsetAndRotation(1f, 10f, 2.5f, 0.7418f, 0f, 0f));

		PartDefinition rightArm = root.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(28, 22).addBox(-3f, -1f, -2f, 4f, 12f, 4f, new CubeDeformation(0.01f))
			.texOffs(30, 38).addBox(-3.5f, -1.5f, -2.5f, 5f, 12f, 5f, new CubeDeformation(0.01f)), PartPose.offset(-5f, 4f, -1f));
		PartDefinition rightCuff = rightArm.addOrReplaceChild("rightCuff", CubeListBuilder.create().texOffs(30, 55).addBox(-2.5f, -4.6f, -0.4f, 5f, 5f, 4f, new CubeDeformation(0f)), PartPose.offsetAndRotation(-1f, 10f, 2.5f, 0.7418f, 0f, 0f));

		PartDefinition leftLeg = root.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(44, 22).addBox(-2f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0f)), PartPose.offset(2f, 12f, 0f));
		PartDefinition rightLeg = root.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(44, 22).addBox(-2f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0f)), PartPose.offset(-2f, 12f, 0f));

		return LayerDefinition.create(modelData, 128, 64);
	}

	@Override
	public void setupAnim(Wizard wizard, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		rightArm.xRot = Mth.cos(limbAngle * 0.6662f + (float) Math.PI) * 2f * limbDistance * 0.5f;
		leftArm.xRot = Mth.cos(limbAngle * 0.6662f) * 2f * limbDistance * 0.5f;
		rightArm.yRot = 0;
		leftArm.yRot = 0;

		rightLeg.xRot = Mth.cos(limbAngle * 0.6662f) * 1.4f * limbDistance * 0.5f;
		leftLeg.xRot = Mth.cos(limbAngle * 0.6662f + (float) Math.PI) * 1.4f * limbDistance * 0.5f;
		head.xRot = (float) Math.toRadians(headPitch);
		head.yRot = (float) Math.toRadians(headYaw);

		if(!wizard.getMainHandItem().isEmpty()) {
			if(wizard.isLeftHanded()) {
				leftArm.xRot = (float) Math.toRadians(-75) + Mth.cos(limbAngle * 0.6662f) * 2f * limbDistance * 0.25f;
				leftArm.yRot = (float) Math.toRadians(-20);
			}
			else {
				rightArm.xRot = (float) Math.toRadians(-75) + Mth.cos(limbAngle * 0.6662f + (float) Math.PI) * 2f * limbDistance * 0.25f;
				rightArm.yRot = (float) Math.toRadians(20);
			}
		}

		leftLeg.xRot = Mth.lerp(wizard.getSwimAmount(animationProgress), leftLeg.xRot, 0.3f * Mth.cos(limbAngle * 0.33333334f + (float) Math.PI));
		rightLeg.xRot = Mth.lerp(wizard.getSwimAmount(animationProgress), rightLeg.xRot, 0.3f * Mth.cos(limbAngle * 0.33333334f));
	}

	@Override
	public ModelPart getHead() {
		return head;
	}

	@Override
	public void translateToHand(HumanoidArm arm, PoseStack matrices) {
		if(arm == HumanoidArm.LEFT)
			leftArm.translateAndRotate(matrices);
		else
			rightArm.translateAndRotate(matrices);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		head.render(poseStack, buffer, packedLight, packedOverlay, color);
		leftArm.render(poseStack, buffer, packedLight, packedOverlay, color);
		rightArm.render(poseStack, buffer, packedLight, packedOverlay, color);
		body.render(poseStack, buffer, packedLight, packedOverlay, color);
		leftLeg.render(poseStack, buffer, packedLight, packedOverlay, color);
		rightLeg.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
