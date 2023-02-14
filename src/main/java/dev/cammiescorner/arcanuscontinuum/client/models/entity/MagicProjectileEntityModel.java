package dev.cammiescorner.arcanuscontinuum.client.models.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.MagicProjectileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

public class MagicProjectileEntityModel extends EntityModel<MagicProjectileEntity> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("magic_projectile"), "main");
	private final ModelPart base;
	public final ModelPart ring1;
	public final ModelPart ring2;
	public final ModelPart ring3;

	public MagicProjectileEntityModel(ModelPart root) {
		this.base = root.getChild("base");
		this.ring1 = base.getChild("ring1");
		this.ring2 = base.getChild("ring2");
		this.ring3 = base.getChild("ring3");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		ModelPartData base = root.addChild("base", ModelPartBuilder.create().uv(0, -16).cuboid(0.0F, -2.5F, -8.0F, 0.0F, 5.0F, 16.0F, new Dilation(0.0F)).uv(-16, 5).cuboid(-2.5F, 0.0F, -8.0F, 5.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 21.5F, 0.0F));
		ModelPartData ring1 = base.addChild("ring1", ModelPartBuilder.create().uv(10, 5).cuboid(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -4.0F));
		ModelPartData ring2 = base.addChild("ring2", ModelPartBuilder.create().uv(10, 12).cuboid(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData ring3 = base.addChild("ring3", ModelPartBuilder.create().uv(10, 19).cuboid(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 4.0F));

		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(MagicProjectileEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}
