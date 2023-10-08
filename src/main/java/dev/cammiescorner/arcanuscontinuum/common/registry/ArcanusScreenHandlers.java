package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.screens.ArcaneWorkbenchScreenHandler;
import dev.cammiescorner.arcanuscontinuum.common.screens.SpellBookScreenHandler;
import dev.cammiescorner.arcanuscontinuum.common.screens.SpellcraftScreenHandler;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.feature_flags.FeatureFlags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;

public class ArcanusScreenHandlers {

	public static final RegistryHandler<ScreenHandlerType<?>> SCREEN_HANDLERS = RegistryHandler.create(RegistryKeys.SCREEN_HANDLER_TYPE, Arcanus.MOD_ID);

	public static final RegistrySupplier<ScreenHandlerType<SpellcraftScreenHandler>> SPELLCRAFT_SCREEN_HANDLER = SCREEN_HANDLERS.register("spellcraft_screen_handler", () -> new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> new SpellcraftScreenHandler(syncId, inventory, buf.readBlockPos(), buf.readItemStack())));
	public static final RegistrySupplier<ScreenHandlerType<SpellBookScreenHandler>> SPELL_BOOK_SCREEN_HANDLER = SCREEN_HANDLERS.register("spell_book_screen_handler", () -> new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> new SpellBookScreenHandler(syncId, inventory, buf.readItemStack())));
	public static final RegistrySupplier<ScreenHandlerType<ArcaneWorkbenchScreenHandler>> ARCANE_WORKBENCH_SCREEN_HANDLER = SCREEN_HANDLERS.register("arcane_workbench_screen_handler", () -> new ScreenHandlerType<>(ArcaneWorkbenchScreenHandler::new, FeatureFlags.DEFAULT_SET));
}
