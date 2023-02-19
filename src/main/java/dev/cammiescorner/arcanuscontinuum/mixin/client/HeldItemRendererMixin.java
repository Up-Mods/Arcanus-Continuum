package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.client.utils.ClientUtils;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Axis;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
	@Shadow @Final private MinecraftClient client;
	@Shadow private ItemStack mainHand;

	@Inject(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
			ordinal = 0
	))
	private void arcanuscontinuum$animateStaff(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light, CallbackInfo info) {
		World world = client.world;
		boolean isCasting = ((ClientUtils) client).isCasting();

		if(world != null && isCasting && mainHand.getItem() instanceof StaffItem item && item.isTwoHanded) {
			double time = world.getTime() + tickDelta;

			matrices.multiply(Axis.X_POSITIVE.rotationDegrees(-65F));
			matrices.multiply(Axis.Z_POSITIVE.rotationDegrees(20F));
			matrices.translate(0.1 + Math.cos(time * 0.25) * 0.05, 1.2, -0.4 + Math.sin(time * 0.25) * 0.05);
		}
	}
}
