package dev.cammiescorner.arcanus.client.model.feature;

import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class SpellBookModel<T extends LivingEntity> extends HumanoidModel<T> {
	public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Arcanus.id("spell_book"), "main");

	public SpellBookModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = PlayerModel.createMesh(CubeDeformation.NONE, 0);
		PartDefinition root = modelData.getRoot().getChild("body");

		PartDefinition book = root.addOrReplaceChild("book", CubeListBuilder.create().texOffs(0, 64).addBox(0f, 0f, -4f, 2f, 6f, 8f, new CubeDeformation(0f))
		.texOffs(23, 64).addBox(0f, 0f, -3.5f, 2f, 5f, 7f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));

		return LayerDefinition.create(modelData, 64, 128);
	}
}
