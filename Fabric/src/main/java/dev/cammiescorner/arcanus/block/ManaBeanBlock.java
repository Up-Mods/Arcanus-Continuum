package dev.cammiescorner.arcanus.block;

import dev.cammiescorner.arcanus.block.entities.ManaBeanBlockEntity;
import dev.cammiescorner.arcanus.item.ManaBeanItem;
import dev.cammiescorner.arcanus.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.registry.ArcanusDataComponents;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.redstone.Orientation;
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

	public ManaBeanBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(getStateDefinition().any().setValue(AGE, 0));
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos abovePos = pos.above();
		BlockState aboveState = level.getBlockState(abovePos);

		return aboveState.is(BlockTags.LOGS) && aboveState.isFaceSturdy(level, abovePos, Direction.DOWN);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @org.jspecify.annotations.Nullable Orientation orientation, boolean movedByPiston) {
		if(!state.canSurvive(level, pos))
			level.destroyBlock(pos, true);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		double chance = random.nextDouble();
		int currentAge = state.getValue(AGE);

		if(currentAge < MAX_AGE && chance > 0.85)
			level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(currentAge + 1, MAX_AGE)));
	}

	@Override
	protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
		ItemStack stack = new ItemStack(ArcanusBlocks.MANA_BEAN.get());

		if(level.getBlockEntity(pos) instanceof ManaBeanBlockEntity manaBean)
			stack.set(ArcanusDataComponents.ARCANA.get(), manaBean.getArcana());

		return stack;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		int age = state.getValue(AGE);

		return switch(age) {
			case 4 -> Shapes.or(SHAPE, BEAN_POD_0);
			case 5 -> Shapes.or(SHAPE, BEAN_POD_1);
			case 6 -> Shapes.or(SHAPE, BEAN_POD_2);
			case 7 -> Shapes.or(SHAPE, BEAN_POD_3);
			default -> SHAPE;
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ManaBeanBlockEntity(pos, state);
	}

	@Override
	public Item createItem(ResourceKey<Block> blockId) {
		return new ManaBeanItem(this, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, blockId.identifier())).useBlockDescriptionPrefix().food(new FoodProperties.Builder().nutrition(1).alwaysEdible().build()).component(ArcanusDataComponents.ARCANA.get(), ArcanusArcana.NIL.get()));
	}
}
