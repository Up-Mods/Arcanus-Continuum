package dev.cammiescorner.arcanus.common.block;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StemBlock;

public class ManaStemBlock extends StemBlock {
	public ManaStemBlock(ManaType manaType, ResourceKey<Block> fruit, ResourceKey<Block> attachedStem, ResourceKey<Item> seed, Properties properties) {
		super(fruit, attachedStem, seed, properties);
	}
}
