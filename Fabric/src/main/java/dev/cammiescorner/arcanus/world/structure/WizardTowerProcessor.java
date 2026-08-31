package dev.cammiescorner.arcanus.world.structure;

import com.mojang.serialization.MapCodec;
import dev.cammiescorner.arcanus.block.entities.DummyBookshelfBlockEntity;
import dev.cammiescorner.arcanus.data.ArcanusLootTables;
import dev.cammiescorner.arcanus.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.registry.ArcanusStructureProcessorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class WizardTowerProcessor extends StructureProcessor {
	public static final WizardTowerProcessor INSTANCE = new WizardTowerProcessor();
	public static final MapCodec<WizardTowerProcessor> CODEC = MapCodec.unit(() -> WizardTowerProcessor.INSTANCE);

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings placementData) {
		if(!relativeBlockInfo.state().is(Blocks.CHISELED_BOOKSHELF))
			return relativeBlockInfo;

		var random = placementData.getRandom(relativeBlockInfo.pos());

		var blockState = DummyBookshelfBlockEntity.copyValues(ArcanusBlocks.DUMMY_BOOKSHELF.get().defaultBlockState(), relativeBlockInfo.state());
		var be = new DummyBookshelfBlockEntity(offset, blockState);
		be.setLootTable(ArcanusLootTables.WIZARD_TOWER_BOOKSHELF);
		be.setLootSeed(random.nextLong());

		return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), blockState, be.saveWithId(level.registryAccess()));
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return ArcanusStructureProcessorTypes.WIZARD_TOWER.get();
	}
}
