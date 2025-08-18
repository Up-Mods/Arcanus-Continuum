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

public class ArtificerArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("artificer_armor"), "main");
	public final ModelPart helmet;
	public final ModelPart chestplate;
	public final ModelPart rightSleeve;
	public final ModelPart rightSleeveSlim;
	public final ModelPart leftSleeve;
	public final ModelPart leftSleeveSlim;
	public final ModelPart rightPants;
	public final ModelPart rightSkirt;
	public final ModelPart rightBoots;
	public final ModelPart leftPants;
	public final ModelPart leftSkirt;
	public final ModelPart leftBoots;

	public ArtificerArmorModel(ModelPart root) {
		super(root);
		this.helmet = this.head.getChild("helmet");
		this.chestplate = this.body.getChild("chestplate");
		this.rightSleeve = this.rightArm.getChild("rightSleeve");
		this.rightSleeveSlim = this.rightArm.getChild("rightSleeveSlim");
		this.leftSleeve = this.leftArm.getChild("leftSleeve");
		this.leftSleeveSlim = this.leftArm.getChild("leftSleeveSlim");
		this.rightPants = this.rightLeg.getChild("rightPants");
		this.rightSkirt = this.rightPants.getChild("rightSkirt");
		this.rightBoots = this.rightLeg.getChild("rightBoots");
		this.leftPants = this.leftLeg.getChild("leftPants");
		this.leftSkirt = this.leftPants.getChild("leftSkirt");
		this.leftBoots = this.leftLeg.getChild("leftBoots");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition data = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
		PartDefinition head = data.getRoot().getChild(PartNames.HEAD);
		PartDefinition body = data.getRoot().getChild(PartNames.BODY);
		PartDefinition rightArm = data.getRoot().getChild(PartNames.RIGHT_ARM);
		PartDefinition leftArm = data.getRoot().getChild(PartNames.LEFT_ARM);
		PartDefinition rightLeg = data.getRoot().getChild(PartNames.RIGHT_LEG);
		PartDefinition leftLeg = data.getRoot().getChild(PartNames.LEFT_LEG);

		PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 64).addBox(-4f, -8f, -4f, 8f, 8f, 8f, new CubeDeformation(0.6f))
		.texOffs(32, 64).addBox(-1f, -10f, -5f, 2f, 10f, 4f, new CubeDeformation(0.6f)), PartPose.offset(0f, 0f, 0f));

		PartDefinition chestplate = body.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(0, 80).addBox(-4f, -0f, -2f, 8f, 7f, 4f, new CubeDeformation(0.35f))
		.texOffs(24, 80).addBox(-4f, 10f, -2f, 8f, 2f, 4f, new CubeDeformation(0.35f))
		.texOffs(48, 80).addBox(-2f, 9.5f, -3f, 4f, 3f, 2f, new CubeDeformation(0.35f)), PartPose.offset(0f, 0f, 0f));

		PartDefinition rightSleeve = rightArm.addOrReplaceChild("rightSleeve", CubeListBuilder.create().texOffs(0, 91).addBox(-3.75f, -2f, -2f, 5f, 6f, 4f, new CubeDeformation(0.35f))
		.texOffs(18, 91).addBox(-5f, -4f, -1f, 2f, 9f, 2f, new CubeDeformation(0.35f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, 0.0873f));
		PartDefinition rightSleeveSlim = rightArm.addOrReplaceChild("rightSleeveSlim", CubeListBuilder.create().texOffs(26, 91).addBox(-2.75f, -2f, -2f, 4f, 6f, 4f, new CubeDeformation(0.35f))
		.texOffs(46, 91).addBox(-4f, -4f, -1f, 2f, 9f, 2f, new CubeDeformation(0.35f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, 0.0873f));

		PartDefinition leftSleeve = leftArm.addOrReplaceChild("leftSleeve", CubeListBuilder.create().texOffs(0, 91).addBox(-1.25f, -2f, -2f, 5f, 6f, 4f, new CubeDeformation(0.35f))
		.texOffs(18, 91).addBox(3f, -4f, -1f, 2f, 9f, 2f, new CubeDeformation(0.35f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, -0.0873f));
		PartDefinition leftSleeveSlim = leftArm.addOrReplaceChild("leftSleeveSlim", CubeListBuilder.create().texOffs(26, 91).addBox(-1.25f, -2f, -2f, 4f, 6f, 4f, new CubeDeformation(0.35f))
		.texOffs(46, 91).addBox(2f, -4f, -1f, 2f, 9f, 2f, new CubeDeformation(0.35f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, -0.0873f));

		PartDefinition rightPants = rightLeg.addOrReplaceChild("rightPants", CubeListBuilder.create().texOffs(16, 102).addBox(-2f, 0f, -2f, 4f, 6f, 4f, new CubeDeformation(0.3f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition rightSkirt = rightPants.addOrReplaceChild("rightSkirt", CubeListBuilder.create().texOffs(0, 102).addBox(-2f, 0f, -2f, 4f, 10f, 4f, new CubeDeformation(0.5f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, 0.2182f));

		PartDefinition rightBoots = rightLeg.addOrReplaceChild("rightBoots", CubeListBuilder.create().texOffs(0, 116).addBox(-2f, 8f, -2f, 4f, 4f, 4f, new CubeDeformation(0.4f)), PartPose.offset(0f, 0f, 0f));

		PartDefinition leftPants = leftLeg.addOrReplaceChild("leftPants", CubeListBuilder.create().texOffs(32, 102).addBox(-2f, 0f, -2f, 4f, 6f, 4f, new CubeDeformation(0.3f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition leftSkirt = leftPants.addOrReplaceChild("leftSkirt", CubeListBuilder.create().texOffs(48, 102).addBox(-2f, 0f, -2f, 4f, 10f, 4f, new CubeDeformation(0.5f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, -0.2182f));

		PartDefinition leftBoots = leftLeg.addOrReplaceChild("leftBoots", CubeListBuilder.create().texOffs(16, 116).addBox(-2f, 8f, -2f, 4f, 4f, 4f, new CubeDeformation(0.4f)), PartPose.offset(0f, 0f, 0f));

		return LayerDefinition.create(data, 64, 128);
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
