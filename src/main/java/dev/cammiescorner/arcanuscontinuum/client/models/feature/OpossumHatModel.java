package dev.cammiescorner.arcanuscontinuum.client.models.feature;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.OpossumEntityModel;
import dev.cammiescorner.arcanuscontinuum.common.entities.OpossumEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

public class OpossumHatModel extends OpossumEntityModel {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("opossum_hat"), "main");
	private final ModelPart possumHat;

	public OpossumHatModel(ModelPart root) {
		super(root);
		this.possumHat = root.getChild("head").getChild("possumHat");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = OpossumEntityModel.getModelData();
		ModelPartData head = data.getRoot().getChild("head");

		ModelPartData possumHat = head.addChild("possumHat", ModelPartBuilder.create().uv(0, 7).cuboid(-2F, -2.5F, -2F, 4F, 2F, 4F, new Dilation(0F)), ModelTransform.pivot(0F, 18.25F, -6F));
		ModelPartData hatTip = possumHat.addChild("hatTip", ModelPartBuilder.create().uv(16, 7).cuboid(-1F, -4F, -1F, 2F, 2F, 4F, new Dilation(0F)), ModelTransform.of(0F, 0F, 0F, -0.1745F, 0.1745F, 0F));
		ModelPartData hatBase = possumHat.addChild("hatBase", ModelPartBuilder.create().uv(0, 0).cuboid(-3F, -1F, -3F, 6F, 1F, 6F, new Dilation(0F)), ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0.0873F));

		return TexturedModelData.of(data, 32, 16);
	}

	@Override
	public void setAngles(OpossumEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		possumHat.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}
