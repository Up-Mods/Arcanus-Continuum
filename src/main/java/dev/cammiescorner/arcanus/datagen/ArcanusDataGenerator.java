package dev.cammiescorner.arcanus.datagen;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.datagen.client.ArcanusEnglishLanguageProvider;
import dev.cammiescorner.arcanus.datagen.client.ArcanusModelProvider;
import dev.cammiescorner.arcanus.datagen.common.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;

public class ArcanusDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void buildRegistry(RegistrySetBuilder builder) {
		DynamicRegistryEntryProvider.builder(Arcanus.MOD_ID)
			.add(ArcanusBiomeProvider::new)
			.add(ArcanusDamageTypeProvider::new)
			.add(ArcanusDimensionProvider::new)
			.add(ArcanusStructureProvider::new)
			.build(builder);
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		var pack = generator.createPack();
		pack.addProvider((output, registriesFuture) -> DynamicRegistryEntryProvider.getGenerator(Arcanus.MOD_ID, output, registriesFuture));

		var blockTags = pack.addProvider(ArcanusBlockTagsProvider::new);
		pack.addProvider((output, registriesFuture) -> new ArcanusItemTagsProvider(output, registriesFuture, blockTags));
		pack.addProvider(ArcanusBiomeTagsProvider::new);
		pack.addProvider(ArcanusDamageTagsProvider::new);
		pack.addProvider(ArcanusEntityTagsProvider::new);
		pack.addProvider(ArcanusEnchantmentTagsProvider::new);
		pack.addProvider(ArcanusDimensionTagsProvider::new);
		pack.addProvider(ArcanusAdvancementRewardProvider::new);
		pack.addProvider(ArcanusBlockLootProvider::new);
		pack.addProvider(ArcanusChestLootProvider::new);
		pack.addProvider(ArcanusRecipeProvider::new);
		pack.addProvider(ArcanusAdvancementProvider::new);
		pack.addProvider(ArcanusLevelStemProvider::new);

		pack.addProvider(ArcanusEnglishLanguageProvider::new);
		pack.addProvider(ArcanusModelProvider::new);
	}
}
