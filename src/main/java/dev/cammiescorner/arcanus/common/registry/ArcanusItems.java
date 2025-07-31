package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.mana.ManaType;
import dev.cammiescorner.arcanus.common.item.*;
import dev.cammiescorner.arcanus.common.util.StaffType;
import dev.upcraft.sparkweave.api.color.Color;
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

	public static final RegistrySupplier<Item> WOODEN_STAFF = ITEMS.register("wooden_staff", () -> new StaffItem(StaffType.STAFF, Color.fromARGB(0xffffffff), Color.fromARGB(0xff51301a)));
	public static final RegistrySupplier<Item> CRYSTAL_STAFF = ITEMS.register("crystal_staff", () -> new StaffItem(StaffType.STAFF, Color.fromARGB(0xffffffff), Color.fromARGB(0xff51301a)));
	public static final RegistrySupplier<Item> DIVINATION_STAFF = ITEMS.register("divination_staff", () -> new StaffItem(StaffType.STAFF, Color.fromARGB(0xffffffff), Color.fromARGB(0xff51301a)));
	public static final RegistrySupplier<Item> CRESCENT_STAFF = ITEMS.register("crescent_staff", () -> new StaffItem(StaffType.STAFF, Color.fromARGB(0xffffffff), Color.fromARGB(0xff51301a)));
	public static final RegistrySupplier<Item> ANCIENT_STAFF = ITEMS.register("ancient_staff", () -> new StaffItem(StaffType.STAFF, Color.fromARGB(0xffffffff), Color.fromARGB(0xff51301a)));
	public static final RegistrySupplier<Item> WAND = ITEMS.register("wand", () -> new StaffItem(StaffType.WAND, Color.fromARGB(0xffffffff), Color.fromARGB(0xff51301a), true));
	public static final RegistrySupplier<Item> THAUMATURGES_GAUNTLET = ITEMS.register("thaumaturges_gauntlet", () -> new StaffItem(StaffType.GAUNTLET, Color.fromARGB(0xffffffff), Color.fromARGB(0xff808080), true));
	public static final RegistrySupplier<Item> MIND_STAFF = ITEMS.register("mind_staff", () -> new StaffItem(StaffType.STAFF, Color.fromARGB(0xffffffff), Color.fromARGB(0xffffffff), true));
	public static final RegistrySupplier<Item> MAGIC_TOME = ITEMS.register("magic_tome", () -> new StaffItem(StaffType.BOOK, Color.fromARGB(0xff8b4513), Color.fromARGB(0xff1e1b1b), true));
	public static final RegistrySupplier<Item> MAGE_PISTOL = ITEMS.register("mage_pistol", () -> new StaffItem(StaffType.GUN, Color.fromARGB(0xffffffff), Color.fromARGB(0xffffffff), true));
	public static final RegistrySupplier<Item> STAFF_CAP = ITEMS.register("staff_cap", StaffCapItem::new);
	public static final RegistrySupplier<Item> STAFF_CORE = ITEMS.register("staff_core", StaffCoreItem::new);

	public static final RegistrySupplier<Item> WIZARD_HAT = ITEMS.register("wizard_hat", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> WIZARD_ROBES = ITEMS.register("wizard_robes", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> WIZARD_PANTS = ITEMS.register("wizard_pants", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> WIZARD_BOOTS = ITEMS.register("wizard_boots", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> RED_CULT_HOOD = ITEMS.register("red_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, ManaType.RED));
	public static final RegistrySupplier<Item> RED_CULT_ROBES = ITEMS.register("red_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, ManaType.RED));
	public static final RegistrySupplier<Item> RED_CULT_PANTS = ITEMS.register("red_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, ManaType.RED));
	public static final RegistrySupplier<Item> RED_CULT_BOOTS = ITEMS.register("red_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, ManaType.RED));
	public static final RegistrySupplier<Item> GREEN_CULT_HOOD = ITEMS.register("green_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, ManaType.GREEN));
	public static final RegistrySupplier<Item> GREEN_CULT_ROBES = ITEMS.register("green_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, ManaType.GREEN));
	public static final RegistrySupplier<Item> GREEN_CULT_PANTS = ITEMS.register("green_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, ManaType.GREEN));
	public static final RegistrySupplier<Item> GREEN_CULT_BOOTS = ITEMS.register("green_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, ManaType.GREEN));
	public static final RegistrySupplier<Item> BLUE_CULT_HOOD = ITEMS.register("blue_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, ManaType.BLUE));
	public static final RegistrySupplier<Item> BLUE_CULT_ROBES = ITEMS.register("blue_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, ManaType.BLUE));
	public static final RegistrySupplier<Item> BLUE_CULT_PANTS = ITEMS.register("blue_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, ManaType.BLUE));
	public static final RegistrySupplier<Item> BLUE_CULT_BOOTS = ITEMS.register("blue_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, ManaType.BLUE));
	public static final RegistrySupplier<Item> WHITE_CULT_HOOD = ITEMS.register("white_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, ManaType.WHITE));
	public static final RegistrySupplier<Item> WHITE_CULT_ROBES = ITEMS.register("white_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, ManaType.WHITE));
	public static final RegistrySupplier<Item> WHITE_CULT_PANTS = ITEMS.register("white_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, ManaType.WHITE));
	public static final RegistrySupplier<Item> WHITE_CULT_BOOTS = ITEMS.register("white_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, ManaType.WHITE));
	public static final RegistrySupplier<Item> BLACK_CULT_HOOD = ITEMS.register("black_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, ManaType.BLACK));
	public static final RegistrySupplier<Item> BLACK_CULT_ROBES = ITEMS.register("black_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, ManaType.BLACK));
	public static final RegistrySupplier<Item> BLACK_CULT_PANTS = ITEMS.register("black_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, ManaType.BLACK));
	public static final RegistrySupplier<Item> BLACK_CULT_BOOTS = ITEMS.register("black_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, ManaType.BLACK));

	public static final RegistrySupplier<Item> BOOK_POUCH = ITEMS.register("book_pouch", BookPouchItem::new);
	public static final RegistrySupplier<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new);
	public static final RegistrySupplier<Item> SPELL_SCROLL = ITEMS.register("spell_scroll", SpellScrollItem::new);
	public static final RegistrySupplier<Item> SCROLL_OF_KNOWLEDGE = ITEMS.register("scroll_of_knowledge", ScrollOfKnowledgeItem::new);
	public static final RegistrySupplier<Item> WIZARD_SPAWN_EGG = ITEMS.register("wizard_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.WIZARD.get(), 0x52392a, 0xffd87c, new Item.Properties()));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_SPAWN_EGG = ITEMS.register("cultist_cleric_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.CULTIST_CLERIC.get(), 0x52392a, 0xffd87c, new Item.Properties())); // TODO colors
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_SPAWN_EGG = ITEMS.register("cultist_knight_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.CULTIST_KNIGHT.get(), 0x52392a, 0xffd87c, new Item.Properties())); // TODO colors
	public static final RegistrySupplier<Item> OPOSSUM_SPAWN_EGG = ITEMS.register("opossum_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.OPOSSUM.get(), 0x131317, 0xbdbdbd, new Item.Properties()));

	public static final List<Supplier<Item>> HOOD_ITEMS = List.of(
		RED_CULT_HOOD, GREEN_CULT_HOOD, BLUE_CULT_HOOD, WHITE_CULT_HOOD, BLACK_CULT_HOOD
	);
}
