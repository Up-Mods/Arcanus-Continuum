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

public class SpellScrollModel extends Model {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("lectern_spell_scroll"), "main");
	public final ModelPart root;
	public final ModelPart page;
	public final ModelPart leftFurl;
	public final ModelPart rightFurl;

	public SpellScrollModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;
		this.page = root.getChild("page");
		this.leftFurl = root.getChild("leftFurl");
		this.rightFurl = root.getChild("rightFurl");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition data = new MeshDefinition();
		PartDefinition root = data.getRoot();

		root.addOrReplaceChild("page", CubeListBuilder.create().texOffs(0, 0).addBox(-5f, 0f, -4f, 10f, 0f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));
		root.addOrReplaceChild("leftFurl", CubeListBuilder.create().texOffs(0, 8).addBox(-1f, -2f, -4f, 2f, 2f, 8f, new CubeDeformation(0f)), PartPose.offset(-6f, 0f, 0f));
		root.addOrReplaceChild("rightFurl", CubeListBuilder.create().texOffs(0, 8).mirror().addBox(-1f, -2f, -4f, 2f, 2f, 8f, new CubeDeformation(0f)).mirror(false), PartPose.offset(6f, 0f, 0f));

		return LayerDefinition.create(data, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, buffer, packedLight, packedOverlay);
	}
}
