package dev.cammiescorner.arcanuscontinuum.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.cammiescorner.arcanuscontinuum.client.utils.EntityRendererSorter;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@ModifyExpressionValue(method = "render", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/world/ClientWorld;getEntities()Ljava/lang/Iterable;"
	))
	private Iterable<Entity> arcanuscontinuum$sortEntitiesForRendering(Iterable<Entity> entities) {
		return EntityRendererSorter.ENTITY_ORDERING.sortedCopy(entities);
	}
}
