package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.items.ScrollOfKnowledgeItem;
import dev.cammiescorner.arcanuscontinuum.common.items.SpellBookItem;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.items.WizardArmorItem;
import dev.cammiescorner.arcanuscontinuum.common.util.StaffType;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ArcanusItems {

	public static final RegistryHandler<ItemGroup> ITEM_GROUPS = RegistryHandler.create(RegistryKeys.ITEM_GROUP, Arcanus.MOD_ID);
	public static final RegistryHandler<Item> ITEMS = RegistryHandler.create(RegistryKeys.ITEM, Arcanus.MOD_ID);

	public static final RegistrySupplier<Item> WOODEN_STAFF = ITEMS.register("wooden_staff", () -> new StaffItem(StaffType.STAFF, 0xffffff, 0x51301a));
	public static final RegistrySupplier<Item> CRYSTAL_STAFF = ITEMS.register("crystal_staff", () -> new StaffItem(StaffType.STAFF, 0xffffff, 0x51301a));
	public static final RegistrySupplier<Item> DIVINATION_STAFF = ITEMS.register("divination_staff", () -> new StaffItem(StaffType.STAFF, 0xffffff, 0x51301a));
	public static final RegistrySupplier<Item> CRESCENT_STAFF = ITEMS.register("crescent_staff", () -> new StaffItem(StaffType.STAFF, 0xffffff, 0x51301a));
	public static final RegistrySupplier<Item> ANCIENT_STAFF = ITEMS.register("ancient_staff", () -> new StaffItem(StaffType.STAFF, 0xffffff, 0x51301a));
	public static final RegistrySupplier<Item> WAND = ITEMS.register("wand", () -> new StaffItem(StaffType.WAND, 0xffffff, 0x51301a, true));
	public static final RegistrySupplier<Item> THAUMATURGES_GAUNTLET = ITEMS.register("thaumaturges_gauntlet", () -> new StaffItem(StaffType.GAUNTLET, 0xffffff, 0x808080, true));
	public static final RegistrySupplier<Item> MIND_STAFF = ITEMS.register("mind_staff", () -> new StaffItem(StaffType.STAFF, 0xffffff, 0xffffff, true));
	public static final RegistrySupplier<Item> MAGIC_TOME = ITEMS.register("magic_tome", () -> new StaffItem(StaffType.BOOK, 0x8b4513, 0x1e1b1b, true));
	public static final RegistrySupplier<Item> MAGE_PISTOL = ITEMS.register("mage_pistol", () -> new StaffItem(StaffType.GUN, 0xffffff, 0xffffff, true));
	public static final RegistrySupplier<Item> WIZARD_HAT = ITEMS.register("wizard_hat", () -> new WizardArmorItem(ArcanusArmourMaterials.WIZARD, ArmorItem.ArmorSlot.HELMET, 1, 0.15, 0.1));
	public static final RegistrySupplier<Item> WIZARD_ROBES = ITEMS.register("wizard_robes", () -> new WizardArmorItem(ArcanusArmourMaterials.WIZARD, ArmorItem.ArmorSlot.CHESTPLATE, 2, 0.2, 0.18));
	public static final RegistrySupplier<Item> WIZARD_PANTS = ITEMS.register("wizard_pants", () -> new WizardArmorItem(ArcanusArmourMaterials.WIZARD, ArmorItem.ArmorSlot.LEGGINGS, 2, 0.18, 0.14));
	public static final RegistrySupplier<Item> WIZARD_BOOTS = ITEMS.register("wizard_boots", () -> new WizardArmorItem(ArcanusArmourMaterials.WIZARD, ArmorItem.ArmorSlot.BOOTS, 1, 0.12, 0.08));
	public static final RegistrySupplier<Item> SPELL_BOOK = ITEMS.register("spell_book", SpellBookItem::new);
	public static final RegistrySupplier<Item> SCROLL_OF_KNOWLEDGE = ITEMS.register("scroll_of_knowledge", ScrollOfKnowledgeItem::new);
	public static final RegistrySupplier<Item> WIZARD_SPAWN_EGG = ITEMS.register("wizard_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.WIZARD.get(), 0x52392a, 0xffd87c, new QuiltItemSettings()));
	public static final RegistrySupplier<Item> OPOSSUM_SPAWN_EGG = ITEMS.register("opossum_spawn_egg", () -> new SpawnEggItem(ArcanusEntities.OPOSSUM.get(), 0x131317, 0xbdbdbd, new QuiltItemSettings()));

	public static final RegistrySupplier<ItemGroup> ITEM_GROUP = ITEM_GROUPS.register("general", () -> FabricItemGroup.builder()
		.name(Arcanus.translate("itemGroup", "general"))
		.icon(() -> new ItemStack(ArcanusItems.CRYSTAL_STAFF.get()))
		.entries((params, entries) -> {
			entries.addItem(ArcanusBlocks.MAGIC_DOOR.get());
			entries.addItem(ArcanusBlocks.ARCANE_WORKBENCH.get());
			entries.addItem(SPELL_BOOK.get());
			entries.addItem(SCROLL_OF_KNOWLEDGE.get());
			entries.addItem(WOODEN_STAFF.get());
			entries.addItem(CRYSTAL_STAFF.get());
			entries.addItem(DIVINATION_STAFF.get());
			entries.addItem(CRESCENT_STAFF.get());
			entries.addItem(ANCIENT_STAFF.get());

			if (Arcanus.isCurrentPlayerSupporter()) {
				entries.addItem(WAND.get());
				entries.addItem(THAUMATURGES_GAUNTLET.get());
				entries.addItem(MIND_STAFF.get());
				entries.addItem(MAGIC_TOME.get());
				entries.addItem(MAGE_PISTOL.get());
			}

			entries.addItem(WIZARD_HAT.get());
			entries.addItem(WIZARD_ROBES.get());
			entries.addItem(WIZARD_PANTS.get());
			entries.addItem(WIZARD_BOOTS.get());
			entries.addItem(WIZARD_SPAWN_EGG.get());
			entries.addItem(OPOSSUM_SPAWN_EGG.get());
		}).build());
}
