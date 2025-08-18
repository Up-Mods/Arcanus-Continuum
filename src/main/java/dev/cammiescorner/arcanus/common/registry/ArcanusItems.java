package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.data_component.StaffCapComponent;
import dev.cammiescorner.arcanus.common.data_component.StaffCoreComponent;
import dev.cammiescorner.arcanus.common.item.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.List;
import java.util.function.Supplier;

public class ArcanusItems {
	public static final RegistryHandler<Item> ITEMS = RegistryHandler.create(Registries.ITEM, Arcanus.MOD_ID);

	public static final RegistrySupplier<Item> ARCANEUM_INGOT = ITEMS.register("arcaneum_ingot", () -> new Item(new Item.Properties()));
	public static final RegistrySupplier<Item> ARCANEUM_NUGGET = ITEMS.register("arcaneum_nugget", () -> new Item(new Item.Properties()));

	public static final RegistrySupplier<Item> ARCANIST_HAT = ITEMS.register("arcanist_hat", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> ARCANIST_ROBES = ITEMS.register("arcanist_robes", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> ARCANIST_PANTS = ITEMS.register("arcanist_pants", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> ARCANIST_BOOTS = ITEMS.register("arcanist_boots", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> ARTIFICER_HELMET = ITEMS.register("artificer_helmet", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> ARTIFICER_CHESTPLATE = ITEMS.register("artificer_chestplate", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> ARTIFICER_LEGGINGS = ITEMS.register("artificer_leggings", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> ARTIFICER_BOOTS = ITEMS.register("artificer_boots", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> CULTIST_HOOD = ITEMS.register("cultist_hood", () -> new CultRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.HELMET, ArcanusArcana.IGNIS));
	public static final RegistrySupplier<Item> CULTIST_ROBES = ITEMS.register("cultist_robes", () -> new CultRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.CHESTPLATE, ArcanusArcana.AER));
	public static final RegistrySupplier<Item> CULTIST_PANTS = ITEMS.register("cultist_pants", () -> new CultRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.LEGGINGS, ArcanusArcana.AQUA));
	public static final RegistrySupplier<Item> CULTIST_BOOTS = ITEMS.register("cultist_boots", () -> new CultRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.BOOTS, ArcanusArcana.TERRA));

	public static final RegistrySupplier<Item> BOOK_POUCH = ITEMS.register("book_pouch", BookPouchItem::new);
	public static final RegistrySupplier<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new);
	public static final RegistrySupplier<Item> SPELL_SCROLL = ITEMS.register("spell_scroll", SpellScrollItem::new);
	public static final RegistrySupplier<Item> SCROLL_OF_KNOWLEDGE = ITEMS.register("scroll_of_knowledge", ScrollOfKnowledgeItem::new);
	public static final RegistrySupplier<Item> ARCANIST_SPAWN_EGG = ITEMS.register("arcanist_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.ARCANIST.get(), 0x52392a, 0xffd87c, new Item.Properties()));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_SPAWN_EGG = ITEMS.register("cultist_cleric_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.CULTIST_CLERIC.get(), 0x612e2e, 0x5f2b63, new Item.Properties()));
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_SPAWN_EGG = ITEMS.register("cultist_knight_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.CULTIST_KNIGHT.get(), 0x612e2e, 0xbdbdbd, new Item.Properties())); // TODO colors
	public static final RegistrySupplier<Item> OPOSSUM_SPAWN_EGG = ITEMS.register("opossum_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.OPOSSUM.get(), 0x131317, 0xbdbdbd, new Item.Properties()));

	public static final RegistrySupplier<Item> IRON_STAFF_CAP = ITEMS.register("iron_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(-0.1d))));
	public static final RegistrySupplier<Item> GOLDEN_STAFF_CAP = ITEMS.register("golden_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0d))));
	public static final RegistrySupplier<Item> COPPER_STAFF_CAP = ITEMS.register("copper_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0.1d, true))));
	public static final RegistrySupplier<Item> NETHERITE_STAFF_CAP = ITEMS.register("netherite_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(-0.9d, true))));
	public static final RegistrySupplier<Item> ARCANEUM_STAFF_CAP = ITEMS.register("arcaneum_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0.25d, true))));

	public static final RegistrySupplier<Item> WOODEN_STAFF_CORE = ITEMS.register("wooden_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(1.1d, 1.1d, 1.1d, 1.1d, 1.1d))));
	public static final RegistrySupplier<Item> CRIMSON_STAFF_CORE = ITEMS.register("crimson_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(0.8d, 1d, 1d, 1d, 0.8d))));
	public static final RegistrySupplier<Item> WARPED_STAFF_CORE = ITEMS.register("warped_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(1d, 1d, 0.8d, 1d, 0.8d))));

	public static final RegistrySupplier<Item> STAFF = ITEMS.register("staff", StaffItem::new);

	public static final List<Supplier<Item>> HOOD_ITEMS = List.of(
		CULTIST_HOOD
	);
}
