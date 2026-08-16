package dev.upcraft.examplemod;

import com.google.auto.service.AutoService;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import dev.upcraft.examplemod.config.ExampleModConfig;
import dev.upcraft.examplemod.init.ExampleBlocks;
import dev.upcraft.examplemod.init.ExampleItems;
import dev.upcraft.sparkweave.api.entrypoint.MainEntryPoint;
import dev.upcraft.sparkweave.api.platform.ModContainer;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import net.minecraft.resources.Identifier;

@AutoService(MainEntryPoint.class)
public class ExampleMod implements MainEntryPoint {
    public static final String MOD_ID = "examplemod";

    private static final Configurator CONFIGURATOR = new Configurator(MOD_ID);

    @Override
    public void onInitialize(ModContainer mod) {
        CONFIGURATOR.register(ExampleModConfig.class);

        var registryService = RegistryService.get();
        ExampleBlocks.BLOCKS.accept(registryService);
        ExampleItems.ITEMS.accept(registryService);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
