package dev.upcraft.examplemod.datagen.client;

import dev.upcraft.sparkweave.api.datagen.ContextAwarePackOutput;
import dev.upcraft.sparkweave.api.datagen.TranslationBuilder;
import dev.upcraft.sparkweave.api.datagen.provider.client.SparkweaveLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.locale.Language;

import java.util.concurrent.CompletableFuture;

public class ExamplemodEnglishLanguageProvider extends SparkweaveLanguageProvider {

    public ExamplemodEnglishLanguageProvider(ContextAwarePackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, Language.DEFAULT);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registries, TranslationBuilder builder) {
        builder.add("examplemod.title", "Example Mod");
        builder.add("examplemod.config.links.website", "Website");
        builder.add("examplemod.config.links.discord", "Discord");
    }
}
