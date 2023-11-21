package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.blocks.ArcaneWorkbenchBlock;
import dev.cammiescorner.arcanuscontinuum.common.blocks.MagicBlock;
import dev.cammiescorner.arcanuscontinuum.common.blocks.MagicDoorBlock;
import dev.cammiescorner.arcanuscontinuum.common.blocks.UnbreakableMagicBlock;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;

public class ArcanusBlocks {
	public static final RegistryHandler<Block> BLOCKS = RegistryHandler.create(RegistryKeys.BLOCK, Arcanus.MOD_ID);

	public static final RegistrySupplier<Block> MAGIC_DOOR = BLOCKS.register("magic_door", MagicDoorBlock::new);
	public static final RegistrySupplier<Block> ARCANE_WORKBENCH = BLOCKS.register("arcane_workbench", ArcaneWorkbenchBlock::new);
	public static final RegistrySupplier<Block> MAGIC_BLOCK = BLOCKS.register("magic_block", MagicBlock::new);
	public static final RegistrySupplier<Block> UNBREAKABLE_MAGIC_BLOCK = BLOCKS.register("unbreakable_magic_block", UnbreakableMagicBlock::new);
}
