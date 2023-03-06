package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.client.utils.ClientUtils;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.util.StaffType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
	@Shadow @Final private MinecraftClient client;
	@Shadow private ItemStack mainHand;
	@Shadow private float prevEquipProgressMainHand;
	@Shadow private float equipProgressMainHand;

	@Shadow protected abstract void renderArmHoldingItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, float swingProgress, Arm arm);

	@Inject(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
			ordinal = 0
	))
	private void arcanuscontinuum$animateStaff(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light, CallbackInfo info) {
		World world = client.world;
		boolean isCasting = ((ClientUtils) client).isCasting();

		if(world != null && isCasting && mainHand.getItem() instanceof StaffItem item) {
			double time = world.getTime() + tickDelta;

			if(item.staffType == StaffType.STAFF) {
				matrices.multiply(Axis.X_POSITIVE.rotationDegrees(-65F));
				matrices.multiply(Axis.Y_POSITIVE.rotationDegrees((float) Math.cos(time * 0.25)));
				matrices.multiply(Axis.Z_POSITIVE.rotationDegrees(20F + (float) Math.sin(time * 0.25)));
				matrices.translate(0.1, 1.2, -0.4);
			}
			else if(item.staffType == StaffType.BOOK && (!ArcanusClient.FIRST_PERSON_MODEL_ENABLED.getAsBoolean() || ArcanusClient.FIRST_PERSON_SHOW_HANDS.getAsBoolean())) {
				float swingProgress = player.getHandSwingProgress(tickDelta);
				float equipProgress = 1F - MathHelper.lerp(tickDelta, prevEquipProgressMainHand, equipProgressMainHand);

				matrices.push();
				matrices.multiply(Axis.Y_POSITIVE.rotationDegrees((float) Math.cos(time * 0.25)));
				matrices.multiply(Axis.Z_POSITIVE.rotationDegrees((float) Math.sin(time * 0.25)));
				renderArmHoldingItem(matrices, vertexConsumers, light, equipProgress, swingProgress, player.getMainArm().getOpposite());
				matrices.pop();
			}
			else if(item.staffType == StaffType.WAND) {
				matrices.multiply(Axis.X_POSITIVE.rotationDegrees(-65F));
				matrices.multiply(Axis.Y_POSITIVE.rotationDegrees((float) Math.cos(time * 0.25)));
				matrices.multiply(Axis.Z_POSITIVE.rotationDegrees(20F + (float) Math.sin(time * 0.25)));
				matrices.translate(0.1, 1, -0.4);
			}
			else if(item.staffType == StaffType.GAUNTLET) {
				matrices.multiply(Axis.Y_POSITIVE.rotationDegrees((float) Math.cos(time * 0.25)));
				matrices.multiply(Axis.Z_POSITIVE.rotationDegrees((float) Math.sin(time * 0.25)));
			}
			else if(item.staffType == StaffType.GUN) {
				matrices.multiply(Axis.Y_POSITIVE.rotationDegrees((float) Math.cos(time * 0.25)));
				matrices.multiply(Axis.Z_POSITIVE.rotationDegrees((float) Math.sin(time * 0.25)));
				matrices.translate(-0.465, 0, 0);
			}
		}
	}
}
