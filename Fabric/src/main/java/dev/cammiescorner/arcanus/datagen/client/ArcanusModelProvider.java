package dev.cammiescorner.arcanus.datagen.client;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.registry.ArcanusItems;
import dev.upcraft.sparkweave.api.datagen.ContextAwarePackOutput;
import dev.upcraft.sparkweave.api.datagen.provider.client.SparkweaveModelProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.world.level.block.Blocks;

public class ArcanusModelProvider extends SparkweaveModelProvider {

	public ArcanusModelProvider(ContextAwarePackOutput output) {
		super(output);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		blockModels.copyModel(Blocks.BARRIER, ArcanusBlocks.DUMMY_BOOKSHELF.get());
		blockModels.createTrivialCube(ArcanusBlocks.MAGIC_BLOCK.get());
		blockModels.createDoor(ArcanusBlocks.MAGIC_DOOR.get());
		blockModels.copyModel(ArcanusBlocks.MAGIC_BLOCK.get(), ArcanusBlocks.SPATIAL_RIFT_WALL.get());
		blockModels.copyModel(Blocks.DEEPSLATE_TILES, ArcanusBlocks.SPATIAL_RIFT_EXIT.get());

		var compendium = Arcanus.id("compendium_arcanus").withPrefix("stack/");
		ModelTemplates.FLAT_ITEM.create(compendium, TextureMapping.layer0(new Material(compendium)), blockModels.modelOutput);

		itemModels.generateFlatItem(ArcanusItems.SPELL_BOOK.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), ModelTemplates.FLAT_ITEM);

		itemModels.generateFlatItem(ArcanusItems.WIZARD_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ArcanusItems.OPOSSUM_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

		var wizardHatModel = ModelLocationUtils.getModelLocation(ArcanusItems.WIZARD_HAT.get());
		var wizardHatTexture = TextureMapping.getItemTexture(ArcanusItems.WIZARD_HAT.get());
		itemModels.generateLayeredItem(wizardHatModel, wizardHatTexture, wizardHatTexture.withSuffix("_trim"));
		var wizardRobesModel = ModelLocationUtils.getModelLocation(ArcanusItems.WIZARD_ROBES.get());
		var wizardRobesTexture = TextureMapping.getItemTexture(ArcanusItems.WIZARD_ROBES.get());
		itemModels.generateLayeredItem(wizardRobesModel, wizardRobesTexture, wizardRobesTexture.withSuffix("_trim"));
		itemModels.generateFlatItem(ArcanusItems.WIZARD_PANTS.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(ArcanusItems.WIZARD_BOOTS.get(), ModelTemplates.FLAT_ITEM);
	}
}
