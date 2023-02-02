package dev.cammiescorner.arcanuscontinuum.client.renderer.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.client.models.feature.SpellPatternModel;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.MathHelper;

public class SpellPatternFeatureRenderer<T extends PlayerEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	private static final Identifier TEXTURE = Arcanus.id("textures/entity/feature/magic_circles.png");
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final SpellPatternModel<PlayerEntity> model;

	public SpellPatternFeatureRenderer(FeatureRendererContext<T, M> context) {
		super(context);
		model = new SpellPatternModel<>(client.getEntityModelLoader().getModelPart(SpellPatternModel.MODEL_LAYER));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertices, int light, PlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		ItemStack stack = player.getMainHandStack();
		String playerUuid = player.getUuidAsString();
		int colour = StaffItem.getMagicColour(stack, switch(playerUuid) {
			case "1b44461a-f605-4b29-a7a9-04e649d1981c" -> 0xff005a; // folly red
			case "6147825f-5493-4154-87c5-5c03c6b0a7c2" -> 0xf2dd50; // lotus gold
			case "63a8c63b-9179-4427-849a-55212e6008bf" -> 0x7cff7c; // moriya green
			case "d5034857-9e8a-44cb-a6da-931caff5b838" -> 0xbd78ff; // upcraft pourble
			default -> 0x68e1ff;
		});

		//
		// Integer.decode(itemName.substring(itemName.lastIndexOf('#')))

		float r = (colour >> 16 & 255) / 255F;
		float g = (colour >> 8 & 255) / 255F;
		float b = (colour & 255) / 255F;

		if(stack.getItem() instanceof StaffItem && stack.hasCustomName() && stack.getName().getString().equals("jeb_")) {
			int m = 15;
			int n = player.age / m + player.getId();
			int o = DyeColor.values().length;
			float f = ((player.age % m) + client.getTickDelta()) / 15F;
			float[] fs = SheepEntity.getRgbColor(DyeColor.byId(n % o));
			float[] gs = SheepEntity.getRgbColor(DyeColor.byId((n + 1) % o));
			r = fs[0] * (1F - f) + gs[0] * f;
			g = fs[1] * (1F - f) + gs[1] * f;
			b = fs[2] * (1F - f) + gs[2] * f;
		}

		model.showMagicCircles(ArcanusComponents.getPattern(player));
		model.first.roll += Math.toRadians(2);
		model.second.roll -= Math.toRadians(3);
		model.third.roll += Math.toRadians(4);

		model.first.pivotZ = -8 + MathHelper.sin((player.age + player.getId() + client.getTickDelta()) / (MathHelper.PI * 2)) * 0.5F;
		model.second.pivotZ = -11 + MathHelper.cos((player.age + player.getId() + client.getTickDelta()) / (MathHelper.PI * 2)) * 0.5F;
		model.third.pivotZ = -13 + MathHelper.sin((player.age + player.getId() + client.getTickDelta()) / (MathHelper.PI * 2)) * 0.5F;

		matrices.push();
		matrices.translate(0, 0.65, -0.35);

		if(ArcanusComponents.isCasting(player)) {
			matrices.translate(-0.35, 0, 0.05);
			matrices.multiply(Axis.Y_POSITIVE.rotationDegrees(player.getMainArm() == Arm.RIGHT ? 65 : -65));
		}

		RenderSystem.disableDepthTest();
		model.render(matrices, vertices.getBuffer(ArcanusClient.getMagicCircles(TEXTURE)), 15728850, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
		RenderSystem.enableDepthTest();
		matrices.pop();
	}
}
