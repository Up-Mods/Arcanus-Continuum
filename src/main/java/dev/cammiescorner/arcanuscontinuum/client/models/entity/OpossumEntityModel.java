package dev.cammiescorner.arcanuscontinuum.client.models.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.OpossumEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

public class OpossumEntityModel extends EntityModel<OpossumEntity> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("opossum"), "main");
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart leftForeleg;
	public final ModelPart rightForeleg;
	public final ModelPart leftHindleg;
	public final ModelPart rightHindleg;
	public final ModelPart tailBase;
	public final ModelPart tailEnd;

	public OpossumEntityModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.leftForeleg = root.getChild("leftForeleg");
		this.rightForeleg = root.getChild("rightForeleg");
		this.leftHindleg = root.getChild("leftHindleg");
		this.rightHindleg = root.getChild("rightHindleg");
		this.tailBase = root.getChild("tailBase");
		this.tailEnd = tailBase.getChild("tailEnd");
	}

	public static ModelData getModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 2).cuboid(-2.0F, -1.0F, -3.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)).uv(1, 8).cuboid(-1.0F, 0.0F, -6.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-3.0F, -3.0F, -1.0F, 6.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 19.0F, -5.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 13).cuboid(-3.0F, -2.0F, -4.0F, 6.0F, 5.0F, 8.0F, new Dilation(0.001F)), ModelTransform.pivot(0.0F, 19.0F, -1.0F));

		ModelPartData leftForeleg = modelPartData.addChild("leftForeleg", ModelPartBuilder.create().uv(14, 27).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 21.0F, -4.0F));
		ModelPartData rightForeleg = modelPartData.addChild("rightForeleg", ModelPartBuilder.create().uv(14, 27).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 21.0F, -4.0F));

		ModelPartData leftHindleg = modelPartData.addChild("leftHindleg", ModelPartBuilder.create().uv(6, 27).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 21.0F, 2.0F));
		ModelPartData rightHindleg = modelPartData.addChild("rightHindleg", ModelPartBuilder.create().uv(6, 27).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 21.0F, 2.0F));

		ModelPartData tailBase = modelPartData.addChild("tailBase", ModelPartBuilder.create().uv(20, 8).cuboid(-1.5F, -1.0F, 0.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 19.0F, 3.0F));
		ModelPartData tailEnd = tailBase.addChild("tailEnd", ModelPartBuilder.create().uv(16, 0).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new Dilation(-0.1F)), ModelTransform.pivot(0.0F, 0.5F, 3.0F));

		return modelData;
	}

	public static TexturedModelData getTexturedModelData() {
		return TexturedModelData.of(getModelData(), 32, 32);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftForeleg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightForeleg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftHindleg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightHindleg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tailBase.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(OpossumEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}
