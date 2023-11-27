package dev.cammiescorner.arcanuscontinuum.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonBlock.class)
public class PistonBlockMixin {
	@ModifyExpressionValue(method = "isMovable", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/block/BlockState;getHardness(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"
	))
	private static float arcanuscontinuum$dontMoveWardedBlocks(float original, BlockState state, World world, BlockPos pos, Direction direction, boolean canBreak, Direction pistonFacing) {
		return ArcanusComponents.isBlockWarded(world, pos) ? -1f : original;
	}
}
