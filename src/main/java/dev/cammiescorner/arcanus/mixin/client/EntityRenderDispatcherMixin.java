package dev.cammiescorner.arcanus.mixin.client;

import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.common.entity.living.Cultist;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

// TODO this doesnt work and i dont know how to make it work mojang why does this have to be so complicated bruh
@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
	@Unique private Map<PlayerSkin.Model, EntityRenderer<? extends Cultist>> cultistRenderers = Map.of();

	@Inject(method = "getRenderer", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 2), cancellable = true)
	private <T extends Entity> void getCultistRenderer(T entity, CallbackInfoReturnable<EntityRenderer<?>> cir) {
		if(entity instanceof Cultist) {
			PlayerSkin.Model model = DefaultPlayerSkin.get(entity.getUUID()).model();
			EntityRenderer<? extends Cultist> entityRenderer = cultistRenderers.get(model);
			cir.setReturnValue(entityRenderer != null ? entityRenderer : cultistRenderers.get(PlayerSkin.Model.WIDE));
		}
	}

	@Inject(method = "onResourceManagerReload", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
	private void setCultistRenderers(ResourceManager resourceManager, CallbackInfo ci, EntityRendererProvider.Context context) {
		this.cultistRenderers = ArcanusClient.createCultistRenderers(context);
	}
}
