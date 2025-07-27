package dev.cammiescorner.arcanus.common.block;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class ManaFruitBlock extends Block implements BlockItemProvider {
	private final ManaType manaType;

	public ManaFruitBlock(ManaType manaType) {
		super(Properties.ofFullCopy(Blocks.PUMPKIN).noOcclusion().pushReaction(PushReaction.DESTROY).randomTicks());
		this.manaType = manaType;
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		// TODO biome checks
		return super.canSurvive(state, level, pos);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if(!canSurvive(state, level, pos))
			destroy(level, pos, state);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		// TODO grow the boy
	}

	public ManaType getManaType() {
		return manaType;
	}
}
