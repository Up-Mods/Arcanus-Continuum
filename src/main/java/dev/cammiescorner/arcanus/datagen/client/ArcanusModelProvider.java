package dev.cammiescorner.arcanus.datagen.client;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.level.block.Blocks;

public class ArcanusModelProvider extends SparkweaveModelProvider {
	public ArcanusModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators gen) {
		copyModelNoItem(gen, Blocks.BARRIER, ArcanusBlocks.DUMMY_BOOKSHELF.get());
		gen.createTrivialCube(ArcanusBlocks.MAGIC_BLOCK.get());
		gen.createDoor(ArcanusBlocks.MAGIC_DOOR.get());
		copyModelNoItem(gen, ArcanusBlocks.MAGIC_BLOCK.get(), ArcanusBlocks.SPATIAL_RIFT_WALL.get());
		copyModelNoItem(gen, Blocks.DEEPSLATE_TILES, ArcanusBlocks.SPATIAL_RIFT_EXIT.get());
	}

	@Override
	public void generateItemModels(ItemModelGenerators gen) {
		var compendium = Arcanus.id("compendium_arcanus").withPrefix("item/");
		ModelTemplates.FLAT_ITEM.create(compendium, TextureMapping.layer0(compendium), gen.output);

		gen.generateFlatItem(ArcanusItems.SPELL_BOOK.get(), ModelTemplates.FLAT_ITEM);
		gen.generateFlatItem(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), ModelTemplates.FLAT_ITEM);

		gen.createSpawnEgg(ArcanusItems.WIZARD_SPAWN_EGG);
		gen.createSpawnEgg(ArcanusItems.OPOSSUM_SPAWN_EGG);

		var wizardHatModel = ModelLocationUtils.getModelLocation(ArcanusItems.WIZARD_HAT.get());
		var wizardHatTexture = TextureMapping.getItemTexture(ArcanusItems.WIZARD_HAT.get());
		gen.generateLayeredItem(wizardHatModel, wizardHatTexture, wizardHatTexture.withSuffix("_trim"));
		var wizardRobesModel = ModelLocationUtils.getModelLocation(ArcanusItems.WIZARD_ROBES.get());
		var wizardRobesTexture = TextureMapping.getItemTexture(ArcanusItems.WIZARD_ROBES.get());
		gen.generateLayeredItem(wizardRobesModel, wizardRobesTexture, wizardRobesTexture.withSuffix("_trim"));
		gen.generateFlatItem(ArcanusItems.WIZARD_PANTS.get(), ModelTemplates.FLAT_ITEM);
		gen.generateFlatItem(ArcanusItems.WIZARD_BOOTS.get(), ModelTemplates.FLAT_ITEM);
	}
}
