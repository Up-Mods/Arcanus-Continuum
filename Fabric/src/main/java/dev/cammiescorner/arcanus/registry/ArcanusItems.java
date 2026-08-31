package dev.cammiescorner.arcanus.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.data.ArcanusArmorMaterials;
import dev.cammiescorner.arcanus.data_component.StaffCapComponent;
import dev.cammiescorner.arcanus.data_component.StaffCoreComponent;
import dev.cammiescorner.arcanus.common.item.*;
import dev.cammiescorner.arcanus.item.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import dev.upcraft.sparkweave.api.registry.item.ItemRegistryHandler;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.List;
import java.util.function.Supplier;

public class ArcanusItems {
	public static final ItemRegistryHandler ITEMS = RegistryHandler.items(Arcanus.MOD_ID);

	public static final RegistrySupplier<Item> EBONY_LOG = ITEMS.registerForBlock(ArcanusBlocks.EBONY_LOG, BlockItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> EBONY_WOOD = ITEMS.registerForBlock(ArcanusBlocks.EBONY_WOOD, BlockItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> EBONY_LEAVES = ITEMS.registerForBlock(ArcanusBlocks.EBONY_LEAVES, BlockItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> EBONY_SAPLING = ITEMS.registerForBlock(ArcanusBlocks.EBONY_SAPLING, BlockItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> STRIPPED_EBONY_LOG = ITEMS.registerForBlock(ArcanusBlocks.STRIPPED_EBONY_LOG, BlockItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> STRIPPED_EBONY_WOOD = ITEMS.registerForBlock(ArcanusBlocks.STRIPPED_EBONY_WOOD, BlockItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> EBONY_PLANKS = ITEMS.registerForBlock(ArcanusBlocks.EBONY_PLANKS, BlockItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> EBONY_STAIRS = ITEMS.registerForBlock(ArcanusBlocks.EBONY_STAIRS, BlockItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> EBONY_SLAB = ITEMS.registerForBlock(ArcanusBlocks.EBONY_SLAB, BlockItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> ARCANEUM_INGOT = ITEMS.register("arcaneum_ingot", Item::new, Item.Properties::new);
	public static final RegistrySupplier<Item> ARCANEUM_NUGGET = ITEMS.register("arcaneum_nugget", Item::new, Item.Properties::new);
	public static final RegistrySupplier<Item> VOID_INGOT = ITEMS.register("void_ingot",Item::new, Item.Properties::new);
	public static final RegistrySupplier<Item> VOID_NUGGET = ITEMS.register("void_nugget", Item::new, Item.Properties::new);

	public static final RegistrySupplier<Item> ARCANIST_HAT = ITEMS.register("arcanist_hat", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ARCANIST, ArmorType.HELMET));
	public static final RegistrySupplier<Item> ARCANIST_ROBES = ITEMS.register("arcanist_robes", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ARCANIST, ArmorType.CHESTPLATE));
	public static final RegistrySupplier<Item> ARCANIST_PANTS = ITEMS.register("arcanist_pants", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ARCANIST, ArmorType.LEGGINGS));
	public static final RegistrySupplier<Item> ARCANIST_BOOTS = ITEMS.register("arcanist_boots", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ARCANIST, ArmorType.BOOTS));
	public static final RegistrySupplier<Item> ARTIFICER_HELMET = ITEMS.register("artificer_helmet", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ARTIFICER, ArmorType.HELMET));
	public static final RegistrySupplier<Item> ARTIFICER_CHESTPLATE = ITEMS.register("artificer_chestplate", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ARTIFICER, ArmorType.CHESTPLATE));
	public static final RegistrySupplier<Item> ARTIFICER_LEGGINGS = ITEMS.register("artificer_leggings", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ARTIFICER, ArmorType.LEGGINGS));
	public static final RegistrySupplier<Item> ARTIFICER_BOOTS = ITEMS.register("artificer_boots", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ARTIFICER, ArmorType.BOOTS));
	public static final RegistrySupplier<Item> ALCHEMIST_GOGGLES = ITEMS.register("alchemist_goggles", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ALCHEMIST, ArmorType.HELMET));
	public static final RegistrySupplier<Item> ALCHEMIST_ROBES = ITEMS.register("alchemist_robes", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ALCHEMIST, ArmorType.CHESTPLATE));
	public static final RegistrySupplier<Item> ALCHEMIST_PANTS = ITEMS.register("alchemist_pants", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ALCHEMIST, ArmorType.LEGGINGS));
	public static final RegistrySupplier<Item> ALCHEMIST_BOOTS = ITEMS.register("alchemist_boots", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.ALCHEMIST, ArmorType.BOOTS));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_HOOD = ITEMS.register("cultist_cleric_hood", properties -> new CultRobesItem(properties, ArcanusArcana.IGNIS), new Item.Properties().humanoidArmor(ArcanusArmorMaterials.CULTIST_CLERIC, ArmorType.HELMET).component(ArcanusDataComponents.HOOD_DOWN.get(), false));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_ROBES = ITEMS.register("cultist_cleric_robes", properties -> new CultRobesItem(properties, ArcanusArcana.AER), new Item.Properties().humanoidArmor(ArcanusArmorMaterials.CULTIST_CLERIC, ArmorType.CHESTPLATE));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_PANTS = ITEMS.register("cultist_cleric_pants", properties -> new CultRobesItem(properties, ArcanusArcana.AQUA), new Item.Properties().humanoidArmor(ArcanusArmorMaterials.CULTIST_CLERIC, ArmorType.LEGGINGS));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_BOOTS = ITEMS.register("cultist_cleric_boots", properties -> new CultRobesItem(properties, ArcanusArcana.TERRA), new Item.Properties().humanoidArmor(ArcanusArmorMaterials.CULTIST_CLERIC, ArmorType.BOOTS));
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_HELMET = ITEMS.register("cultist_knight_helmet", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.CULTIST_KNIGHT, ArmorType.HELMET));
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_CHESTPLATE = ITEMS.register("cultist_knight_chestplate", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.CULTIST_KNIGHT, ArmorType.CHESTPLATE));
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_LEGGINGS = ITEMS.register("cultist_knight_leggings", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.CULTIST_KNIGHT, ArmorType.LEGGINGS));
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_BOOTS = ITEMS.register("cultist_knight_boots", ArcanistRobesItem::new, new Item.Properties().humanoidArmor(ArcanusArmorMaterials.CULTIST_KNIGHT, ArmorType.BOOTS));

	public static final RegistrySupplier<Item> BOOK_POUCH = ITEMS.register("book_pouch", BookPouchItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> SPELL_SCROLL = ITEMS.register("spell_scroll", SpellScrollItem::new, Item.Properties::new);
	public static final RegistrySupplier<Item> SCROLL_OF_KNOWLEDGE = ITEMS.register("scroll_of_knowledge", ScrollOfKnowledgeItem::new);
	public static final RegistrySupplier<Item> ARCANIST_SPAWN_EGG = ITEMS.register("arcanist_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(ArcanusEntities.ARCANIST.get())); // 0x52392a, 0xffd87c
	public static final RegistrySupplier<Item> CULTIST_CLERIC_SPAWN_EGG = ITEMS.register("cultist_cleric_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(ArcanusEntities.CULTIST_CLERIC.get())); // 0x612e2e, 0x5f2b63
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_SPAWN_EGG = ITEMS.register("cultist_knight_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(ArcanusEntities.CULTIST_KNIGHT.get())); // 0x612e2e, 0xffd87c
	public static final RegistrySupplier<Item> OPOSSUM_SPAWN_EGG = ITEMS.register("opossum_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(ArcanusEntities.OPOSSUM.get()));

	public static final RegistrySupplier<Item> IRON_STAFF_CAP = ITEMS.register("iron_staff_cap", StaffCapItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(-0.1d)));
	public static final RegistrySupplier<Item> GOLDEN_STAFF_CAP = ITEMS.register("golden_staff_cap", StaffCapItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0d)));
	public static final RegistrySupplier<Item> COPPER_STAFF_CAP = ITEMS.register("copper_staff_cap", StaffCapItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0.1d, true)));
	public static final RegistrySupplier<Item> NETHERITE_STAFF_CAP = ITEMS.register("netherite_staff_cap", StaffCapItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(-0.9d, true)));
	public static final RegistrySupplier<Item> ARCANEUM_STAFF_CAP = ITEMS.register("arcaneum_staff_cap", StaffCapItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0.2d, true)));
	public static final RegistrySupplier<Item> VOID_STAFF_CAP = ITEMS.register("void_staff_cap", StaffCapItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0.3d, true)));

	public static final RegistrySupplier<Item> WOODEN_STAFF_CORE = ITEMS.register("wooden_staff_core", StaffCoreItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(1.1d, 1.1d, 1.1d, 1.1d, 1.1d)));
	public static final RegistrySupplier<Item> EBONY_STAFF_CORE = ITEMS.register("ebony_staff_core", StaffCoreItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(0.9d, 0.9d, 0.9d, 0.9d, 0.9d)));
	public static final RegistrySupplier<Item> TBD_STAFF_CORE = ITEMS.register("tbd_staff_core", StaffCoreItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(0.7d, 0.7d, 0.7d, 0.7d, 0.7d)));
	public static final RegistrySupplier<Item> BLAZING_STAFF_CORE = ITEMS.register("blazing_staff_core", StaffCoreItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(0.5d, 0.9d, 0.9d, 0.9d, 0.9d)));
	public static final RegistrySupplier<Item> BAMBOO_STAFF_CORE = ITEMS.register("bamboo_staff_core", StaffCoreItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(0.9d, 0.5d, 0.9d, 0.9d, 0.9d)));
	public static final RegistrySupplier<Item> PRISMARINE_STAFF_CORE = ITEMS.register("prismarine_staff_core", StaffCoreItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(0.9d, 0.9d, 0.5d, 0.9d, 0.9d)));
	public static final RegistrySupplier<Item> BONE_STAFF_CORE = ITEMS.register("bone_staff_core", StaffCoreItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(0.9d, 0.9d, 0.9d, 0.5d, 0.9d)));
	public static final RegistrySupplier<Item> HELLISH_STAFF_CORE = ITEMS.register("hellish_staff_core", StaffCoreItem::new, () -> new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(0.9d, 0.9d, 0.9d, 0.9d, 0.5d)));

	public static final RegistrySupplier<Item> STAFF = ITEMS.register("staff", StaffItem::new, Item.Properties::new);

	// TODO get rid of this
	public static final List<Supplier<Item>> HOOD_ITEMS = List.of(
		CULTIST_CLERIC_HOOD
	);
}
