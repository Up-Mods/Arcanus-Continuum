package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.items.*;
import dev.cammiescorner.arcanuscontinuum.common.util.StaffType;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;

public class ArcanusItems {
	//-----Item Map-----//
	public static final LinkedHashMap<Item, Identifier> ITEMS = new LinkedHashMap<>();

	//-----Items-----//
	public static final Item COMPENDIUM_ARCANUS = create("compendium_arcanus", new ArcanusCompendiumItem());
	public static final Item WOODEN_STAFF = create("wooden_staff", new StaffItem());
	public static final Item AMETHYST_SHARD_STAFF = create("amethyst_shard_staff", new StaffItem());
	public static final Item QUARTZ_SHARD_STAFF = create("quartz_shard_staff", new StaffItem());
	public static final Item ENDER_SHARD_STAFF = create("ender_shard_staff", new StaffItem());
	public static final Item ECHO_SHARD_STAFF = create("echo_shard_staff", new StaffItem());
	public static final Item MIND_STAFF = create("mind_staff", new StaffItem());
	public static final Item MAGIC_TOME = create("magic_tome", new StaffItem(StaffType.BOOK));
	public static final Item MAGE_PISTOL = create("mage_pistol", new StaffItem(StaffType.WAND));
	public static final Item WIZARD_HAT = create("wizard_hat", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.HEAD, 0.21, 0.14));
	public static final Item WIZARD_ROBES = create("wizard_robes", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.CHEST, 0.34, 0.21));
	public static final Item WIZARD_PANTS = create("wizard_pants", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.LEGS, 0.29, 0.19));
	public static final Item WIZARD_BOOTS = create("wizard_boots", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.FEET, 0.16, 0.11));
	public static final Item SPELL_BOOK = create("spell_book", new SpellBookItem());
	public static final Item SCROLL_OF_KNOWLEDGE = create("scroll_of_knowledge", new ScrollOfKnowledgeItem());
	public static final Item WIZARD_SPAWN_EGG = create("wizard_spawn_egg", new SpawnEggItem(ArcanusEntities.WIZARD, 0x52392a, 0xffd87c, new QuiltItemSettings()));
	public static final Item OPOSSUM_SPAWN_EGG = create("opossum_spawn_egg", new SpawnEggItem(ArcanusEntities.OPOSSUM, 0x131317, 0xbdbdbd, new QuiltItemSettings()));

	//-----Registry-----//
	public static void register() {
		FabricItemGroup.builder(Arcanus.id("general")).icon(() -> new ItemStack(ArcanusItems.WOODEN_STAFF)).entries((enabledFeatures, entries, operatorsEnabled) -> {
			entries.addItem(ArcanusBlocks.MAGIC_DOOR);
			entries.addItem(ArcanusBlocks.ARCANE_WORKBENCH);
			entries.addItem(COMPENDIUM_ARCANUS);
			entries.addItem(WOODEN_STAFF);
			entries.addItem(AMETHYST_SHARD_STAFF);
			entries.addItem(QUARTZ_SHARD_STAFF);
			entries.addItem(ENDER_SHARD_STAFF);
			entries.addItem(ECHO_SHARD_STAFF);
			entries.addItem(MIND_STAFF);
			entries.addItem(MAGIC_TOME);
			entries.addItem(MAGE_PISTOL);
			entries.addItem(WIZARD_HAT);
			entries.addItem(WIZARD_ROBES);
			entries.addItem(WIZARD_PANTS);
			entries.addItem(WIZARD_BOOTS);
			entries.addItem(SPELL_BOOK);
			entries.addItem(SCROLL_OF_KNOWLEDGE);
			entries.addItem(WIZARD_SPAWN_EGG);
			entries.addItem(OPOSSUM_SPAWN_EGG);
		}).build();

		ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));
	}

	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, Arcanus.id(name));
		return item;
	}
}
