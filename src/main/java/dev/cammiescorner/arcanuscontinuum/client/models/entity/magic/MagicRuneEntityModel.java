package dev.cammiescorner.arcanuscontinuum.client.models.entity.magic;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.MagicRuneEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

public class MagicRuneEntityModel extends EntityModel<MagicRuneEntity> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("magic_rune"), "main");
	public final ModelPart rune;

	public MagicRuneEntityModel(ModelPart root) {
		this.rune = root.getChild("rune");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild("rune", ModelPartBuilder.create().uv(-15, 0).cuboid(-7.5F, 0F, -7.5F, 15F, 0F, 15F, new Dilation(0F)), ModelTransform.pivot(0F, 1F, 0F));

		return TexturedModelData.of(modelData, 32, 16);
	}

	@Override
	public void setAngles(MagicRuneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		rune.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}
