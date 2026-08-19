package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.menu.*;
import dev.cammiescorner.arcanus.common.menu.providers.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ArcanusMenus {
	public static final RegistryHandler<MenuType<?>> MENUS = RegistryHandler.create(Registries.MENU, Arcanus.MOD_ID);

	public static final RegistrySupplier<MenuType<SpellcraftMenu>> SPELLCRAFT_MENU = MENUS.register("spellcraft_menu", () -> new ExtendedMenuType<>((syncId, inventory, data) -> new SpellcraftMenu(syncId, inventory), SpellcraftMenuProvider.MenuData.STREAM_CODEC));
	public static final RegistrySupplier<MenuType<ScrollOfKnowledgeMenu>> SCROLL_OF_KNOWLEDGE_MENU = MENUS.register("scroll_of_knowledge_menu", () -> new ExtendedMenuType<>((syncId, inventory, menuData) -> new ScrollOfKnowledgeMenu(syncId, menuData.stack()), ScrollOfKnowledgeMenuProvider.MenuData.STREAM_CODEC));
	public static final RegistrySupplier<MenuType<SpellScrollMenu>> SPELL_SCROLL_MENU = MENUS.register("spell_scroll_menu", () -> new ExtendedMenuType<>((syncId, inventory, menuData) -> new SpellScrollMenu(syncId, menuData.stack()), SpellScrollMenuProvider.MenuData.STREAM_CODEC));
	public static final RegistrySupplier<MenuType<SpellBookMenu>> SPELL_BOOK_MENU = MENUS.register("spell_book_menu", () -> new ExtendedMenuType<>((syncId, inventory, menuData) -> new SpellBookMenu(syncId, inventory, menuData.book()), SpellBookMenuProvider.MenuData.STREAM_CODEC));
	public static final RegistrySupplier<MenuType<BookPouchMenu>> BOOK_POUCH_MENU = MENUS.register("book_pouch_menu", () -> new ExtendedMenuType<>((syncId, inventory, menuData) -> new BookPouchMenu(syncId, inventory, menuData.pouch()), BookPouchMenuProvider.MenuData.STREAM_CODEC));
	public static final RegistrySupplier<MenuType<ArcaneWorkbenchMenu>> ARCANE_WORKBENCH_MENU = MENUS.register("arcane_workbench_menu", () -> new MenuType<>(ArcaneWorkbenchMenu::new, FeatureFlags.VANILLA_SET));
}
