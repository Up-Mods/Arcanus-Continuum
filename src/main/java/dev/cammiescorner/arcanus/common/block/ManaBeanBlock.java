package dev.cammiescorner.arcanus.common.block;

import dev.cammiescorner.arcanus.common.block.entities.ManaBeanBlockEntity;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ManaBeanBlock extends Block implements EntityBlock, BlockItemProvider {
	private static final int MAX_AGE = 7;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
	private static final VoxelShape SHAPE = Shapes.box(0.35, 0.875, 0.35, 0.65, 1, 0.65);
	private static final VoxelShape BEAN_POD_0 = Shapes.box(0.4375, 0.6875, 0.4375, 0.5625, 0.875, 0.5625);
	private static final VoxelShape BEAN_POD_1 = Shapes.box(0.40625, 0.625, 0.40625, 0.59375, 0.875, 0.59375);
	private static final VoxelShape BEAN_POD_2 = Shapes.box(0.34375, 0.5, 0.34375, 0.65625, 0.875, 0.65625);
	private static final VoxelShape BEAN_POD_3 = Shapes.box(0.28125, 0.3125, 0.28125, 0.71875, 0.875, 0.71875);

	public ManaBeanBlock() {
		super(Properties.of().sound(SoundType.GRASS).mapColor(MapColor.PLANT).offsetType(OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks());
		registerDefaultState(getStateDefinition().any().setValue(AGE, 0));
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos abovePos = pos.above();
		BlockState aboveState = level.getBlockState(abovePos);

		return aboveState.is(BlockTags.LOGS) && aboveState.isFaceSturdy(level, abovePos, Direction.DOWN);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if(!canSurvive(state, level, pos))
			destroy(level, pos, state);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		double chance = random.nextDouble();
		int currentAge = state.getValue(AGE);

		if(currentAge < MAX_AGE && chance > 0.75)
			level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(currentAge + 1, MAX_AGE)));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Vec3 vec3 = state.getOffset(level, pos);
		int age = state.getValue(AGE);

		VoxelShape shape = switch(age) {
			case 4 -> Shapes.or(SHAPE, BEAN_POD_0);
			case 5 -> Shapes.or(SHAPE, BEAN_POD_1);
			case 6 -> Shapes.or(SHAPE, BEAN_POD_2);
			case 7 -> Shapes.or(SHAPE, BEAN_POD_3);
			default -> SHAPE;
		};

		return shape.move(vec3.x, vec3.y, vec3.z);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ManaBeanBlockEntity(pos, state);
	}
}
