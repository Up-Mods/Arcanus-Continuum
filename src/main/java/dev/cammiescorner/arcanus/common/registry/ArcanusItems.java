package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.data_component.StaffCapComponent;
import dev.cammiescorner.arcanus.common.data_component.StaffCoreComponent;
import dev.cammiescorner.arcanus.common.item.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.List;
import java.util.function.Supplier;

public class ArcanusItems {
	public static final RegistryHandler<Item> ITEMS = RegistryHandler.create(Registries.ITEM, Arcanus.MOD_ID);

	public static final RegistrySupplier<Item> EMYRWOOD_LOG = ITEMS.register("emyrwood_log", () -> new BlockItem(ArcanusBlocks.EMYRWOOD_LOG.get(), new Item.Properties()));
	public static final RegistrySupplier<Item> EMYRWOOD_WOOD = ITEMS.register("emyrwood_wood", () -> new BlockItem(ArcanusBlocks.EMYRWOOD_WOOD.get(), new Item.Properties()));
	public static final RegistrySupplier<Item> STRIPPED_EMYRWOOD_LOG = ITEMS.register("stripped_emyrwood_log", () -> new BlockItem(ArcanusBlocks.STRIPPED_EMYRWOOD_LOG.get(), new Item.Properties()));
	public static final RegistrySupplier<Item> STRIPPED_EMYRWOOD_WOOD = ITEMS.register("stripped_emyrwood_wood", () -> new BlockItem(ArcanusBlocks.STRIPPED_EMYRWOOD_WOOD.get(), new Item.Properties()));
	public static final RegistrySupplier<Item> EMYRWOOD_PLANKS = ITEMS.register("emyrwood_planks", () -> new BlockItem(ArcanusBlocks.EMYRWOOD_PLANKS.get(), new Item.Properties()));
	public static final RegistrySupplier<Item> EMYRWOOD_STAIRS = ITEMS.register("emyrwood_stairs", () -> new BlockItem(ArcanusBlocks.EMYRWOOD_STAIRS.get(), new Item.Properties()));
	public static final RegistrySupplier<Item> EMYRWOOD_SLAB = ITEMS.register("emyrwood_slab", () -> new BlockItem(ArcanusBlocks.EMYRWOOD_SLAB.get(), new Item.Properties()));
	public static final RegistrySupplier<Item> ARCANEUM_INGOT = ITEMS.register("arcaneum_ingot", () -> new Item(new Item.Properties()));
	public static final RegistrySupplier<Item> ARCANEUM_NUGGET = ITEMS.register("arcaneum_nugget", () -> new Item(new Item.Properties()));
	public static final RegistrySupplier<Item> VOID_INGOT = ITEMS.register("void_ingot", () -> new Item(new Item.Properties()));
	public static final RegistrySupplier<Item> VOID_NUGGET = ITEMS.register("void_nugget", () -> new Item(new Item.Properties()));

