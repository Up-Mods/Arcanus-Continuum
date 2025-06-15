package dev.cammiescorner.arcanus.client.models.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.entities.magic.AreaOfEffect;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class AreaOfEffectModel extends EntityModel<AreaOfEffect> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("area_of_effect"), "main");
	public final ModelPart base;
	public final ModelPart pillar;
	public final ModelPart walls;

	public AreaOfEffectModel(ModelPart root) {
		this.base = root.getChild("base");
		this.pillar = base.getChild("pillar");
		this.walls = base.getChild("walls");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create().texOffs(-72, 80).addBox(-36f, 0f, -36f, 72f, 0f, 72f, new CubeDeformation(0f)), PartPose.offset(0f, 24f, 0f));
		PartDefinition pillar = base.addOrReplaceChild("pillar", CubeListBuilder.create().texOffs(192, 0).addBox(-8f, -40f, -8f, 16f, 40f, 16f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition walls = base.addOrReplaceChild("walls", CubeListBuilder.create(), PartPose.offset(0f, 0f, 0f));
		PartDefinition wall1 = walls.addOrReplaceChild("wall1", CubeListBuilder.create().texOffs(0, 0).addBox(-24f, -32f, -24f, 48f, 32f, 48f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition wall2 = walls.addOrReplaceChild("wall2", CubeListBuilder.create().texOffs(0, 0).addBox(-24f, -32f, -24f, 48f, 32f, 48f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, -0.7854f, 0f));

		return LayerDefinition.create(modelData, 256, 256);
	}

	@Override
	public void setupAnim(AreaOfEffect entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		base.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
