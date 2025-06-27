package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.menus.ArcaneWorkbenchMenu;
import dev.cammiescorner.arcanus.common.menus.SpellBookMenu;
import dev.cammiescorner.arcanus.common.menus.SpellScrollMenu;
import dev.cammiescorner.arcanus.common.menus.SpellcraftMenu;
import dev.cammiescorner.arcanus.common.menus.providers.SpellBookMenuProvider;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class ArcanusMenus {
	public static final RegistryHandler<MenuType<?>> MENUS = RegistryHandler.create(Registries.MENU, Arcanus.MOD_ID);

	public static final RegistrySupplier<MenuType<SpellcraftMenu>> SPELLCRAFT_MENU = MENUS.register("spellcraft_menu", () -> new MenuType<>(SpellcraftMenu::new, FeatureFlags.VANILLA_SET));
	public static final RegistrySupplier<MenuType<SpellScrollMenu>> SPELL_SCROLL_MENU = MENUS.register("spell_scroll_menu", () -> new MenuType<>((i, inventory) -> new SpellScrollMenu(i, ArcanusItems.SPELL_SCROLL.get().getDefaultInstance()), FeatureFlags.VANILLA_SET));
	public static final RegistrySupplier<MenuType<SpellBookMenu>> SPELL_BOOK_MENU = MENUS.register("spell_book_menu", () -> new ExtendedScreenHandlerType<>((syncId, inventory, menuData) -> new SpellBookMenu(syncId, inventory, menuData.stack()), SpellBookMenuProvider.MenuData.STREAM_CODEC));
	public static final RegistrySupplier<MenuType<ArcaneWorkbenchMenu>> ARCANE_WORKBENCH_MENU = MENUS.register("arcane_workbench_menu", () -> new MenuType<>(ArcaneWorkbenchMenu::new, FeatureFlags.VANILLA_SET));
}
