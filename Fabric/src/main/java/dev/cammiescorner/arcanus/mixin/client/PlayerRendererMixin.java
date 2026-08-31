package dev.cammiescorner.arcanus.mixin.client;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.renderer.layer.HaloLayerRenderer;
import dev.cammiescorner.arcanus.client.renderer.layer.SpellBookLayerRenderer;
import dev.cammiescorner.arcanus.client.renderer.layer.SpellPatternLayerRenderer;
import dev.cammiescorner.arcanus.registry.ArcanusMobEffects;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
	public PlayerRendererMixin(EntityRendererProvider.Context ctx, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(EntityRendererProvider.Context context, boolean bl, CallbackInfo info) {
		addLayer(new SpellPatternLayerRenderer<>(this));
		addLayer(new HaloLayerRenderer<>(this));
		addLayer(new SpellBookLayerRenderer<>(this));
	}

	@Inject(method = "getTextureLocation(Lnet/minecraft/client/player/AbstractClientPlayer;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
	private void getTexture(AbstractClientPlayer player, CallbackInfoReturnable<ResourceLocation> info) {
		if(player.hasEffect(ArcanusMobEffects.ANONYMITY.holder()))
			info.setReturnValue(Arcanus.id("textures/entity/player/anonymous.png"));
	}
}
