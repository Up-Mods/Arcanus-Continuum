package dev.cammiescorner.arcanus.block;

import dev.cammiescorner.arcanus.block.entities.SpatialRiftExitBlockEntity;
import dev.cammiescorner.arcanus.component.level.PocketDimensionComponent;
import dev.upcraft.sparkweave.api.scheduler.Tasks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SpatialRiftExitBlock extends Block implements EntityBlock {
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	public SpatialRiftExitBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(getStateDefinition().any().setValue(ACTIVE, false));
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if(!level.isClientSide()) {
			// the game assumes that at the end of the right click, the player is still in the same dimension.
			// therefore we need to schedule the teleportation at some later point
			Tasks.scheduleEphemeral(() -> PocketDimensionComponent.get(level).teleportOutOfPocketDimension(player), 0L);
		}

		return InteractionResult.SUCCESS_SERVER;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ACTIVE);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		if(state.getValue(ACTIVE))
			return new SpatialRiftExitBlockEntity(pos, state);

		return null;
	}
}
