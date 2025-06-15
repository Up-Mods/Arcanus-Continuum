package dev.cammiescorner.arcanus.client.models.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class PocketDimensionPortalModel extends EntityModel<Entity> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("pocket_dimension_portal"), "main");
	private final ModelPart box;
	public final ModelPart skybox;

	public PocketDimensionPortalModel(ModelPart root) {
		this.box = root.getChild("box");
		this.skybox = root.getChild("skybox");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition data = new MeshDefinition();
		PartDefinition root = data.getRoot();

		PartDefinition box = root.addOrReplaceChild("box", CubeListBuilder.create().texOffs(4, 5).addBox(-24f, -32f, -24f, 48f, 32f, 48f, new CubeDeformation(0f))
			.texOffs(0, 85).addBox(-25f, -32f, -25f, 50f, 33f, 50f, new CubeDeformation(0f)), PartPose.offset(0f, 24f, 0f));

		PartDefinition skybox = root.addOrReplaceChild("skybox", CubeListBuilder.create().texOffs(0, 192).addBox(-32f, 1.25f, -32f, 64f, 0f, 64f, new CubeDeformation(0f)), PartPose.offset(0f, 24f, 0f));

		return LayerDefinition.create(data, 256, 320);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		box.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
