package dev.cammiescorner.arcanus.client.model.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class WizardRobesModel<T extends LivingEntity> extends HumanoidModel<T> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("wizard_robes"), "main");
	public final ModelPart wizardHat;
	public final ModelPart robes;
	public final ModelPart rightSleeve;
	public final ModelPart leftSleeve;
	public final ModelPart rightSleeveSlim;
	public final ModelPart leftSleeveSlim;
	public final ModelPart rightPants;
	public final ModelPart leftPants;
	public final ModelPart rightBoot;
	public final ModelPart leftBoot;

	public WizardRobesModel(ModelPart root) {
		super(root);
		wizardHat = head.getChild("wizardHat");
		robes = body.getChild("robes");
		rightSleeve = rightArm.getChild("rightSleeve");
		leftSleeve = leftArm.getChild("leftSleeve");
		rightSleeveSlim = rightArm.getChild("rightSleeveSlim");
		leftSleeveSlim = leftArm.getChild("leftSleeveSlim");
		rightPants = rightLeg.getChild("rightPants");
		leftPants = leftLeg.getChild("leftPants");
		rightBoot = rightLeg.getChild("rightBoot");
		leftBoot = leftLeg.getChild("leftBoot");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition data = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
		PartDefinition head = data.getRoot().getChild(PartNames.HEAD);
		PartDefinition body = data.getRoot().getChild(PartNames.BODY);
		PartDefinition rightArm = data.getRoot().getChild(PartNames.RIGHT_ARM);
		PartDefinition leftArm = data.getRoot().getChild(PartNames.LEFT_ARM);
		PartDefinition rightLeg = data.getRoot().getChild(PartNames.RIGHT_LEG);
		PartDefinition leftLeg = data.getRoot().getChild(PartNames.LEFT_LEG);

		PartDefinition wizardHat = head.addOrReplaceChild("wizardHat", CubeListBuilder.create().texOffs(51, 64).addBox(-4.5f, -11f, -4.5f, 9f, 6f, 9f, new CubeDeformation(0.05f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition hatTip = wizardHat.addOrReplaceChild("hatTip", CubeListBuilder.create().texOffs(100, 79).addBox(-2.5f, -13.2f, -4.7f, 5f, 4f, 9f, new CubeDeformation(0.05f)), PartPose.offsetAndRotation(0f, 0f, 0f, -0.6545f, 0f, 0f));
		PartDefinition hatStalk = wizardHat.addOrReplaceChild("hatStalk", CubeListBuilder.create().texOffs(87, 64).addBox(-3.5f, -11.8f, -1.5f, 7f, 6f, 9f, new CubeDeformation(0.05f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0.2618f, 0f, 0f));
		PartDefinition hatRim = wizardHat.addOrReplaceChild("hatRim", CubeListBuilder.create().texOffs(0, 64).addBox(-9f, -6f, -8.5f, 17f, 2f, 17f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, 0.1309f));

		PartDefinition robes = body.addOrReplaceChild("robes", CubeListBuilder.create().texOffs(0, 83).addBox(-4.5f, -0.5f, -2.5f, 9f, 13f, 5f, new CubeDeformation(0.05f)).texOffs(28, 83).addBox(-5f, -0.5f, -2.5f, 10f, 13f, 5f, new CubeDeformation(0.1f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition leftFlap = robes.addOrReplaceChild("leftFlap", CubeListBuilder.create().texOffs(36, 101).addBox(-2.5f, 12.6f, -0.95f, 5f, 9f, 2f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0.2618f, 1.5708f, 0f));
		PartDefinition rightFlap = robes.addOrReplaceChild("rightFlap", CubeListBuilder.create().texOffs(22, 101).addBox(-2.5f, 12.6f, -0.95f, 5f, 9f, 2f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0.2618f, -1.5708f, 0f));
		PartDefinition backFlap = robes.addOrReplaceChild("backFlap", CubeListBuilder.create().texOffs(0, 101).addBox(-4.5f, 12.1f, -2.75f, 9f, 9f, 2f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0.2618f, 0f, 0f));

		PartDefinition rightSleeve = rightArm.addOrReplaceChild("rightSleeve", CubeListBuilder.create().texOffs(58, 84).addBox(-3.5f, -2.5f, -2.5f, 5f, 12f, 5f, new CubeDeformation(0.01f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition rightCuff = rightSleeve.addOrReplaceChild("rightCuff", CubeListBuilder.create().texOffs(78, 92).addBox(-2.5f, -4.6f, -0.3f, 5f, 5f, 4f, new CubeDeformation(0f)), PartPose.offsetAndRotation(-1f, 9f, 2.5f, 0.7418f, 0f, 0f));
		PartDefinition rightSleeveSlim = rightArm.addOrReplaceChild("rightSleeveSlim", CubeListBuilder.create().texOffs(58, 104).addBox(-2.5f, -2.5f, -2.5f, 4f, 12f, 5f, new CubeDeformation(0.01f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition rightCuffSlim = rightSleeveSlim.addOrReplaceChild("rightCuffSlim", CubeListBuilder.create().texOffs(78, 112).addBox(-1.5f, -4.6f, -0.3f, 4f, 5f, 4f, new CubeDeformation(0f)), PartPose.offsetAndRotation(-1f, 9f, 2.5f, 0.7418f, 0f, 0f));

		PartDefinition leftSleeve = leftArm.addOrReplaceChild("leftSleeve", CubeListBuilder.create().texOffs(58, 84).mirror().addBox(-1.5f, -2.5f, -2.5f, 5f, 12f, 5f, new CubeDeformation(0.01f)).mirror(false), PartPose.offset(0f, 0f, 0f));
		PartDefinition leftCuff = leftSleeve.addOrReplaceChild("leftCuff", CubeListBuilder.create().texOffs(78, 92).mirror().addBox(-2.5f, -4.6f, -0.3f, 5f, 5f, 4f, new CubeDeformation(0f)).mirror(false), PartPose.offsetAndRotation(1f, 9f, 2.5f, 0.7418f, 0f, 0f));
		PartDefinition leftSleeveSlim = leftArm.addOrReplaceChild("leftSleeveSlim", CubeListBuilder.create().texOffs(58, 104).mirror().addBox(-1.5f, -2.5f, -2.5f, 4f, 12f, 5f, new CubeDeformation(0.01f)).mirror(false), PartPose.offset(0f, 0f, 0f));
		PartDefinition leftCuffSlim = leftSleeveSlim.addOrReplaceChild("leftCuffSlim", CubeListBuilder.create().texOffs(78, 112).mirror().addBox(-2.5f, -4.6f, -0.3f, 4f, 5f, 4f, new CubeDeformation(0f)).mirror(false), PartPose.offsetAndRotation(1f, 9f, 2.5f, 0.7418f, 0f, 0f));

		PartDefinition rightPants = rightLeg.addOrReplaceChild("rightPants", CubeListBuilder.create().texOffs(0, 112).addBox(-2f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0.3f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition leftPants = leftLeg.addOrReplaceChild("leftPants", CubeListBuilder.create().texOffs(0, 112).mirror().addBox(-2f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0.3f)).mirror(false), PartPose.offset(0f, 0f, 0f));

		PartDefinition rightBoot = rightLeg.addOrReplaceChild("rightBoot", CubeListBuilder.create().texOffs(16, 112).addBox(-2f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0.4f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition leftBoot = leftLeg.addOrReplaceChild("leftBoot", CubeListBuilder.create().texOffs(16, 112).mirror().addBox(-2f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0.4f)).mirror(false), PartPose.offset(0f, 0f, 0f));

		return LayerDefinition.create(data, 128, 128);
	}

	@Override
	public void setupAnim(T livingEntity, float f, float g, float h, float i, float j) {
		super.setupAnim(livingEntity, f, g, h, i, j);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
