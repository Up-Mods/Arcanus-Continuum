package dev.cammiescorner.arcanus.common.block;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class ManaBeanBlock extends Block implements BlockItemProvider {
	private static final int MAX_AGE = 7;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
	private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 12, 11);
	private final Supplier<? extends Arcana> arcana;

	public ManaBeanBlock(Supplier<? extends Arcana> arcana) {
		super(Properties.of().sound(SoundType.GRASS).mapColor(MapColor.PLANT).noCollission().noTerrainParticles().offsetType(OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks());
		this.arcana = arcana;
		registerDefaultState(getStateDefinition().any().setValue(AGE, 0));
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if(!canSurvive(state, level, pos))
			destroy(level, pos, state);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		int currentStage = state.getValue(AGE);

		if(currentStage < MAX_AGE && random.nextDouble() > 0.5)
			state.setValue(AGE, Math.min(currentStage + random.nextInt(), MAX_AGE));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Vec3 vec3 = state.getOffset(level, pos);
		return SHAPE.move(vec3.x, vec3.y, vec3.z);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	public Arcana getArcanaType() {
		return arcana.get();
	}
}
