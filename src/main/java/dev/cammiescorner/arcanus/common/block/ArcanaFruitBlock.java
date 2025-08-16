package dev.cammiescorner.arcanus.common.block;

import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArcanaFruitBlock extends Block implements BlockItemProvider, BonemealableBlock {
	private static final int MAX_STAGE = 6;
	public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, MAX_STAGE);
	private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 12, 11);
	private final PrimalArcana primalArcana;

	public ArcanaFruitBlock(PrimalArcana primalArcana) {
		super(Properties.of().sound(SoundType.GRASS).mapColor(MapColor.PLANT).noCollission().noTerrainParticles().offsetType(OffsetType.XYZ).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks());
		this.primalArcana = primalArcana;
		registerDefaultState(getStateDefinition().any().setValue(STAGE, 0));
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
		int currentStage = state.getValue(STAGE);

		if(currentStage < MAX_STAGE && random.nextDouble() > 0.5)
			state.setValue(STAGE, Math.min(currentStage + random.nextInt(), MAX_STAGE));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Vec3 vec3 = state.getOffset(level, pos);
		return SHAPE.move(vec3.x, vec3.y, vec3.z);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(STAGE);
	}

	public PrimalArcana getArcanaType() {
		return primalArcana;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return state.getValue(STAGE) < MAX_STAGE;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return random.nextDouble() > 0.9 && state.getValue(STAGE) < MAX_STAGE;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		level.setBlockAndUpdate(pos, state.setValue(STAGE, Math.min(state.getValue(STAGE) + random.nextInt(1, 3), MAX_STAGE)));
	}
}
