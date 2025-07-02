package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class DummyBookshelfBlockEntity extends BlockEntity {
	@Nullable
	private ResourceKey<LootTable> lootTableId;
	private long lootTableSeed = 0L;

	public DummyBookshelfBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.DUMMY_BOOKSHELF.get(), pos, blockState);
	}

	public static BlockState copyValues(BlockState to, BlockState from) {
		for(var property : from.getProperties())
			to = tryCopyValue(to, from, property);

		return to;
	}

	public static <T extends Comparable<T>> BlockState tryCopyValue(BlockState to, BlockState from, Property<T> key) {
		if(to.getProperties().contains(key))
			return to.setValue(key, from.getValue(key));

		return to;
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);

		if(lootTableId != null) {
			tag.putString("LootTable", lootTableId.location().toString());

			if (lootTableSeed != 0L)
				tag.putLong("Seed", lootTableSeed);
		}
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);

		if(tag.contains("LootTable", Tag.TAG_STRING)) {
			lootTableId = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(tag.getString("LootTable")));

			if (tag.contains("Seed", Tag.TAG_LONG))
				lootTableSeed = tag.getLong("Seed");
		}
	}

	public static void tick(Level level, BlockPos blockPos, BlockState state, DummyBookshelfBlockEntity blockEntity) {
		if(level instanceof ServerLevel serverLevel) {
			level.setBlock(blockPos, copyValues(Blocks.CHISELED_BOOKSHELF.defaultBlockState(), state), Block.UPDATE_SUPPRESS_DROPS);

			level.getBlockEntity(blockPos, BlockEntityType.CHISELED_BOOKSHELF).ifPresent(be -> {
				var lootTableId = blockEntity.getLootTable();
				var bookshelfState = be.getBlockState();

				if(lootTableId != null) {
					var lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(lootTableId);
					var builder = new LootParams.Builder(serverLevel).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(be.getBlockPos()));
					lootTable.fill(be, builder.create(LootContextParamSets.CHEST), blockEntity.getLootSeed());
					be.setChanged();

					for(int i = 0; i < ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.size(); i++) {
						boolean bl = !be.getItem(i).isEmpty();
						var property = ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
						bookshelfState = bookshelfState.setValue(property, bl);
					}
				}

				level.setBlock(be.getBlockPos(), bookshelfState, Block.UPDATE_ALL);
			});
		}
	}

	public void setLootTable(@Nullable ResourceKey<LootTable> lootTableId) {
		this.lootTableId = lootTableId;
	}

	@Nullable
	public ResourceKey<LootTable> getLootTable() {
		return lootTableId;
	}

	public void setLootSeed(long lootTableSeed) {
		this.lootTableSeed = lootTableSeed;
	}

	public long getLootSeed() {
		return this.lootTableSeed;
	}
}
