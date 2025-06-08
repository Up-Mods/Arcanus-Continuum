package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.screens.ArcaneWorkbenchMenu;
import dev.cammiescorner.arcanus.common.screens.SpellBookMenu;
import dev.cammiescorner.arcanus.common.screens.SpellcraftMenu;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class ArcanusMenus {
	public static final RegistryHandler<MenuType<?>> MENUS = RegistryHandler.create(Registries.MENU, Arcanus.MOD_ID);

	public static final RegistrySupplier<MenuType<SpellcraftMenu>> SPELLCRAFT_MENU = MENUS.register("spellcraft_menu", () -> new MenuType<>(SpellcraftMenu::new, FeatureFlags.VANILLA_SET));
	public static final RegistrySupplier<MenuType<SpellBookMenu>> SPELL_BOOK_MENU = MENUS.register("spell_book_menu", () -> new MenuType<>((i, inventory) -> new SpellBookMenu(i, ItemStack.EMPTY), FeatureFlags.VANILLA_SET));
	public static final RegistrySupplier<MenuType<ArcaneWorkbenchMenu>> ARCANE_WORKBENCH_MENU = MENUS.register("arcane_workbench_menu", () -> new MenuType<>(ArcaneWorkbenchMenu::new, FeatureFlags.VANILLA_SET));
}
