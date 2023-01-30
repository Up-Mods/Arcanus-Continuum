package dev.cammiescorner.arcanuscontinuum.client.models.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.WizardEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

public class WizardEntityModel extends EntityModel<WizardEntity> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("wizard"), "main");
	private final ModelPart head;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart body;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public WizardEntityModel(ModelPart root) {
		this.head = root.getChild("head");
		this.leftArm = root.getChild("leftArm");
		this.rightArm = root.getChild("rightArm");
		this.body = root.getChild("body");
		this.leftLeg = root.getChild("leftLeg");
		this.rightLeg = root.getChild("rightLeg");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -6.0F, -8.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F))
				.uv(32, 0).cuboid(-4.0F, 4.0F, -8.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F))
				.uv(64, 0).cuboid(-2.0F, 1.0F, -12.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F))
				.uv(92, 30).cuboid(-4.5F, -7.0F, -8.5F, 9.0F, 6.0F, 9.0F, new Dilation(0.05F)), ModelTransform.pivot(0.0F, 2.0F, -3.0F));

		ModelPartData hatTip = head.addChild("hatTip", ModelPartBuilder.create().uv(100, 2).cuboid(-2.5F, -13.2F, -4.7F, 5.0F, 4.0F, 9.0F, new Dilation(0.05F)), ModelTransform.of(0.0F, 4.0F, -4.0F, -0.6545F, 0.0F, 0.0F));
		ModelPartData hatStalk = head.addChild("hatStalk", ModelPartBuilder.create().uv(96, 15).cuboid(-3.5F, -11.8F, -1.5F, 7.0F, 6.0F, 9.0F, new Dilation(0.05F)), ModelTransform.of(0.0F, 4.0F, -4.0F, 0.2618F, 0.0F, 0.0F));
		ModelPartData hatRim = head.addChild("hatRim", ModelPartBuilder.create().uv(60, 45).cuboid(-9.0F, -6.0F, -8.5F, 17.0F, 2.0F, 17.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.0F, -4.0F, 0.0F, 0.0F, 0.1309F));

		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 20).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.0F))
				.uv(0, 38).cuboid(-4.5F, 0.0F, -3.0F, 9.0F, 20.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		ModelPartData leftArm = root.addChild("leftArm", ModelPartBuilder.create().uv(28, 22).mirrored().cuboid(-1.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.01F)).mirrored(false)
				.uv(30, 38).mirrored().cuboid(-1.5F, -1.5F, -2.5F, 5.0F, 12.0F, 5.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.pivot(5.0F, 4.0F, -1.0F));
		ModelPartData leftCuff = leftArm.addChild("leftCuff", ModelPartBuilder.create().uv(30, 55).mirrored().cuboid(-2.5F, -4.6F, -0.4F, 5.0F, 5.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.0F, 10.0F, 2.5F, 0.7418F, 0.0F, 0.0F));

		ModelPartData rightArm = root.addChild("rightArm", ModelPartBuilder.create().uv(28, 22).cuboid(-3.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.01F))
				.uv(30, 38).cuboid(-3.5F, -1.5F, -2.5F, 5.0F, 12.0F, 5.0F, new Dilation(0.01F)), ModelTransform.pivot(-5.0F, 4.0F, -1.0F));
		ModelPartData rightCuff = rightArm.addChild("rightCuff", ModelPartBuilder.create().uv(30, 55).cuboid(-2.5F, -4.6F, -0.4F, 5.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 10.0F, 2.5F, 0.7418F, 0.0F, 0.0F));

		ModelPartData leftLeg = root.addChild("leftLeg", ModelPartBuilder.create().uv(44, 22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
		ModelPartData rightLeg = root.addChild("rightLeg", ModelPartBuilder.create().uv(44, 22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));

		return TexturedModelData.of(modelData, 128, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(WizardEntity wizard, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		if(!wizard.getMainHandStack().isEmpty())
			rightArm.pitch = (float) Math.toRadians(-75);
	}
}
