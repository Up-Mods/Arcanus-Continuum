package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.screens.ArcaneWorkbenchScreenHandler;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ArcanusScreenHandlers {

	public static final RegistryHandler<MenuType<?>> SCREEN_HANDLERS = RegistryHandler.create(Registries.MENU, Arcanus.MOD_ID);

	// TODO uhhh figure out how to register these now???
//	public static final RegistrySupplier<MenuType<SpellcraftScreenHandler>> SPELLCRAFT_SCREEN_HANDLER = SCREEN_HANDLERS.register("spellcraft_screen_handler", () -> new ExtendedScreenHandlerType<>((syncId, inventory, data) -> new SpellcraftScreenHandler(syncId, inventory, BlockPos.ZERO, ItemStack.EMPTY), null));
//	public static final RegistrySupplier<MenuType<SpellBookScreenHandler>> SPELL_BOOK_SCREEN_HANDLER = SCREEN_HANDLERS.register("spell_book_screen_handler", () -> new ExtendedScreenHandlerType<>((syncId, inventory, data) -> new SpellBookScreenHandler(syncId, inventory, ItemStack.EMPTY), null));
	public static final RegistrySupplier<MenuType<ArcaneWorkbenchScreenHandler>> ARCANE_WORKBENCH_SCREEN_HANDLER = SCREEN_HANDLERS.register("arcane_workbench_screen_handler", () -> new MenuType<>(ArcaneWorkbenchScreenHandler::new, FeatureFlags.VANILLA_SET));
}
