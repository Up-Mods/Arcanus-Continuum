package dev.cammiescorner.arcanuscontinuum.mixin;

import dev.cammiescorner.arcanuscontinuum.common.entities.magic.ManaShieldEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.EntityView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EntityView.class)
public interface EntityViewMixin {
	@Inject(method = "getEntityCollisions", at = @At("HEAD"))
	private void arcanuscontinuum$collidesWithHead(@Nullable Entity entity, Box box, CallbackInfoReturnable<List<VoxelShape>> info) {
		ManaShieldEntity.COLLIDING_ENTITY.set(entity);
	}

	@Inject(method = "getEntityCollisions", at = @At("RETURN"))
	private void arcanuscontinuum$collidesWithReturn(@Nullable Entity entity, Box box, CallbackInfoReturnable<List<VoxelShape>> info) {
		ManaShieldEntity.COLLIDING_ENTITY.remove();
	}
}
