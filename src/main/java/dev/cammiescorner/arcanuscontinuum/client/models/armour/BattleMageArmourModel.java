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

		ModelPartData armorHead = head.addChild("armorHead", ModelPartBuilder.create().uv(18, 223).cuboid(0.0F, -17.0F, -7.5F, 0.0F, 15.0F, 18.0F, new Dilation(0.0F))
			.uv(61, 130).cuboid(-1.0F, -10.0F, -6.0F, 2.0F, 10.0F, 12.0F, new Dilation(0.0F))
			.uv(0, 144).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.55F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData armet = armorHead.addChild("armet", ModelPartBuilder.create().uv(49, 137).cuboid(-4.5F, 2.25F, -4.75F, 9.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.0472F, 0.0F, 0.0F));

		ModelPartData armorBody = body.addChild("armorBody", ModelPartBuilder.create().uv(0, 192).cuboid(-4.5F, 9.2F, -2.5F, 9.0F, 7.0F, 5.0F, new Dilation(0.0F))
			.uv(40, 160).cuboid(-5.0F, 0.0F, -2.0F, 10.0F, 4.0F, 4.0F, new Dilation(0.66F))
			.uv(0, 160).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.42F))
			.uv(0, 204).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 4.0F, new Dilation(0.51F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData surcoatFront = armorBody.addChild("surcoatFront", ModelPartBuilder.create().uv(0, 216).cuboid(-4.0F, 0.5F, 0.5F, 8.0F, 8.0F, 2.0F, new Dilation(0.51F)), ModelTransform.of(0.0F, 8.5F, -2.5F, -0.0436F, 0.0F, 0.0F));
		ModelPartData surcoatBack = armorBody.addChild("surcoatBack", ModelPartBuilder.create().uv(20, 216).cuboid(-4.0F, 0.5F, -2.5F, 8.0F, 8.0F, 2.0F, new Dilation(0.51F)), ModelTransform.of(0.0F, 8.5F, 2.5F, 0.0436F, 0.0F, 0.0F));
		ModelPartData trinketStuff = armorBody.addChild("trinketStuff", ModelPartBuilder.create().uv(0, 226).cuboid(-5.0F, 9.0F, -3.0F, 10.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData armorRightArm = rightArm.addChild("armorRightArm", ModelPartBuilder.create().uv(60, 191).cuboid(-3.0F, -2.0F, -2.0F, 5.0F, 6.0F, 4.0F, new Dilation(0.6F))
			.uv(40, 168).cuboid(-4.5F, -3.75F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
			.uv(44, 191).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.41F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData armorLeftArm = leftArm.addChild("armorLeftArm", ModelPartBuilder.create().uv(60, 191).mirrored().cuboid(-2.0F, -2.0F, -2.0F, 5.0F, 6.0F, 4.0F, new Dilation(0.6F)).mirrored(false)
			.uv(40, 168).mirrored().cuboid(2.5F, -3.75F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
			.uv(44, 191).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.41F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData armorRightLeg = rightLeg.addChild("armorRightLeg", ModelPartBuilder.create().uv(0, 176).cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData rightTasset = armorRightLeg.addChild("rightTasset", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData armorRightBoot = rightLeg.addChild("armorRightBoot", ModelPartBuilder.create().uv(16, 176).cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.35F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData armorLeftLeg = leftLeg.addChild("armorLeftLeg", ModelPartBuilder.create().uv(0, 176).mirrored().cuboid(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData leftTasset = armorLeftLeg.addChild("leftTasset", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData armorLeftBoot = leftLeg.addChild("armorLeftBoot", ModelPartBuilder.create().uv(16, 176).mirrored().cuboid(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.35F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

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
