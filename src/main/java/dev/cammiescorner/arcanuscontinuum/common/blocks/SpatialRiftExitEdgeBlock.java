package dev.cammiescorner.arcanuscontinuum.common.blocks;

import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class SpatialRiftExitEdgeBlock extends HorizontalFacingBlock implements BlockEntityProvider {
	public static final BooleanProperty CORNER = BooleanProperty.of("corner");

	public SpatialRiftExitEdgeBlock() {
		super(QuiltBlockSettings.copyOf(ArcanusBlocks.UNBREAKABLE_MAGIC_BLOCK.get())
			.sounds(BlockSoundGroup.STONE)
			.luminance(value -> 9)
		);
		setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(CORNER, false));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING).add(CORNER);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new MagicBlockEntity(pos, state);
	}
}
