package dev.cammiescorner.arcanuscontinuum.client.models.entity.magic;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class SpatialRiftEntitySigilModel extends EntityModel<Entity> {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Arcanus.id("spatial_rift_sigil"), "main");
	public final ModelPart sigil;

	public SpatialRiftEntitySigilModel(ModelPart root) {
		this.sigil = root.getChild("sigil");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData sigil = modelPartData.addChild("sigil", ModelPartBuilder.create().uv(-64, 0).cuboid(-32.0F, 0.0F, -32.0F, 64.0F, 0.0F, 64.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 64);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		sigil.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}
