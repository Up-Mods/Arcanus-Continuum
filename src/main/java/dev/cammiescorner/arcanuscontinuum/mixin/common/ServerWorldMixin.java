package dev.cammiescorner.arcanuscontinuum.mixin.common;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	@WrapWithCondition(method = "tickEntity", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;tick()V"
	))
	private boolean arcanuscontinuum$blockEntityTick(Entity entity) {
		return !ArcanusComponents.areUpdatesBlocked(entity);
	}
}
