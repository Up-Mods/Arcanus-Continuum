package dev.cammiescorner.arcanus.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderers.class)
public class EntityRenderersMixin {
	@WrapOperation(method = "validateRegistrations", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/EntityType;PLAYER:Lnet/minecraft/world/entity/EntityType;"))
	private static EntityType<?> excludeCultists(Operation<EntityType<Player>> original, @Local EntityType<?> entityType) {
		if(entityType == ArcanusEntities.CULTIST_CLERIC.get())
			return ArcanusEntities.CULTIST_CLERIC.get();
		if(entityType == ArcanusEntities.CULTIST_KNIGHT.get())
			return ArcanusEntities.CULTIST_KNIGHT.get();

		return original.call();
	}
}
