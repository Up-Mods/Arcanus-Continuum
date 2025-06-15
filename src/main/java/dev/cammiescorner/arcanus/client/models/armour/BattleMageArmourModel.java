package dev.cammiescorner.arcanus.client.models.armour;

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

public class BattleMageArmourModel<T extends LivingEntity> extends HumanoidModel<T> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("battle_mage_armor"), "main");
	public final ModelPart helmet;
	public final ModelPart chestplate;
	public final ModelPart surcoatFront;
	public final ModelPart surcoatBack;
	public final ModelPart rightGauntlet;
	public final ModelPart leftGauntlet;
	public final ModelPart rightGreaves;
	public final ModelPart rightBoot;
	public final ModelPart leftGreaves;
	public final ModelPart leftBoot;

	public BattleMageArmourModel(ModelPart root) {
		super(root);
		helmet = head.getChild("armorHead");
		chestplate = body.getChild("armorBody");
		surcoatFront = chestplate.getChild("surcoatFront");
		surcoatBack = chestplate.getChild("surcoatBack");
		rightGauntlet = rightArm.getChild("armorRightArm");
		leftGauntlet = leftArm.getChild("armorLeftArm");
		rightGreaves = rightLeg.getChild("armorRightLeg");
		leftGreaves = leftLeg.getChild("armorLeftLeg");
		rightBoot = rightLeg.getChild("armorRightBoot");
		leftBoot = leftLeg.getChild("armorLeftBoot");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition data = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
		PartDefinition root = data.getRoot();
		PartDefinition head = root.getChild(PartNames.HEAD);
		PartDefinition body = root.getChild(PartNames.BODY);
		PartDefinition rightArm = root.getChild(PartNames.RIGHT_ARM);
		PartDefinition leftArm = root.getChild(PartNames.LEFT_ARM);
		PartDefinition rightLeg = root.getChild(PartNames.RIGHT_LEG);
		PartDefinition leftLeg = root.getChild(PartNames.LEFT_LEG);

		PartDefinition armorHead = head.addOrReplaceChild("armorHead", CubeListBuilder.create().texOffs(18, 223).addBox(0f, -17f, -7.5f, 0f, 15f, 18f, new CubeDeformation(0f))
			.texOffs(61, 130).addBox(-1f, -10f, -6f, 2f, 10f, 12f, new CubeDeformation(0f))
			.texOffs(0, 144).addBox(-4f, -8f, -4f, 8f, 8f, 8f, new CubeDeformation(0.55f)), PartPose.offset(0f, 0f, 0f));

		PartDefinition armet = armorHead.addOrReplaceChild("armet", CubeListBuilder.create().texOffs(49, 137).addBox(-4.5f, 2.25f, -4.75f, 9f, 2f, 3f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, -1.0472f, 0f, 0f));

		PartDefinition armorBody = body.addOrReplaceChild("armorBody", CubeListBuilder.create().texOffs(0, 192).addBox(-4.5f, 9.2f, -2.5f, 9f, 7f, 5f, new CubeDeformation(0f))
			.texOffs(40, 160).addBox(-5f, 0f, -2f, 10f, 4f, 4f, new CubeDeformation(0.66f))
			.texOffs(0, 160).addBox(-4f, 0f, -2f, 8f, 12f, 4f, new CubeDeformation(0.42f))
			.texOffs(0, 204).addBox(-4f, 0f, -2f, 8f, 8f, 4f, new CubeDeformation(0.51f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition surcoatFront = armorBody.addOrReplaceChild("surcoatFront", CubeListBuilder.create().texOffs(0, 216).addBox(-4f, 0.5f, 0.5f, 8f, 8f, 2f, new CubeDeformation(0.51f)), PartPose.offsetAndRotation(0f, 8.5f, -2.5f, -0.0436f, 0f, 0f));
		PartDefinition surcoatBack = armorBody.addOrReplaceChild("surcoatBack", CubeListBuilder.create().texOffs(20, 216).addBox(-4f, 0.5f, -2.5f, 8f, 8f, 2f, new CubeDeformation(0.51f)), PartPose.offsetAndRotation(0f, 8.5f, 2.5f, 0.0436f, 0f, 0f));
		PartDefinition trinketStuff = armorBody.addOrReplaceChild("trinketStuff", CubeListBuilder.create().texOffs(0, 226).addBox(-5f, 9f, -3f, 10f, 2f, 6f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));

		PartDefinition armorRightArm = rightArm.addOrReplaceChild("armorRightArm", CubeListBuilder.create().texOffs(60, 191).addBox(-3f, -2f, -2f, 5f, 6f, 4f, new CubeDeformation(0.6f))
			.texOffs(40, 168).addBox(-4.5f, -3.75f, -1f, 2f, 6f, 2f, new CubeDeformation(0f))
			.texOffs(44, 191).addBox(-3f, -2f, -2f, 4f, 12f, 4f, new CubeDeformation(0.41f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition armorLeftArm = leftArm.addOrReplaceChild("armorLeftArm", CubeListBuilder.create().texOffs(60, 191).mirror().addBox(-2f, -2f, -2f, 5f, 6f, 4f, new CubeDeformation(0.6f)).mirror(false)
			.texOffs(40, 168).mirror().addBox(2.5f, -3.75f, -1f, 2f, 6f, 2f, new CubeDeformation(0f)).mirror(false)
			.texOffs(44, 191).mirror().addBox(-1f, -2f, -2f, 4f, 12f, 4f, new CubeDeformation(0.41f)).mirror(false), PartPose.offset(0f, 0f, 0f));

		PartDefinition armorRightLeg = rightLeg.addOrReplaceChild("armorRightLeg", CubeListBuilder.create().texOffs(0, 176).addBox(-1.9f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0.3f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition rightTasset = armorRightLeg.addOrReplaceChild("rightTasset", CubeListBuilder.create(), PartPose.offset(0f, 0f, 0f));
		PartDefinition armorRightBoot = rightLeg.addOrReplaceChild("armorRightBoot", CubeListBuilder.create().texOffs(16, 176).addBox(-1.9f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0.35f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition armorLeftLeg = leftLeg.addOrReplaceChild("armorLeftLeg", CubeListBuilder.create().texOffs(0, 176).mirror().addBox(-2.1f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0.3f)).mirror(false), PartPose.offset(0f, 0f, 0f));
		PartDefinition leftTasset = armorLeftLeg.addOrReplaceChild("leftTasset", CubeListBuilder.create(), PartPose.offset(0f, 0f, 0f));
		PartDefinition armorLeftBoot = leftLeg.addOrReplaceChild("armorLeftBoot", CubeListBuilder.create().texOffs(16, 176).mirror().addBox(-2.1f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0.35f)).mirror(false), PartPose.offset(0f, 0f, 0f));

		return LayerDefinition.create(data, 128, 256);
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
