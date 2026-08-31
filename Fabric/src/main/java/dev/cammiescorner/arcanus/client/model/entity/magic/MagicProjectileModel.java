package dev.cammiescorner.arcanus.client.model.entity.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.entity.magic.Missile;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MagicProjectileModel extends EntityModel<Missile> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("magic_projectile"), "main");
	private final ModelPart base;
	public final ModelPart ring1;
	public final ModelPart ring2;
	public final ModelPart ring3;

	public MagicProjectileModel(ModelPart root) {
		this.base = root.getChild("base");
		this.ring1 = base.getChild("ring1");
		this.ring2 = base.getChild("ring2");
		this.ring3 = base.getChild("ring3");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, -16).addBox(0f, -2.5f, -8f, 0f, 5f, 16f, new CubeDeformation(0f)).texOffs(-16, 5).addBox(-2.5f, 0f, -8f, 5f, 0f, 16f, new CubeDeformation(0f)), PartPose.offset(0f, 21.5f, 0f));
		PartDefinition ring1 = base.addOrReplaceChild("ring1", CubeListBuilder.create().texOffs(10, 5).addBox(-3.5f, -3.5f, 0f, 7f, 7f, 0f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, -4f));
		PartDefinition ring2 = base.addOrReplaceChild("ring2", CubeListBuilder.create().texOffs(10, 12).addBox(-3.5f, -3.5f, 0f, 7f, 7f, 0f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		PartDefinition ring3 = base.addOrReplaceChild("ring3", CubeListBuilder.create().texOffs(10, 19).addBox(-3.5f, -3.5f, 0f, 7f, 7f, 0f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 4f));

		return LayerDefinition.create(modelData, 32, 32);
	}

	@Override
	public void setupAnim(Missile entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		base.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
