package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.items.CompendiumItem;
import dev.cammiescorner.arcanuscontinuum.common.items.SpellBookItem;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.items.WizardArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ArcanusItems {
	//-----Item Map-----//
	public static final LinkedHashMap<Item, Identifier> ITEMS = new LinkedHashMap<>();

	//-----Items-----//
	public static final Item COMPENDIUM_ARCANUS = create("compendium_arcanus", new CompendiumItem());
	public static final Item WOODEN_STAFF = create("wooden_staff", new StaffItem());
	public static final Item AMETHYST_SHARD_STAFF = create("amethyst_shard_staff", new StaffItem());
	public static final Item QUARTZ_SHARD_STAFF = create("quartz_shard_staff", new StaffItem());
	public static final Item ENDER_SHARD_STAFF = create("ender_shard_staff", new StaffItem());
	public static final Item ECHO_SHARD_STAFF = create("echo_shard_staff", new StaffItem());
	public static final Item WIZARD_HAT = create("wizard_hat", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.HEAD, 0.21));
	public static final Item WIZARD_ROBES = create("wizard_robes", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.CHEST, 0.34));
	public static final Item WIZARD_PANTS = create("wizard_pants", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.LEGS, 0.29));
	public static final Item WIZARD_BOOTS = create("wizard_boots", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.FEET, 0.16));
	public static final Item SPELL_BOOK = create("spell_book", new SpellBookItem());

	//-----Registry-----//
	public static void register() {
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}

	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, Arcanus.id(name));
		return item;
	}
}
