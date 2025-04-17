package dev.cammiescorner.arcanus.datagen;

import com.google.auto.service.AutoService;
import dev.cammiescorner.arcanus.datagen.client.ArcanusEnglishLanguageProvider;
import dev.cammiescorner.arcanus.datagen.common.ArcanusBiomeProvider;
import dev.cammiescorner.arcanus.datagen.common.ArcanusDamageTypeProvider;
import dev.cammiescorner.arcanus.datagen.common.ArcanusDimensionProvider;
import dev.cammiescorner.arcanus.datagen.common.ArcanusStructureProvider;
import dev.upcraft.sparkweave.api.datagen.DataGenerationContext;
import dev.upcraft.sparkweave.api.datagen.DynamicRegistryBuilder;
import dev.upcraft.sparkweave.api.datagen.Pack;
import dev.upcraft.sparkweave.api.entrypoint.DataGenerationEntryPoint;

@AutoService(DataGenerationEntryPoint.class)
public class ArcanusDataGenerator implements DataGenerationEntryPoint {
	@Override
	public void generateDynamicRegistryEntries(DynamicRegistryBuilder builder) {
		builder.add(ArcanusBiomeProvider::new);
		builder.add(ArcanusDamageTypeProvider::new);
		builder.add(ArcanusDimensionProvider::new);
		builder.add(ArcanusStructureProvider::new);
	}

	@Override
	public void generate(DataGenerationContext ctx) {
		// TODO fix data gen stuff
		Pack pack = ctx.getDefaultPack();
//		FabricTagProvider.BlockTagProvider blockTags = pack.addProvider(DataGenerationContext::includeClient, ArcanusBlockTagsProvider::new);

//		pack.addProvider(DataGenerationContext::includeClient, (output, registriesFuture) -> new ArcanusItemTagsProvider(output, registriesFuture, blockTags));
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusBiomeTagsProvider::new);
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusDamageTagsProvider::new);
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusEntityTagsProvider::new);
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusEnchantmentTagsProvider::new);
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusDimensionTagsProvider::new);
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusBlockLootProvider::new);
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusChestLootProvider::new);
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusRecipeProvider::new);
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusAdvancementProvider::new);
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusLevelStemProvider::new);

		pack.addProvider(DataGenerationContext::includeClient, ArcanusEnglishLanguageProvider::new);
//		pack.addProvider(DataGenerationContext::includeClient, ArcanusModelProvider::new);
	}
}
