package dev.cammiescorner.arcanus.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@ModifyExpressionValue(method = "getProjectionMatrix", at = @At(value = "CONSTANT", args = "floatValue=0.05"))
	private float fixCullWhenSmol(float depth) {
		Minecraft client = Minecraft.getInstance();

		if(client.player == null)
			return depth;

		float scale = (float) client.player.getAttributeValue(Attributes.SCALE);

		return Math.min(scale, 1f) * depth;
	}
}
