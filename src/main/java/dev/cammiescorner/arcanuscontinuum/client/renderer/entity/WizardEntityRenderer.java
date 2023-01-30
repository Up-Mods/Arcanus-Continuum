package dev.cammiescorner.arcanuscontinuum.client.renderer.entity;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.WizardEntityModel;
import dev.cammiescorner.arcanuscontinuum.common.entities.WizardEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class WizardEntityRenderer extends MobEntityRenderer<WizardEntity, WizardEntityModel> {
	public static final Identifier TEXTURE = Arcanus.id("textures/entity/living/wizard.png");
	public static final Identifier ROBES_TEXTURE = Arcanus.id("textures/entity/living/wizard_overlay.png");
	private final EntityRendererFactory.Context context;

	public WizardEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new WizardEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(WizardEntityModel.MODEL_LAYER)), 0.6F);
		this.context = context;
	}

	@Override
	public void render(WizardEntity wizard, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
		super.render(wizard, yaw, tickDelta, matrices, vertices, light);

		context.getHeldItemRenderer().renderItem(wizard, wizard.getMainHandStack(), ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, false, matrices, vertices, light);

		matrices.push();
		setupTransforms(wizard, matrices, 0, MathHelper.lerpAngleDegrees(tickDelta, wizard.prevBodyYaw, wizard.bodyYaw), tickDelta);
		matrices.scale(-1.0F, -1.0F, 1.0F);
		scale(wizard, matrices, tickDelta);
		matrices.translate(0.0, -1.5, 0.0);
		model.render(matrices, vertices.getBuffer(RenderLayer.getEntityCutout(ROBES_TEXTURE)), light, OverlayTexture.DEFAULT_UV, 0.32156864F, 0.22352941F, 0.16470589F, 1F);
		matrices.pop();
	}

	@Override
	public Identifier getTexture(WizardEntity entity) {
		return TEXTURE;
	}
}
