package dev.cammiescorner.arcanus.common.block;

import dev.cammiescorner.arcanus.common.block.entities.JarBlockEntity;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class JarBlock extends Block implements BlockItemProvider, EntityBlock {
	private static final VoxelShape SHAPE = Shapes.or(
		Shapes.box(0.1875,  0,    0.1875,  0.8125,  0.75,  0.8125),
		Shapes.box(0.28125, 0.75, 0.28125, 0.71875, 0.875, 0.71875)
	);
	public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 9);

	public JarBlock() {
		super(Properties.of().noOcclusion());
		registerDefaultState(getStateDefinition().any().setValue(LEVEL, 0));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new JarBlockEntity(pos, state);
	}
}
