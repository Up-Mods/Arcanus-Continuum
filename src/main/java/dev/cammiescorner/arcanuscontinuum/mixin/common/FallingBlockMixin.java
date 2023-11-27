package dev.cammiescorner.arcanuscontinuum.mixin.common;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlock.class)
public class FallingBlockMixin {
	@Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$wardedBlocksCantFall(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random, CallbackInfo info) {
		if(ArcanusComponents.isBlockWarded(world, pos))
			info.cancel();
	}
}
