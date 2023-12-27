package dev.cammiescorner.arcanuscontinuum.client.models.entity.magic;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class PocketDimensionPortalEntityModel extends EntityModel<Entity> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("pocket_dimension_portal"), "main");
	private final ModelPart box;
	public final ModelPart skybox;

	public PocketDimensionPortalEntityModel(ModelPart root) {
		this.box = root.getChild("box");
		this.skybox = root.getChild("skybox");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData box = root.addChild("box", ModelPartBuilder.create().uv(4, 5).cuboid(-24.0F, -32.0F, -24.0F, 48.0F, 32.0F, 48.0F, new Dilation(0.0F))
		.uv(0, 85).cuboid(-25.0F, -32.0F, -25.0F, 50.0F, 33.0F, 50.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		ModelPartData skybox = root.addChild("skybox", ModelPartBuilder.create().uv(0, 192).cuboid(-32.0F, 1.25F, -32.0F, 64.0F, 0.0F, 64.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		return TexturedModelData.of(data, 256, 320);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		box.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}
