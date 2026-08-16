package dev.cammiescorner.arcanus.client.model.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class SpatialRiftSigilModel extends EntityModel<Entity> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("spatial_rift_sigil"), "main");
	public final ModelPart sigil;

	public SpatialRiftSigilModel(ModelPart root) {
		this.sigil = root.getChild("sigil");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition sigil = modelPartData.addOrReplaceChild("sigil", CubeListBuilder.create().texOffs(-64, 0).addBox(-32f, 0f, -32f, 64f, 0f, 64f, new CubeDeformation(0f)), PartPose.offset(0f, 24f, 0f));
		return LayerDefinition.create(modelData, 128, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		sigil.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
