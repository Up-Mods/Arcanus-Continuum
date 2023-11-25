package dev.cammiescorner.arcanuscontinuum.mixin.common;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
	@Inject(method = "calcBlockBreakingDelta", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$wardedBlockBreakStatus(PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> info) {
		if(ArcanusComponents.isBlockWarded((World) world, pos))
			info.setReturnValue(0f);
	}
}