	public static final RegistrySupplier<Item> ARCANIST_HAT = ITEMS.register("arcanist_hat", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> ARCANIST_ROBES = ITEMS.register("arcanist_robes", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> ARCANIST_PANTS = ITEMS.register("arcanist_pants", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> ARCANIST_BOOTS = ITEMS.register("arcanist_boots", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARCANIST.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> ARTIFICER_HELMET = ITEMS.register("artificer_helmet", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARTIFICER.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> ARTIFICER_CHESTPLATE = ITEMS.register("artificer_chestplate", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARTIFICER.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> ARTIFICER_LEGGINGS = ITEMS.register("artificer_leggings", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARTIFICER.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> ARTIFICER_BOOTS = ITEMS.register("artificer_boots", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ARTIFICER.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> ALCHEMIST_GOGGLES = ITEMS.register("alchemist_goggles", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ALCHEMIST.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> ALCHEMIST_ROBES = ITEMS.register("alchemist_robes", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ALCHEMIST.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> ALCHEMIST_PANTS = ITEMS.register("alchemist_pants", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ALCHEMIST.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> ALCHEMIST_BOOTS = ITEMS.register("alchemist_boots", () -> new ArcanistRobesItem(ArcanusArmorMaterials.ALCHEMIST.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_HOOD = ITEMS.register("cultist_cleric_hood", () -> new CultRobesItem(ArcanusArmorMaterials.CULTIST_CLERIC.holder(), ArmorItem.Type.HELMET, ArcanusArcana.IGNIS));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_ROBES = ITEMS.register("cultist_cleric_robes", () -> new CultRobesItem(ArcanusArmorMaterials.CULTIST_CLERIC.holder(), ArmorItem.Type.CHESTPLATE, ArcanusArcana.AER));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_PANTS = ITEMS.register("cultist_cleric_pants", () -> new CultRobesItem(ArcanusArmorMaterials.CULTIST_CLERIC.holder(), ArmorItem.Type.LEGGINGS, ArcanusArcana.AQUA));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_BOOTS = ITEMS.register("cultist_cleric_boots", () -> new CultRobesItem(ArcanusArmorMaterials.CULTIST_CLERIC.holder(), ArmorItem.Type.BOOTS, ArcanusArcana.TERRA));
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_HELMET = ITEMS.register("cultist_knight_helmet", () -> new ArcanistRobesItem(ArcanusArmorMaterials.CULTIST_KNIGHT.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_CHESTPLATE = ITEMS.register("cultist_knight_chestplate", () -> new ArcanistRobesItem(ArcanusArmorMaterials.CULTIST_KNIGHT.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_LEGGINGS = ITEMS.register("cultist_knight_leggings", () -> new ArcanistRobesItem(ArcanusArmorMaterials.CULTIST_KNIGHT.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_BOOTS = ITEMS.register("cultist_knight_boots", () -> new ArcanistRobesItem(ArcanusArmorMaterials.CULTIST_KNIGHT.holder(), ArmorItem.Type.BOOTS));

	public static final RegistrySupplier<Item> BOOK_POUCH = ITEMS.register("book_pouch", BookPouchItem::new);
	public static final RegistrySupplier<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new);
	public static final RegistrySupplier<Item> SPELL_SCROLL = ITEMS.register("spell_scroll", SpellScrollItem::new);
	public static final RegistrySupplier<Item> SCROLL_OF_KNOWLEDGE = ITEMS.register("scroll_of_knowledge", ScrollOfKnowledgeItem::new);
	public static final RegistrySupplier<Item> ARCANIST_SPAWN_EGG = ITEMS.register("arcanist_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.ARCANIST.get(), 0x52392a, 0xffd87c, new Item.Properties()));
	public static final RegistrySupplier<Item> CULTIST_CLERIC_SPAWN_EGG = ITEMS.register("cultist_cleric_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.CULTIST_CLERIC.get(), 0x612e2e, 0x5f2b63, new Item.Properties()));
	public static final RegistrySupplier<Item> CULTIST_KNIGHT_SPAWN_EGG = ITEMS.register("cultist_knight_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.CULTIST_KNIGHT.get(), 0x612e2e, 0xffd87c, new Item.Properties()));
	public static final RegistrySupplier<Item> OPOSSUM_SPAWN_EGG = ITEMS.register("opossum_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.OPOSSUM.get(), 0x131317, 0xbdbdbd, new Item.Properties()));

	public static final RegistrySupplier<Item> IRON_STAFF_CAP = ITEMS.register("iron_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(-0.1d))));
	public static final RegistrySupplier<Item> GOLDEN_STAFF_CAP = ITEMS.register("golden_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0d))));
	public static final RegistrySupplier<Item> COPPER_STAFF_CAP = ITEMS.register("copper_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0.1d, true))));
	public static final RegistrySupplier<Item> NETHERITE_STAFF_CAP = ITEMS.register("netherite_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(-0.9d, true))));
	public static final RegistrySupplier<Item> ARCANEUM_STAFF_CAP = ITEMS.register("arcaneum_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0.2d, true))));
	public static final RegistrySupplier<Item> VOID_STAFF_CAP = ITEMS.register("void_staff_cap", () -> new StaffCapItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(0.3d, true))));

	public static final RegistrySupplier<Item> WOODEN_STAFF_CORE = ITEMS.register("wooden_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(1.1d, 1.1d, 1.1d, 1.1d, 1.1d))));
	public static final RegistrySupplier<Item> BLAZING_STAFF_CORE = ITEMS.register("blazing_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(0.5d, 1d, 1d, 1d, 1d))));
	public static final RegistrySupplier<Item> BAMBOO_STAFF_CORE = ITEMS.register("bamboo_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(1d, 0.5d, 1d, 1d, 1d))));
	public static final RegistrySupplier<Item> PRISMARINE_STAFF_CORE = ITEMS.register("prismarine_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(1d, 1d, 0.5d, 1d, 1d))));
	public static final RegistrySupplier<Item> BONE_STAFF_CORE = ITEMS.register("bone_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(1d, 1d, 1d, 0.5d, 1d))));
	public static final RegistrySupplier<Item> HELLISH_STAFF_CORE = ITEMS.register("hellish_staff_core", () -> new StaffCoreItem(new Item.Properties().component(ArcanusDataComponents.STAFF_CORE.get(), StaffCoreComponent.of(1d, 1d, 1d, 1d, 0.5d))));

	public static final RegistrySupplier<Item> STAFF = ITEMS.register("staff", StaffItem::new);

	public static final List<Supplier<Item>> HOOD_ITEMS = List.of(
		CULTIST_CLERIC_HOOD
	);
}
