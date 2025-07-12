package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.item.*;
import dev.cammiescorner.arcanus.common.util.StaffType;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.List;
import java.util.function.Supplier;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

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

	public static final RegistrySupplier<Item> WIZARD_HAT = ITEMS.register("wizard_hat", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET, 1, 0.1, 0, 0, -0.06));
	public static final RegistrySupplier<Item> WIZARD_ROBES = ITEMS.register("wizard_robes", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE, 2, 0.2, 0, 0, -0.12));
	public static final RegistrySupplier<Item> WIZARD_PANTS = ITEMS.register("wizard_pants", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS, 2, 0.2, 0, 0, -0.1));
	public static final RegistrySupplier<Item> WIZARD_BOOTS = ITEMS.register("wizard_boots", () -> new WizardRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS, 1, 0.1, 0, 0, -0.05));
	public static final RegistrySupplier<Item> RED_CULT_HOOD = ITEMS.register("red_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> RED_CULT_ROBES = ITEMS.register("red_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> RED_CULT_PANTS = ITEMS.register("red_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> RED_CULT_BOOTS = ITEMS.register("red_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> GREEN_CULT_HOOD = ITEMS.register("green_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> GREEN_CULT_ROBES = ITEMS.register("green_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> GREEN_CULT_PANTS = ITEMS.register("green_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> GREEN_CULT_BOOTS = ITEMS.register("green_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> BLUE_CULT_HOOD = ITEMS.register("blue_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> BLUE_CULT_ROBES = ITEMS.register("blue_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> BLUE_CULT_PANTS = ITEMS.register("blue_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> BLUE_CULT_BOOTS = ITEMS.register("blue_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> WHITE_CULT_HOOD = ITEMS.register("white_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> WHITE_CULT_ROBES = ITEMS.register("white_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> WHITE_CULT_PANTS = ITEMS.register("white_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> WHITE_CULT_BOOTS = ITEMS.register("white_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS));
	public static final RegistrySupplier<Item> BLACK_CULT_HOOD = ITEMS.register("black_cult_hood", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.HELMET));
	public static final RegistrySupplier<Item> BLACK_CULT_ROBES = ITEMS.register("black_cult_robes", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.CHESTPLATE));
	public static final RegistrySupplier<Item> BLACK_CULT_PANTS = ITEMS.register("black_cult_pants", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.LEGGINGS));
	public static final RegistrySupplier<Item> BLACK_CULT_BOOTS = ITEMS.register("black_cult_boots", () -> new CultRobesItem(ArcanusArmorMaterials.WIZARD.holder(), ArmorItem.Type.BOOTS));
//	public static final RegistrySupplier<Item> BATTLE_MAGE_HELMET = ITEMS.register("battle_mage_helmet", () -> new BattleMageArmorItem(ArcanusArmorMaterials.BATTLE_MAGE.holder(), ArmorItem.Type.HELMET, 0.25, 0, 0.2, 0.06, 0));
//	public static final RegistrySupplier<Item> BATTLE_MAGE_CHESTPLATE = ITEMS.register("battle_mage_chestplate", () -> new BattleMageArmorItem(ArcanusArmorMaterials.BATTLE_MAGE.holder(), ArmorItem.Type.CHESTPLATE, 0.75, 0, 0.3, 0.12, 0));
//	public static final RegistrySupplier<Item> BATTLE_MAGE_LEGGINGS = ITEMS.register("battle_mage_leggings", () -> new BattleMageArmorItem(ArcanusArmorMaterials.BATTLE_MAGE.holder(), ArmorItem.Type.LEGGINGS, 0.75, 0, 0.3, 0.1, 0));
//	public static final RegistrySupplier<Item> BATTLE_MAGE_BOOTS = ITEMS.register("battle_mage_boots", () -> new BattleMageArmorItem(ArcanusArmorMaterials.BATTLE_MAGE.holder(), ArmorItem.Type.BOOTS, 0.25, 0, 0.2, 0.05, 0));

//	public static final RegistrySupplier<Item> BATTLE_MAGE_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("battle_mage_upgrade_smithing_template", ArcanusItems::getBattleMageUpgrade);
	public static final RegistrySupplier<Item> BOOK_POUCH = ITEMS.register("book_pouch", BookPouchItem::new);
	public static final RegistrySupplier<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new);
	public static final RegistrySupplier<Item> SPELL_SCROLL = ITEMS.register("spell_scroll", SpellScrollItem::new);
	public static final RegistrySupplier<Item> SCROLL_OF_KNOWLEDGE = ITEMS.register("scroll_of_knowledge", ScrollOfKnowledgeItem::new);
	public static final RegistrySupplier<Item> WIZARD_SPAWN_EGG = ITEMS.register("wizard_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.WIZARD.get(), 0x52392a, 0xffd87c, new Item.Properties()));
	public static final RegistrySupplier<Item> OPOSSUM_SPAWN_EGG = ITEMS.register("opossum_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.OPOSSUM.get(), 0x131317, 0xbdbdbd, new Item.Properties()));

	private static SmithingTemplateItem getBattleMageUpgrade() {
		var appliesToText = Component.translatable(BATTLE_MAGE_UPGRADE_APPLIES_TO).withStyle(ChatFormatting.BLUE);
		var ingredientsText = Component.translatable(BATTLE_MAGE_UPGRADE_INGREDIENTS).withStyle(ChatFormatting.BLUE);
		var upgradeText = Component.translatable(BATTLE_MAGE_UPGRADE).withStyle(ChatFormatting.GRAY);
		var baseSlotText = Component.translatable(BATTLE_MAGE_UPGRADE_BASE_SLOT_DESC);
		var additionsSlotText = Component.translatable(BATTLE_MAGE_UPGRADE_ADDITIONS_SLOT_DESC);
		var baseIcons = SmithingTemplateItem.createTrimmableArmorIconList();
		var additionsIcons = List.of(SmithingTemplateItem.EMPTY_SLOT_AMETHYST_SHARD);
		return new SmithingTemplateItem(appliesToText, ingredientsText, upgradeText, baseSlotText, additionsSlotText, baseIcons, additionsIcons);
	}

	public static final List<Supplier<Item>> HOOD_ITEMS = List.of(
		RED_CULT_HOOD, GREEN_CULT_HOOD, BLUE_CULT_HOOD, WHITE_CULT_HOOD, BLACK_CULT_HOOD
	);
}
