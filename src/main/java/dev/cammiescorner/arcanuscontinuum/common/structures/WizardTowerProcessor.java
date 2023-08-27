package dev.cammiescorner.arcanuscontinuum.common.structures;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class WizardTowerProcessor extends StructureProcessor {
	public static final WizardTowerProcessor INSTANCE = new WizardTowerProcessor();
	public static final Codec<WizardTowerProcessor> CODEC = Codec.unit(() -> WizardTowerProcessor.INSTANCE);

	@Nullable
	@Override
	public Structure.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, Structure.StructureBlockInfo localBlockInfo, Structure.StructureBlockInfo absoluteBlockInfo, StructurePlacementData placementData) {
		if(!absoluteBlockInfo.state().isOf(Blocks.CHISELED_BOOKSHELF))
			return absoluteBlockInfo;

		SimpleInventory inventory = new SimpleInventory(6);
		Identifier lootTableId = Arcanus.id("bookshelves/wizard_tower");
		long lootTableSeed = placementData.getRandom(absoluteBlockInfo.pos()).nextLong();
		BlockState blockState = absoluteBlockInfo.state();

		if(world instanceof ServerWorldAccess serverWorld) {
			LootTable lootTable = serverWorld.getServer().getLootManager().getLootTable(lootTableId);
			LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder(serverWorld.toServerWorld()).add(LootContextParameters.ORIGIN, Vec3d.ofCenter(absoluteBlockInfo.pos()));
			lootTable.supplyInventory(inventory, builder.build(LootContextTypes.CHEST), lootTableSeed);
			Inventories.writeNbt(absoluteBlockInfo.nbt(), inventory.stacks, true);

			for(int j = 0; j < ChiseledBookshelfBlock.SLOT_OCCUPATION_PROPERTIES.size(); ++j) {
				boolean bl = !inventory.getStack(j).isEmpty();
				BooleanProperty booleanProperty = ChiseledBookshelfBlock.SLOT_OCCUPATION_PROPERTIES.get(j);
				blockState = blockState.with(booleanProperty, bl);
			}
		}

		return new Structure.StructureBlockInfo(absoluteBlockInfo.pos(), blockState, absoluteBlockInfo.nbt());
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return Arcanus.WIZARD_TOWER_PROCESSOR;
	}
}
