package dev.cammiescorner.arcanuscontinuum.client.models.feature;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class SpellPatternModel<T extends PlayerEntity> extends BipedEntityModel<T> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("spell_pattern"), "main");
	private final ModelPart base;
	public final ModelPart first;
	private final ModelPart left1;
	private final ModelPart right1;
	public final ModelPart second;
	private final ModelPart left2;
	private final ModelPart right2;
	public final ModelPart third;
	private final ModelPart left3;
	private final ModelPart right3;

	public SpellPatternModel(ModelPart root) {
		super(root);
		this.base = root.getChild("body").getChild("base");
		this.first = base.getChild("first");
		this.left1 = first.getChild("left1");
		this.right1 = first.getChild("right1");
		this.second = base.getChild("second");
		this.left2 = second.getChild("left2");
		this.right2 = second.getChild("right2");
		this.third = base.getChild("third");
		this.left3 = third.getChild("left3");
		this.right3 = third.getChild("right3");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = PlayerEntityModel.getModelData(Dilation.NONE, 0);
		ModelPartData root = modelData.getRoot().getChild("body");


		ModelPartData base = root.addChild("base", ModelPartBuilder.create(), ModelTransform.pivot(0F, 0F, 0F));

		ModelPartData first = base.addChild("first", ModelPartBuilder.create(), ModelTransform.pivot(0F, -6F, -8F));
		ModelPartData left1 = first.addChild("left1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.5F, -8.5F, 0F, 17F, 17F, 0F, new Dilation(0F)), ModelTransform.pivot(0F, 0F, 0F));
		ModelPartData right1 = first.addChild("right1", ModelPartBuilder.create().uv(0, 24).cuboid(-8.5F, -8.5F, 0F, 17F, 17F, 0F, new Dilation(0F)), ModelTransform.pivot(0F, 0F, 0F));

		ModelPartData second = base.addChild("second", ModelPartBuilder.create(), ModelTransform.pivot(0F, -6F, -11F));
		ModelPartData left2 = second.addChild("left2", ModelPartBuilder.create().uv(34, 0).cuboid(-8.5F, -8.5F, 0F, 17F, 17F, 0F, new Dilation(0F)), ModelTransform.pivot(0F, 0F, 0F));
		ModelPartData right2 = second.addChild("right2", ModelPartBuilder.create().uv(34, 24).cuboid(-8.5F, -8.5F, 0F, 17F, 17F, 0F, new Dilation(0F)), ModelTransform.pivot(0F, 0F, 0F));

		ModelPartData third = base.addChild("third", ModelPartBuilder.create(), ModelTransform.pivot(0F, -6F, -14F));
		ModelPartData left3 = third.addChild("left3", ModelPartBuilder.create().uv(68, 0).cuboid(-8.5F, -8.5F, 0F, 17F, 17F, 0F, new Dilation(0F)), ModelTransform.pivot(0F, 0F, 0F));
		ModelPartData right3 = third.addChild("right3", ModelPartBuilder.create().uv(68, 24).cuboid(-8.5F, -8.5F, 0F, 17F, 17F, 0F, new Dilation(0F)), ModelTransform.pivot(0F, 0F, 0F));

		return TexturedModelData.of(modelData, 128, 48);
	}

	@Override
	public void setAngles(T player, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		super.setAngles(player, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
		base.pivotY = 0;
		base.pivotX = 0;
		base.pivotZ = 0;

		if(player.isInSneakingPose()) {
			base.pivotY = 4.2F;

			if(ArcanusComponents.isCasting(player)) {
				base.pivotY = 8;
				base.pivotX = 4;
				base.pivotZ = 3;
			}
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	public void showMagicCircles(List<Pattern> pattern) {
		first.visible = pattern.size() > 0;
		second.visible = pattern.size() > 1;
		third.visible = pattern.size() > 2;

		if(first.visible) {
			left1.visible = pattern.get(0) == Pattern.LEFT;
			right1.visible = pattern.get(0) == Pattern.RIGHT;
		}
		if(second.visible) {
			left2.visible = pattern.get(1) == Pattern.LEFT;
			right2.visible = pattern.get(1) == Pattern.RIGHT;
		}
		if(third.visible) {
			left3.visible = pattern.get(2) == Pattern.LEFT;
			right3.visible = pattern.get(2) == Pattern.RIGHT;
		}
	}
}
