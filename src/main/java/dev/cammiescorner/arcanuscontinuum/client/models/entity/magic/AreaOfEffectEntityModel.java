package dev.cammiescorner.arcanuscontinuum.client.models.entity.magic;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.AreaOfEffectEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

public class AreaOfEffectEntityModel extends EntityModel<AreaOfEffectEntity> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("area_of_effect"), "main");
	public final ModelPart base;
	public final ModelPart pillar;
	public final ModelPart walls;

	public AreaOfEffectEntityModel(ModelPart root) {
		this.base = root.getChild("base");
		this.pillar = base.getChild("pillar");
		this.walls = base.getChild("walls");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		ModelPartData base = root.addChild("base", ModelPartBuilder.create().uv(-72, 80).cuboid(-36.0F, 0.0F, -36.0F, 72.0F, 0.0F, 72.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		ModelPartData pillar = base.addChild("pillar", ModelPartBuilder.create().uv(192, 0).cuboid(-8.0F, -40.0F, -8.0F, 16.0F, 40.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData walls = base.addChild("walls", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData wall1 = walls.addChild("wall1", ModelPartBuilder.create().uv(0, 0).cuboid(-24.0F, -32.0F, -24.0F, 48.0F, 32.0F, 48.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData wall2 = walls.addChild("wall2", ModelPartBuilder.create().uv(0, 0).cuboid(-24.0F, -32.0F, -24.0F, 48.0F, 32.0F, 48.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return TexturedModelData.of(modelData, 256, 256);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(AreaOfEffectEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}
