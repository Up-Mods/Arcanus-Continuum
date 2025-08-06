package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
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

	public static final RegistrySupplier<Item> POLYSIUM_INGOT = ITEMS.register("polysium_ingot", () -> new Item(new Item.Properties()));
	public static final RegistrySupplier<Item> POLYSIUM_NUGGET = ITEMS.register("polysium_nugget", () -> new Item(new Item.Properties()));

	public static final RegistrySupplier<Item> WIZARD_HAT = ITEMS.register("wizard_hat", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> WIZARD_ROBES = ITEMS.register("wizard_robes", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> WIZARD_PANTS = ITEMS.register("wizard_pants", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> WIZARD_BOOTS = ITEMS.register("wizard_boots", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> RED_CULT_HOOD = ITEMS.register("red_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, ArcanaType.IGNIS));
	public static final RegistrySupplier<Item> RED_CULT_ROBES = ITEMS.register("red_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, ArcanaType.IGNIS));
	public static final RegistrySupplier<Item> RED_CULT_PANTS = ITEMS.register("red_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, ArcanaType.IGNIS));
	public static final RegistrySupplier<Item> RED_CULT_BOOTS = ITEMS.register("red_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, ArcanaType.IGNIS));
	public static final RegistrySupplier<Item> GREEN_CULT_HOOD = ITEMS.register("green_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, ArcanaType.TERRA));
	public static final RegistrySupplier<Item> GREEN_CULT_ROBES = ITEMS.register("green_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, ArcanaType.TERRA));
	public static final RegistrySupplier<Item> GREEN_CULT_PANTS = ITEMS.register("green_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, ArcanaType.TERRA));
	public static final RegistrySupplier<Item> GREEN_CULT_BOOTS = ITEMS.register("green_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, ArcanaType.TERRA));
	public static final RegistrySupplier<Item> BLUE_CULT_HOOD = ITEMS.register("blue_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, ArcanaType.AQUA));
	public static final RegistrySupplier<Item> BLUE_CULT_ROBES = ITEMS.register("blue_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, ArcanaType.AQUA));
	public static final RegistrySupplier<Item> BLUE_CULT_PANTS = ITEMS.register("blue_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, ArcanaType.AQUA));
	public static final RegistrySupplier<Item> BLUE_CULT_BOOTS = ITEMS.register("blue_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, ArcanaType.AQUA));
	public static final RegistrySupplier<Item> WHITE_CULT_HOOD = ITEMS.register("white_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, ArcanaType.AER));
	public static final RegistrySupplier<Item> WHITE_CULT_ROBES = ITEMS.register("white_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, ArcanaType.AER));
	public static final RegistrySupplier<Item> WHITE_CULT_PANTS = ITEMS.register("white_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, ArcanaType.AER));
	public static final RegistrySupplier<Item> WHITE_CULT_BOOTS = ITEMS.register("white_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, ArcanaType.AER));
	public static final RegistrySupplier<Item> BLACK_CULT_HOOD = ITEMS.register("black_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, ArcanaType.AETHER));
	public static final RegistrySupplier<Item> BLACK_CULT_ROBES = ITEMS.register("black_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, ArcanaType.AETHER));
	public static final RegistrySupplier<Item> BLACK_CULT_PANTS = ITEMS.register("black_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, ArcanaType.AETHER));
	public static final RegistrySupplier<Item> BLACK_CULT_BOOTS = ITEMS.register("black_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, ArcanaType.AETHER));

	public static final RegistrySupplier<Item> BOOK_POUCH = ITEMS.register("book_pouch", BookPouchItem::new);
	public static final RegistrySupplier<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new);
	public static final RegistrySupplier<Item> SPELL_SCROLL = ITEMS.register("spell_scroll", SpellScrollItem::new);
	public static final RegistrySupplier<Item> SCROLL_OF_KNOWLEDGE = ITEMS.register("scroll_of_knowledge", ScrollOfKnowledgeItem::new);
	public static final RegistrySupplier<Item> WIZARD_SPAWN_EGG = ITEMS.register("wizard_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.WIZARD.get(), 0x52392a, 0xffd87c, new Item.Properties()));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_SPAWN_EGG = ITEMS.register("cultist_cleric_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.CULTIST_CLERIC.get(), 0x52392a, 0xffd87c, new Item.Properties())); // TODO colors
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_SPAWN_EGG = ITEMS.register("cultist_knight_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.CULTIST_KNIGHT.get(), 0x52392a, 0xffd87c, new Item.Properties())); // TODO colors
	public static final RegistrySupplier<Item> OPOSSUM_SPAWN_EGG = ITEMS.register("opossum_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.OPOSSUM.get(), 0x131317, 0xbdbdbd, new Item.Properties()));

	public static final RegistrySupplier<Item> IRON_STAFF_CAP = ITEMS.register("iron_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(-0.1d))));
	public static final RegistrySupplier<Item> GOLDEN_STAFF_CAP = ITEMS.register("golden_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0d))));
	public static final RegistrySupplier<Item> COPPER_STAFF_CAP = ITEMS.register("copper_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0.1d, true))));
	public static final RegistrySupplier<Item> NETHERITE_STAFF_CAP = ITEMS.register("netherite_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(-0.9d, true))));
	public static final RegistrySupplier<Item> POLYSIUM_STAFF_CAP = ITEMS.register("polysium_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0.25d, true))));

	public static final RegistrySupplier<Item> WOODEN_STAFF_CORE = ITEMS.register("wooden_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(1.1d, 1.1d, 1.1d, 1.1d, 1.1d))));
	public static final RegistrySupplier<Item> CRIMSON_STAFF_CORE = ITEMS.register("crimson_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(0.8d, 1d, 1d, 1d, 0.8d))));
	public static final RegistrySupplier<Item> WARPED_STAFF_CORE = ITEMS.register("warped_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(1d, 1d, 0.8d, 1d, 0.8d))));

	public static final RegistrySupplier<Item> STAFF = ITEMS.register("staff", StaffItem::new);

	public static final List<Supplier<Item>> HOOD_ITEMS = List.of(
		RED_CULT_HOOD, GREEN_CULT_HOOD, BLUE_CULT_HOOD, WHITE_CULT_HOOD, BLACK_CULT_HOOD
	);
}
