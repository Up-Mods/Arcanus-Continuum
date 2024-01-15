package dev.cammiescorner.arcanuscontinuum.client.models.armour;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class BattleMageArmourModel<T extends LivingEntity> extends BipedEntityModel<T> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("battle_mage_armor"), "main");
	public final ModelPart helmet;
	public final ModelPart chestplate;
	public final ModelPart surcoatFront;
	public final ModelPart surcoatBack;
	public final ModelPart rightGauntlet;
	public final ModelPart leftGauntlet;
	public final ModelPart rightGreaves;
	public final ModelPart rightBoot;
	public final ModelPart leftGreaves;
	public final ModelPart leftBoot;

	public BattleMageArmourModel(ModelPart root) {
		super(root);
		helmet = head.getChild("armorHead");
		chestplate = body.getChild("armorBody");
		surcoatFront = chestplate.getChild("surcoatFront");
		surcoatBack = chestplate.getChild("surcoatBack");
		rightGauntlet = rightArm.getChild("armorRightArm");
		leftGauntlet = leftArm.getChild("armorLeftArm");
		rightGreaves = rightLeg.getChild("armorRightLeg");
		leftGreaves = leftLeg.getChild("armorLeftLeg");
		rightBoot = rightLeg.getChild("armorRightBoot");
		leftBoot = leftLeg.getChild("armorLeftBoot");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
		ModelPartData root = data.getRoot();
		ModelPartData head = root.getChild(EntityModelPartNames.HEAD);
		ModelPartData body = root.getChild(EntityModelPartNames.BODY);
		ModelPartData rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM);
		ModelPartData leftArm = root.getChild(EntityModelPartNames.LEFT_ARM);
		ModelPartData rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
		ModelPartData leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);

		ModelPartData armorHead = head.addChild("armorHead", ModelPartBuilder.create().uv(0, 144).cuboid(-4f, -8f, -4f, 8f, 8f, 8f, new Dilation(0.53f))
		.uv(18, 223).cuboid(0f, -17f, -7.5f, 0f, 15f, 18f, new Dilation(0f))
		.uv(28, 130).cuboid(-1f, -10f, -6f, 2f, 2f, 12f, new Dilation(0f))
		.uv(52, 128).cuboid(-1f, -8f, -6f, 2f, 6f, 2f, new Dilation(0f))
		.uv(44, 128).cuboid(-1f, -8f, 4f, 2f, 8f, 2f, new Dilation(0f))
		.uv(32, 144).cuboid(-4f, -8f, -4f, 8f, 8f, 8f, new Dilation(0.55f)), ModelTransform.pivot(0f, 0f, 0f));
		ModelPartData armet_r1 = armorHead.addChild("armet_r1", ModelPartBuilder.create().uv(49, 137).cuboid(-4.5f, 2.25f, -4.75f, 9f, 2f, 3f, new Dilation(0f)), ModelTransform.of(0f, 0f, 0f, -1.0472f, 0f, 0f));

		ModelPartData armorBody = body.addChild("armorBody", ModelPartBuilder.create().uv(0, 192).cuboid(-4.5f, 9.2f, -2.5f, 9f, 7f, 5f, new Dilation(0f))
		.uv(48, 179).cuboid(-4.5f, 9.2f, -2.5f, 9f, 7f, 5f, new Dilation(0.01f))
		.uv(40, 160).cuboid(-5f, 0f, -2f, 10f, 4f, 4f, new Dilation(0.66f))
		.uv(0, 160).cuboid(-4f, 0f, -2f, 8f, 12f, 4f, new Dilation(0.4f))
		.uv(56, 203).cuboid(-4f, 0f, -2f, 8f, 12f, 4f, new Dilation(0.42f))
		.uv(0, 204).cuboid(-4f, 0f, -2f, 8f, 8f, 4f, new Dilation(0.51f)), ModelTransform.pivot(0f, 0f, 0f));
		ModelPartData surcoatFront = armorBody.addChild("surcoatFront", ModelPartBuilder.create().uv(0, 216).cuboid(-4f, 0.5f, 0.5f, 8f, 8f, 2f, new Dilation(0.51f)), ModelTransform.of(0f, 8.5f, -2.5f, -0.0436f, 0f, 0f));
		ModelPartData surcoatBack = armorBody.addChild("surcoatBack", ModelPartBuilder.create().uv(20, 216).cuboid(-4f, 0.5f, -2.5f, 8f, 8f, 2f, new Dilation(0.51f)), ModelTransform.of(0f, 8.5f, 2.5f, 0.0436f, 0f, 0f));
		ModelPartData trinketStuff = armorBody.addChild("trinketStuff", ModelPartBuilder.create().uv(0, 226).cuboid(-5f, 9f, -2.75f, 10f, 2f, 3f, new Dilation(0f))
		.uv(0, 231).cuboid(-5f, 9f, -0.25f, 10f, 2f, 3f, new Dilation(0f)), ModelTransform.pivot(0f, 0f, 0f));

		ModelPartData armorRightArm = rightArm.addChild("armorRightArm", ModelPartBuilder.create().uv(24, 160).cuboid(-3f, -2f, -2f, 4f, 12f, 4f, new Dilation(0.3f))
		.uv(60, 191).cuboid(-3f, -2f, -2f, 5f, 6f, 4f, new Dilation(0.55f))
		.uv(40, 168).cuboid(-4.5f, -3.75f, -1f, 2f, 6f, 2f, new Dilation(0f))
		.uv(44, 191).cuboid(-3f, -2f, -2f, 4f, 12f, 4f, new Dilation(0.41f))
		.uv(28, 191).cuboid(-3f, -2f, -2f, 4f, 12f, 4f, new Dilation(0.5f)), ModelTransform.pivot(0f, 0f, 0f));
		ModelPartData armorLeftArm = leftArm.addChild("armorLeftArm", ModelPartBuilder.create().uv(24, 160).mirrored().cuboid(-1f, -2f, -2f, 4f, 12f, 4f, new Dilation(0.3f)).mirrored(false)
		.uv(60, 191).mirrored().cuboid(-2f, -2f, -2f, 5f, 6f, 4f, new Dilation(0.55f)).mirrored(false)
		.uv(40, 168).mirrored().cuboid(2.5f, -3.75f, -1f, 2f, 6f, 2f, new Dilation(0f)).mirrored(false)
		.uv(44, 191).mirrored().cuboid(-1f, -2f, -2f, 4f, 12f, 4f, new Dilation(0.41f)).mirrored(false)
		.uv(28, 191).mirrored().cuboid(-1f, -2f, -2f, 4f, 12f, 4f, new Dilation(0.5f)).mirrored(false), ModelTransform.pivot(0f, 0f, 0f));

		ModelPartData armorRightLeg = rightLeg.addChild("armorRightLeg", ModelPartBuilder.create().uv(0, 176).cuboid(-1.9f, 0f, -2f, 4f, 12f, 4f, new Dilation(0.3f)), ModelTransform.pivot(0f, 0f, 0f));
		ModelPartData rightTasset = armorRightLeg.addChild("rightTasset", ModelPartBuilder.create().uv(32, 176).cuboid(-2.55f, -1f, -2.55f, 4f, 11f, 4f, new Dilation(0f)), ModelTransform.pivot(0f, 0f, 0f));
		ModelPartData armorRightBoot = rightLeg.addChild("armorRightBoot", ModelPartBuilder.create().uv(16, 176).cuboid(-1.9f, 0f, -2f, 4f, 12f, 4f, new Dilation(0.35f)), ModelTransform.pivot(0f, 0f, 0f));
		ModelPartData armorLeftLeg = leftLeg.addChild("armorLeftLeg", ModelPartBuilder.create().uv(0, 176).mirrored().cuboid(-2.1f, 0f, -2f, 4f, 12f, 4f, new Dilation(0.3f)).mirrored(false), ModelTransform.pivot(0f, 0f, 0f));
		ModelPartData leftTasset = armorLeftLeg.addChild("leftTasset", ModelPartBuilder.create().uv(32, 176).mirrored().cuboid(-1.45f, -1f, -2.55f, 4f, 11f, 4f, new Dilation(0f)).mirrored(false), ModelTransform.pivot(0f, 0f, 0f));
		ModelPartData armorLeftBoot = leftLeg.addChild("armorLeftBoot", ModelPartBuilder.create().uv(16, 176).mirrored().cuboid(-2.1f, 0f, -2f, 4f, 12f, 4f, new Dilation(0.35f)).mirrored(false), ModelTransform.pivot(0f, 0f, 0f));

		return TexturedModelData.of(data, 128, 256);
	}

	@Override
	public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {
		super.setAngles(livingEntity, f, g, h, i, j);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}
