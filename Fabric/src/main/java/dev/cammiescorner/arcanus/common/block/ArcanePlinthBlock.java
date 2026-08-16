package dev.cammiescorner.arcanus.common.block;

import com.mojang.serialization.MapCodec;
import dev.cammiescorner.arcanus.common.block.entities.ArcanePlinthBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ArcanePlinthBlock extends AbstractPedestalBlock<ArcanePlinthBlockEntity> {
	private static final VoxelShape SHAPE = Shapes.or(
		Shapes.box(0.0625, 0,    0.0625, 0.9375, 0.25, 0.9375),
		Shapes.box(0.125,  0.25, 0.125,  0.875,  0.5,  0.875)
	);
	private static final MapCodec<ArcanePlinthBlock> CODEC = simpleCodec(ArcanePlinthBlock::new);

	public ArcanePlinthBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable ArcanePlinthBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ArcanePlinthBlockEntity(pos, state);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected MapCodec<? extends AbstractPedestalBlock<ArcanePlinthBlockEntity>> codec() {
		return CODEC;
	}
}
