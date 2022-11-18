package dev.cammiescorner.arcanuscontinuum.client.models.armour;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class WizardArmourModel<T extends LivingEntity> extends BipedEntityModel<T> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("wizard_robes"), "main");
	public final ModelPart wizardHat;
	public final ModelPart robes;
	public final ModelPart rightSleeve;
	public final ModelPart leftSleeve;
	public final ModelPart rightPants;
	public final ModelPart leftPants;
	public final ModelPart rightBoot;
	public final ModelPart leftBoot;

	public WizardArmourModel(ModelPart root) {
		super(root);
		wizardHat = head.getChild("wizardHat");
		robes = body.getChild("robes");
		rightSleeve = rightArm.getChild("rightSleeve");
		leftSleeve = leftArm.getChild("leftSleeve");
		rightPants = rightLeg.getChild("rightPants");
		leftPants = leftLeg.getChild("leftPants");
		rightBoot = rightLeg.getChild("rightBoot");
		leftBoot = leftLeg.getChild("leftBoot");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
		ModelPartData head = data.getRoot().getChild(EntityModelPartNames.HEAD);
		ModelPartData body = data.getRoot().getChild(EntityModelPartNames.BODY);
		ModelPartData rightArm = data.getRoot().getChild(EntityModelPartNames.RIGHT_ARM);
		ModelPartData leftArm = data.getRoot().getChild(EntityModelPartNames.LEFT_ARM);
		ModelPartData rightLeg = data.getRoot().getChild(EntityModelPartNames.RIGHT_LEG);
		ModelPartData leftLeg = data.getRoot().getChild(EntityModelPartNames.LEFT_LEG);

		ModelPartData wizardHat = head.addChild("wizardHat", ModelPartBuilder.create().uv(51, 64).cuboid(-4.5F, -11F, -4.5F, 9F, 6F, 9F, new Dilation(0.05F)), ModelTransform.pivot(0F, 0F, 0F));
		ModelPartData hatTip = wizardHat.addChild("hatTip", ModelPartBuilder.create().uv(100, 79).cuboid(-2.5F, -13.2F, -4.7F, 5F, 4F, 9F, new Dilation(0.05F)), ModelTransform.of(0F, 0F, 0F, -0.6545F, 0F, 0F));
		ModelPartData hatStalk = wizardHat.addChild("hatStalk", ModelPartBuilder.create().uv(87, 64).cuboid(-3.5F, -11.8F, -1.5F, 7F, 6F, 9F, new Dilation(0.05F)), ModelTransform.of(0F, 0F, 0F, 0.2618F, 0F, 0F));
		ModelPartData hatRim = wizardHat.addChild("hatRim", ModelPartBuilder.create().uv(0, 64).cuboid(-9F, -6F, -8.5F, 17F, 2F, 17F, new Dilation(0F)), ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0.1309F));

		ModelPartData robes = body.addChild("robes", ModelPartBuilder.create().uv(0, 83).cuboid(-4.5F, -0.5F, -2.5F, 9F, 13F, 5F, new Dilation(0.05F)).uv(28, 83).cuboid(-5F, -0.5F, -2.5F, 10F, 13F, 5F, new Dilation(0.1F)), ModelTransform.pivot(0F, 0F, 0F));
		ModelPartData leftFlap = robes.addChild("leftFlap", ModelPartBuilder.create().uv(36, 101).cuboid(-2.5F, 12.6F, -0.95F, 5F, 9F, 2F, new Dilation(0F)), ModelTransform.of(0F, 0F, 0F, 0.2618F, 1.5708F, 0F));
		ModelPartData rightFlap = robes.addChild("rightFlap", ModelPartBuilder.create().uv(22, 101).cuboid(-2.5F, 12.6F, -0.95F, 5F, 9F, 2F, new Dilation(0F)), ModelTransform.of(0F, 0F, 0F, 0.2618F, -1.5708F, 0F));
		ModelPartData backFlap = robes.addChild("backFlap", ModelPartBuilder.create().uv(0, 101).cuboid(-4.5F, 12.1F, -2.75F, 9F, 9F, 2F, new Dilation(0F)), ModelTransform.of(0F, 0F, 0F, 0.2618F, 0F, 0F));

		ModelPartData rightSleeve = rightArm.addChild("rightSleeve", ModelPartBuilder.create().uv(58, 84).cuboid(-3.5F, -2.5F, -2.5F, 5F, 12F, 5F, new Dilation(0.01F)), ModelTransform.pivot(0F, 0F, 0F));
		ModelPartData rightCuff = rightSleeve.addChild("rightCuff", ModelPartBuilder.create().uv(78, 92).cuboid(-2.5F, -4.6F, -0.3F, 5F, 5F, 4F, new Dilation(0F)), ModelTransform.of(-1F, 9F, 2.5F, 0.7418F, 0F, 0F));
		ModelPartData leftSleeve = leftArm.addChild("leftSleeve", ModelPartBuilder.create().uv(58, 84).mirrored().cuboid(-1.5F, -2.5F, -2.5F, 5F, 12F, 5F, new Dilation(0.01F)).mirrored(false), ModelTransform.pivot(0F, 0F, 0F));
		ModelPartData leftCuff = leftSleeve.addChild("leftCuff", ModelPartBuilder.create().uv(78, 92).mirrored().cuboid(-2.5F, -4.6F, -0.3F, 5F, 5F, 4F, new Dilation(0F)).mirrored(false), ModelTransform.of(1F, 9F, 2.5F, 0.7418F, 0F, 0F));

		ModelPartData rightPants = rightLeg.addChild("rightPants", ModelPartBuilder.create().uv(0, 112).cuboid(-2F, 0F, -2F, 4F, 12F, 4F, new Dilation(0.3F)), ModelTransform.pivot(0F, 0F, 0F));
		ModelPartData leftPants = leftLeg.addChild("leftPants", ModelPartBuilder.create().uv(0, 112).mirrored().cuboid(-2F, 0F, -2F, 4F, 12F, 4F, new Dilation(0.3F)).mirrored(false), ModelTransform.pivot(0F, 0F, 0F));

		ModelPartData rightBoot = rightLeg.addChild("rightBoot", ModelPartBuilder.create().uv(16, 112).cuboid(-2F, 0F, -2F, 4F, 12F, 4F, new Dilation(0.4F)), ModelTransform.pivot(0F, 0F, 0F));
		ModelPartData leftBoot = leftLeg.addChild("leftBoot", ModelPartBuilder.create().uv(16, 112).mirrored().cuboid(-2F, 0F, -2F, 4F, 12F, 4F, new Dilation(0.4F)).mirrored(false), ModelTransform.pivot(0F, 0F, 0F));

		return TexturedModelData.of(data, 128, 128);
	}

	@Override
	public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {
		super.setAngles(livingEntity, f, g, h, i, j);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		super.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}
