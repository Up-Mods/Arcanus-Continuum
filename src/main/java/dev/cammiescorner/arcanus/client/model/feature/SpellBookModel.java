package dev.cammiescorner.arcanus.client.model.feature;

import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class SpellBookModel<T extends LivingEntity> extends HumanoidModel<T> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("spell_book"), "main");
	public final ModelPart book;

	public SpellBookModel(ModelPart root) {
		super(root);
		this.book = body.getChild("book");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
		PartDefinition root = modelData.getRoot().getChild(PartNames.BODY);

		PartDefinition book = root.addOrReplaceChild("book", CubeListBuilder.create().texOffs(0, 64).addBox(0f, 24f, -4f, 2f, 6f, 8f, new CubeDeformation(0f))
		.texOffs(23, 64).addBox(0f, 24f, -3.5f, 2f, 5f, 7f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));

		return LayerDefinition.create(modelData, 64, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		if(entity.isCrouching()) {
			book.xRot = 0.5f;
			book.y = 5f;
			book.z = -9f;
		}
		else {
			book.xRot = 0f;
			book.y = 0f;
			book.z = 0f;
		}
	}
}
