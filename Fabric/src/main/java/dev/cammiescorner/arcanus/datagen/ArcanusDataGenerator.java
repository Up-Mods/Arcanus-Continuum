package dev.cammiescorner.arcanus.datagen;

import com.google.auto.service.AutoService;
import dev.cammiescorner.arcanus.datagen.client.ArcanusEnglishLanguageProvider;
import dev.cammiescorner.arcanus.datagen.client.ArcanusModelProvider;
import dev.cammiescorner.arcanus.datagen.common.*;
import dev.cammiescorner.arcanus.datagen.common.advancement.ArcanusBookAdvancements;
import dev.cammiescorner.arcanus.datagen.common.loot.ArcanusBlockLoot;
import dev.cammiescorner.arcanus.datagen.common.loot.ArcanusChestLoot;
import dev.upcraft.sparkweave.api.datagen.DataGenerationContext;
import dev.upcraft.sparkweave.api.datagen.DynamicRegistryBuilder;
import dev.upcraft.sparkweave.api.datagen.Pack;
import dev.upcraft.sparkweave.api.entrypoint.DataGenerationEntryPoint;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

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
		// TODO pass required loot tables set
		pack.addProvider((output, registriesFuture) -> new LootTableProvider(output, Set.of(), List.of(
			new LootTableProvider.SubProviderEntry(ArcanusBlockLoot::new, LootContextParamSets.BLOCK),
			new LootTableProvider.SubProviderEntry(ArcanusChestLoot::new, LootContextParamSets.CHEST)
		), registriesFuture));

		pack.addProvider(DataGenerationContext::includeClient, ArcanusEnglishLanguageProvider::new);
		pack.addProvider(DataGenerationContext::includeClient, ArcanusModelProvider::new);
	}
}
