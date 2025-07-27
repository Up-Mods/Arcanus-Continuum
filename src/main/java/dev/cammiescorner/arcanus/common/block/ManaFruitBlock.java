package dev.cammiescorner.arcanus.common.block;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ManaFruitBlock extends Block implements BlockItemProvider {
	private final ManaType manaType;

	public ManaFruitBlock(ManaType manaType) {
		super(Properties.ofFullCopy(Blocks.PUMPKIN));
		this.manaType = manaType;
	}

	public ManaType getManaType() {
		return manaType;
	}
}
