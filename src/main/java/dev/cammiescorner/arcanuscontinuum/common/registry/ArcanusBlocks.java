package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.blocks.ArcaneWorkbenchBlock;
import dev.cammiescorner.arcanuscontinuum.common.blocks.MagicBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;

public class ArcanusBlocks {
	//-----Block Map-----//
	private static final LinkedHashMap<Block, Identifier> BLOCKS = new LinkedHashMap<>();

	//-----Blocks-----//
	public static final Block ARCANE_WORKBENCH = create("arcane_workbench", new ArcaneWorkbenchBlock());
	public static final Block MAGIC_BLOCK = create("magic_block", new MagicBlock());

	//-----Registry-----//
	public static void register() {
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
		Registry.register(Registry.ITEM, BLOCKS.get(ARCANE_WORKBENCH), getItem(ARCANE_WORKBENCH));
	}

	private static BlockItem getItem(Block block) {
		return new BlockItem(block, new QuiltItemSettings());
	}

	private static <T extends Block> T create(String name, T block) {
		BLOCKS.put(block, Arcanus.id(name));
		return block;
	}
}
