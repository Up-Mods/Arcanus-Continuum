package dev.cammiescorner.arcanuscontinuum.mixin.common;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {
	@Shadow public abstract BlockState getBlockState(BlockPos pos);

	@Inject(method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$noReplacingWardedBlocks(BlockPos pos, BlockState state, int flags, int maxUpdateDepth, CallbackInfoReturnable<Boolean> info) {
		if(state.isAir() && ArcanusComponents.isBlockWarded((World) (Object) this, pos))
			info.setReturnValue(false);
	}

	@Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$noBreakingWardedBlocks(BlockPos pos, boolean drop, Entity breakingEntity, int maxUpdateDepth, CallbackInfoReturnable<Boolean> info) {
		BlockState state = getBlockState(pos);

		if(state.isAir() && ArcanusComponents.isBlockWarded((World) (Object) this, pos))
			info.setReturnValue(false);
	}
}
