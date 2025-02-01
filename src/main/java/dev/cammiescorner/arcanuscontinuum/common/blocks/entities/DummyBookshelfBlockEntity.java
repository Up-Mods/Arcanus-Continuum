package dev.cammiescorner.arcanuscontinuum.common.blocks.entities;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalLong;

public class DummyBookshelfBlockEntity extends BlockEntity {

	@Nullable
	private ResourceLocation lootTableId;
	@Nullable
	private Long lootTableSeed;

	public DummyBookshelfBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.DUMMY_BOOKSHELF.get(), pos, blockState);
	}

	public static BlockState copyValues(BlockState to, BlockState from) {
		for (var property : from.getProperties()) {
			to = tryCopyValue(to, from, property);
		}

		return to;
	}

	public static <T extends Comparable<T>> BlockState tryCopyValue(BlockState to, BlockState from, Property<T> key) {
		if (to.getProperties().contains(key)) {
			return to.setValue(key, from.getValue(key));
		}

		return to;
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		if (lootTableId != null) {
			tag.putString("LootTable", lootTableId.toString());
		}

		if (lootTableSeed != null) {
			tag.putLong("Seed", lootTableSeed);
		}
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("LootTable", Tag.TAG_STRING)) {
			lootTableId = ResourceLocation.tryParse(tag.getString("LootTable"));
		}

		if (tag.contains("Seed", Tag.TAG_LONG)) {
			lootTableSeed = tag.getLong("Seed");
		}
	}

	public static void tick(Level level, BlockPos blockPos, BlockState state, DummyBookshelfBlockEntity blockEntity) {
		if (level instanceof ServerLevel serverLevel) {
			level.setBlock(blockPos, copyValues(Blocks.CHISELED_BOOKSHELF.defaultBlockState(), state), Block.UPDATE_SUPPRESS_DROPS);

			var lootTableId = blockEntity.getLootTable();
			var lootTableSeed = blockEntity.getLootSeed();

			level.getBlockEntity(blockPos, BlockEntityType.CHISELED_BOOKSHELF).ifPresent(be -> {
				var bookshelfState = be.getBlockState();

				if (lootTableId != null) {
					var lootTable = serverLevel.getServer().getLootData().getLootTable(lootTableId);
					var builder = new LootParams.Builder(serverLevel).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(be.getBlockPos()));
					lootTable.fill(be, builder.create(LootContextParamSets.CHEST), lootTableSeed.orElseGet(() -> serverLevel.getRandom().nextLong()));
					be.setChanged();

					for (int i = 0; i < ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.size(); i++) {
						boolean bl = !be.getItem(i).isEmpty();
						var property = ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
						bookshelfState = bookshelfState.setValue(property, bl);
					}
				}

				level.setBlock(be.getBlockPos(), bookshelfState, Block.UPDATE_ALL);
			});
		}
	}

	public void setLootTable(@Nullable ResourceLocation lootTableId) {
		this.lootTableId = lootTableId;
	}

	@Nullable
	public ResourceLocation getLootTable() {
		return lootTableId;
	}

	public void setLootSeed(@Nullable Long lootTableSeed) {
		this.lootTableSeed = lootTableSeed;
	}

	public OptionalLong getLootSeed() {
		return this.lootTableSeed != null ? OptionalLong.of(this.lootTableSeed) : OptionalLong.empty();
	}
}
