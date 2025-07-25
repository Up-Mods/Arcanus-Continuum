package dev.cammiescorner.arcanus.client.model.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class JarFluidModel extends Model {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("jar_fluid"), "main");
	public final ModelPart root;
	public final ModelPart[] levels;

	public JarFluidModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;

		this.levels = new ModelPart[] {
			root.getChild("level_1"), root.getChild("level_2"),
			root.getChild("level_3"), root.getChild("level_4"),
			root.getChild("level_5"), root.getChild("level_6"),
			root.getChild("level_7"), root.getChild("level_8")
		};
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition data = new MeshDefinition();
		PartDefinition root = data.getRoot();

		root.addOrReplaceChild("level_1", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 1.25f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_2", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 2.5f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_3", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 3.75f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_4", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 5f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_5", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 6.25f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_6", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 7.5f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_7", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 8.75f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_8", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 10f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));

		root.addOrReplaceChild("level_1", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 1f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_2", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 2f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_3", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 3f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_4", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 4f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_5", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 5f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_6", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 6f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_7", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 7f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("level_8", CubeListBuilder.create().texOffs(0, 0).addBox(0f, 0f, 0f, 8f, 8f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));

		return LayerDefinition.create(data, 32, 576);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}
