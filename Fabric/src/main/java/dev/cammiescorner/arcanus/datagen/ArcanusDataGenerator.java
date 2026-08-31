package dev.cammiescorner.arcanus.datagen;

import com.google.auto.service.AutoService;
import dev.cammiescorner.arcanus.datagen.client.ArcanusEnglishLanguageProvider;
import dev.cammiescorner.arcanus.datagen.client.ArcanusModelProvider;
import dev.cammiescorner.arcanus.datagen.common.*;
import dev.cammiescorner.arcanus.datagen.common.advancement.ArcanusBookAdvancements;
import dev.upcraft.sparkweave.api.datagen.DataGenerationContext;
import dev.upcraft.sparkweave.api.datagen.DynamicRegistryBuilder;
import dev.upcraft.sparkweave.api.datagen.Pack;
import dev.upcraft.sparkweave.api.entrypoint.DataGenerationEntryPoint;
import net.minecraft.data.advancements.AdvancementProvider;

import java.util.List;

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
		var blockTags = pack.addProvider(ArcanusBlockTagsProvider::new);

		pack.addProvider((output, registriesFuture) -> new ArcanusItemTagsProvider(output, registriesFuture, blockTags));
		pack.addProvider(ArcanusBiomeTagsProvider::new);
		pack.addProvider(ArcanusDamageTagsProvider::new);
		pack.addProvider(ArcanusEntityTagsProvider::new);
		pack.addProvider(ArcanusEnchantmentTagsProvider::new);
		pack.addProvider(ArcanusDimensionTagsProvider::new);
		pack.addRecipes(ArcanusRecipeProvider::new);
		pack.addProvider((output, registriesFuture) -> new AdvancementProvider(output, registriesFuture, List.of(
			new ArcanusBookAdvancements()
		)));

		pack.addProvider(DataGenerationContext::includeClient, ArcanusEnglishLanguageProvider::new);
		pack.addProvider(DataGenerationContext::includeClient, ArcanusModelProvider::new);
	}
}
