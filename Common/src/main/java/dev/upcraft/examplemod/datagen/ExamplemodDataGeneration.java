package dev.upcraft.examplemod.datagen;

import com.google.auto.service.AutoService;
import dev.upcraft.examplemod.datagen.client.ExamplemodEnglishLanguageProvider;
import dev.upcraft.sparkweave.api.datagen.DataGenerationContext;
import dev.upcraft.sparkweave.api.datagen.DynamicRegistryBuilder;
import dev.upcraft.sparkweave.api.entrypoint.DataGenerationEntryPoint;

@AutoService(DataGenerationEntryPoint.class)
public class ExamplemodDataGeneration implements DataGenerationEntryPoint {

    @Override
    public void generateDynamicRegistryEntries(DynamicRegistryBuilder builder) {

    }

    @Override
    public void generate(DataGenerationContext ctx) {
        ctx.getDefaultPack().addProvider(DataGenerationContext::includeClient, ExamplemodEnglishLanguageProvider::new);
    }
}
