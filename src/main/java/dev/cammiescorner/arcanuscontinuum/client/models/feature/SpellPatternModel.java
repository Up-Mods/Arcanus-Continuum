package dev.cammiescorner.arcanuscontinuum.client.models.feature;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class SpellPatternModel extends EntityModel<Entity> {
	public final ModelPart base;
	public final ModelPart first;
	public final ModelPart left1;
	public final ModelPart right1;
	public final ModelPart second;
	public final ModelPart left2;
	public final ModelPart right2;
	public final ModelPart third;
	public final ModelPart left3;
	public final ModelPart right3;

	public SpellPatternModel(ModelPart root) {
		this.base = root.getChild("base");
		this.first = base.getChild("first");
		this.left1 = first.getChild("left1");
		this.right1 = first.getChild("right1");
		this.second = base.getChild("second");
		this.left2 = second.getChild("left2");
		this.right2 = second.getChild("right2");
		this.third = base.getChild("third");
		this.left3 = third.getChild("left2");
		this.right3 = third.getChild("right2");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData first = base.addChild("first", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData left1 = first.addChild("left1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.5F, -8.5F, 0.0F, 17.0F, 17.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData right1 = first.addChild("right1", ModelPartBuilder.create().uv(0, 24).cuboid(-8.5F, -8.5F, 0.0F, 17.0F, 17.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData second = base.addChild("second", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData left2 = second.addChild("left2", ModelPartBuilder.create().uv(34, 0).cuboid(-8.5F, -8.5F, 0.0F, 17.0F, 17.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData right2 = second.addChild("right2", ModelPartBuilder.create().uv(34, 24).cuboid(-8.5F, -8.5F, 0.0F, 17.0F, 17.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData third = base.addChild("third", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData left3 = third.addChild("left3", ModelPartBuilder.create().uv(68, 0).cuboid(-8.5F, -8.5F, 0.0F, 17.0F, 17.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData right3 = third.addChild("right3", ModelPartBuilder.create().uv(68, 24).cuboid(-8.5F, -8.5F, 0.0F, 17.0F, 17.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 128, 48);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}
