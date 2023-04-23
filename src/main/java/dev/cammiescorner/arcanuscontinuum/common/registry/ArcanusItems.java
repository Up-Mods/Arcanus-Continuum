package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.items.*;
import dev.cammiescorner.arcanuscontinuum.common.util.StaffType;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;

public class ArcanusItems {
	//-----Item Map-----//
	public static final LinkedHashMap<Item, Identifier> ITEMS = new LinkedHashMap<>();

	//-----Items-----//
	public static final Item COMPENDIUM_ARCANUS = create("compendium_arcanus", new ArcanusCompendiumItem());
	public static final Item WOODEN_STAFF = create("wooden_staff", new StaffItem(StaffType.STAFF, 0xffffff, 0x51301a));
	public static final Item CRYSTAL_STAFF = create("crystal_staff", new StaffItem(StaffType.STAFF, 0xffffff, 0x51301a));
	public static final Item DIVINATION_STAFF = create("divination_staff", new StaffItem(StaffType.STAFF, 0xffffff, 0x51301a));
	public static final Item CRESCENT_STAFF = create("crescent_staff", new StaffItem(StaffType.STAFF, 0xffffff, 0x51301a));
	public static final Item ANCIENT_STAFF = create("ancient_staff", new StaffItem(StaffType.STAFF, 0xffffff, 0x51301a));
	public static final Item WAND = create("wand", new StaffItem(StaffType.WAND, 0xffffff, 0x51301a, true));
	public static final Item THAUMATURGES_GAUNTLET = create("thaumaturges_gauntlet", new StaffItem(StaffType.GAUNTLET, 0xffffff, 0x808080, true));
	public static final Item MIND_STAFF = create("mind_staff", new StaffItem(StaffType.STAFF, 0xffffff, 0xffffff, true));
	public static final Item MAGIC_TOME = create("magic_tome", new StaffItem(StaffType.BOOK, 0x8b4513, 0x1e1b1b, true));
	public static final Item MAGE_PISTOL = create("mage_pistol", new StaffItem(StaffType.GUN, 0xffffff, 0xffffff, true));
	public static final Item WIZARD_HAT = create("wizard_hat", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.HEAD, 0.3, 0.15, 0.1));
	public static final Item WIZARD_ROBES = create("wizard_robes", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.CHEST, 0.5, 0.2, 0.18));
	public static final Item WIZARD_PANTS = create("wizard_pants", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.LEGS, 0.45, 0.18, 0.14));
	public static final Item WIZARD_BOOTS = create("wizard_boots", new WizardArmorItem(ArcanusArmourMaterials.WIZARD, EquipmentSlot.FEET, 0.25, 0.12, 0.08));
	public static final Item SPELL_BOOK = create("spell_book", new SpellBookItem());
	public static final Item SCROLL_OF_KNOWLEDGE = create("scroll_of_knowledge", new ScrollOfKnowledgeItem());
	public static final Item WIZARD_SPAWN_EGG = create("wizard_spawn_egg", new SpawnEggItem(ArcanusEntities.WIZARD, 0x52392a, 0xffd87c, new QuiltItemSettings()));
	public static final Item OPOSSUM_SPAWN_EGG = create("opossum_spawn_egg", new SpawnEggItem(ArcanusEntities.OPOSSUM, 0x131317, 0xbdbdbd, new QuiltItemSettings()));

	//-----Registry-----//
	public static void register() {
		FabricItemGroupBuilder.create(Arcanus.id("general")).icon(() -> new ItemStack(ArcanusItems.WOODEN_STAFF)).appendItems(entries -> {
			entries.add(COMPENDIUM_ARCANUS.getDefaultStack());
			entries.add(ArcanusBlocks.ARCANE_WORKBENCH.asItem().getDefaultStack());
			entries.add(WOODEN_STAFF.getDefaultStack());
			entries.add(CRYSTAL_STAFF.getDefaultStack());
			entries.add(DIVINATION_STAFF.getDefaultStack());
			entries.add(CRESCENT_STAFF.getDefaultStack());
			entries.add(ANCIENT_STAFF.getDefaultStack());

			if(isSupporter()) {
				entries.add(WAND.getDefaultStack());
				entries.add(THAUMATURGES_GAUNTLET.getDefaultStack());
				entries.add(MIND_STAFF.getDefaultStack());
				entries.add(MAGIC_TOME.getDefaultStack());
				entries.add(MAGE_PISTOL.getDefaultStack());
			}

			entries.add(WIZARD_HAT.getDefaultStack());
			entries.add(WIZARD_ROBES.getDefaultStack());
			entries.add(WIZARD_PANTS.getDefaultStack());
			entries.add(WIZARD_BOOTS.getDefaultStack());
			entries.add(SPELL_BOOK.getDefaultStack());
			entries.add(SCROLL_OF_KNOWLEDGE.getDefaultStack());
			entries.add(WIZARD_SPAWN_EGG.getDefaultStack());
			entries.add(OPOSSUM_SPAWN_EGG.getDefaultStack());
		}).build();

		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}

	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, Arcanus.id(name));
		return item;
	}

	@ClientOnly
	private static boolean isSupporter() {
		return Arcanus.getSupporters().containsKey(MinecraftClient.getInstance().getSession().getPlayerUuid());
	}
}
