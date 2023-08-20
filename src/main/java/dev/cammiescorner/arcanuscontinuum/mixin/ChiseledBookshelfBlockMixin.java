package dev.cammiescorner.arcanuscontinuum.mixin;

import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChiseledBookshelfBlock.class)
public abstract class ChiseledBookshelfBlockMixin extends Block {
	public ChiseledBookshelfBlockMixin(Settings settings) { super(settings); }

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockWithEntity;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
	private static AbstractBlock.Settings arcanuscontinuum$enableChiseledBookshelf(AbstractBlock.Settings settings) {
		return AbstractBlock.Settings.of(Material.WOOD).strength(1.5F).sounds(BlockSoundGroup.CHISELED_BOOKSHELF);
	}

	// Mojang please ;-;
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(HorizontalFacingBlock.FACING, rotation.rotate(state.get(HorizontalFacingBlock.FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(HorizontalFacingBlock.FACING)));
	}
}
