package dev.cammiescorner.arcanus.block;

import com.mojang.serialization.MapCodec;
import dev.cammiescorner.arcanus.block.entities.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends AbstractPedestalBlock<PedestalBlockEntity> {
	private static final VoxelShape SHAPE = Shapes.or(
		Shapes.box(0.0625, 0,    0.0625, 0.9375, 0.25, 0.9375),
		Shapes.box(0.25,   0.25, 0.25,   0.75,   0.75, 0.75),
		Shapes.box(0.125,  0.75, 0.125,  0.875,  1,    0.875)
	);
	private static final MapCodec<PedestalBlock> CODEC = simpleCodec(PedestalBlock::new);

	public PedestalBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable PedestalBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PedestalBlockEntity(pos, state);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected MapCodec<? extends AbstractPedestalBlock<PedestalBlockEntity>> codec() {
		return CODEC;
	}
}
