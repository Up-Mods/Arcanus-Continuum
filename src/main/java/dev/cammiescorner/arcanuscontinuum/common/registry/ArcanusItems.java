package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.items.*;
import dev.cammiescorner.arcanuscontinuum.common.util.StaffType;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.component.TranslatableComponent;
import net.minecraft.util.Formatting;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.List;

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

	public static final RegistrySupplier<Item> WIZARD_HAT = ITEMS.register("wizard_hat", () -> new WizardArmorItem(ArcanusArmourMaterials.WIZARD, ArmorItem.ArmorSlot.HELMET, 1.5, 0.15, 0.2));
	public static final RegistrySupplier<Item> WIZARD_ROBES = ITEMS.register("wizard_robes", () -> new WizardArmorItem(ArcanusArmourMaterials.WIZARD, ArmorItem.ArmorSlot.CHESTPLATE, 1.5, 0.15, 0.4));
	public static final RegistrySupplier<Item> WIZARD_PANTS = ITEMS.register("wizard_pants", () -> new WizardArmorItem(ArcanusArmourMaterials.WIZARD, ArmorItem.ArmorSlot.LEGGINGS, 1.5, 0.15, 0.3));
	public static final RegistrySupplier<Item> WIZARD_BOOTS = ITEMS.register("wizard_boots", () -> new WizardArmorItem(ArcanusArmourMaterials.WIZARD, ArmorItem.ArmorSlot.BOOTS, 1.5, 0.15, 0.1));
	public static final RegistrySupplier<Item> BATTLE_MAGE_HELMET = ITEMS.register("battle_mage_helmet", () -> new WizardArmorItem(ArcanusArmourMaterials.BATTLE_MAGE, ArmorItem.ArmorSlot.HELMET, 0.5, 0.1, 0.1));
	public static final RegistrySupplier<Item> BATTLE_MAGE_CHESTPLATE = ITEMS.register("battle_mage_chestplate", () -> new WizardArmorItem(ArcanusArmourMaterials.BATTLE_MAGE, ArmorItem.ArmorSlot.CHESTPLATE, 0.5, 0.1, 0.2));
	public static final RegistrySupplier<Item> BATTLE_MAGE_LEGGINGS = ITEMS.register("battle_mage_leggings", () -> new WizardArmorItem(ArcanusArmourMaterials.BATTLE_MAGE, ArmorItem.ArmorSlot.LEGGINGS, 0.5, 0.1, 0.15));
	public static final RegistrySupplier<Item> BATTLE_MAGE_BOOTS = ITEMS.register("battle_mage_boots", () -> new WizardArmorItem(ArcanusArmourMaterials.BATTLE_MAGE, ArmorItem.ArmorSlot.BOOTS, 0.5, 0.1, 0.05));

	public static final RegistrySupplier<Item> BATTLE_MAGE_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("battle_mage_upgrade_smithing_template", ArcanusItems::getBattleMageUpgrade);
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

			if(Arcanus.isCurrentPlayerSupporter()) {
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
			entries.addItem(BATTLE_MAGE_HELMET.get());
			entries.addItem(BATTLE_MAGE_CHESTPLATE.get());
			entries.addItem(BATTLE_MAGE_LEGGINGS.get());
			entries.addItem(BATTLE_MAGE_BOOTS.get());
			entries.addItem(BATTLE_MAGE_UPGRADE_SMITHING_TEMPLATE.get());

			entries.addItem(WIZARD_SPAWN_EGG.get());
			entries.addItem(OPOSSUM_SPAWN_EGG.get());
		}).build());

	private static MutableText smithingTemplateText(String upgradeType, String desc) {
		return Arcanus.translate("item", "smithing_template", upgradeType, desc);
	}

	private static SmithingTemplateItem getBattleMageUpgrade() {
		return new SmithingTemplateItem(
				smithingTemplateText("battle_mage_upgrade", "applies_to").formatted(Formatting.BLUE),
				smithingTemplateText("battle_mage_upgrade", "ingredients").formatted(Formatting.BLUE),
				Arcanus.translate("upgrade", "battle_mage_upgrade").formatted(Formatting.GRAY),
				smithingTemplateText("battle_mage_upgrade", "base_slot_description"),
				smithingTemplateText("battle_mage_upgrade", "additions_slot_description"),
				SmithingTemplateItem.getArmorIcons(),
				List.of(SmithingTemplateItem.AMETHYST_ICON)
		);
	}
}
