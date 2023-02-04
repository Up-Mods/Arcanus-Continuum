package dev.cammiescorner.arcanuscontinuum.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChiseledBookshelfBlock.class)
public class ChiseledBookshelfBlockMixin {
	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockWithEntity;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
	private static AbstractBlock.Settings arcanuscontinuum$enableChiseledBookshelf(AbstractBlock.Settings settings) {
		return AbstractBlock.Settings.of(Material.WOOD).strength(1.5F).sounds(BlockSoundGroup.f_oueonadr);
	}
}
