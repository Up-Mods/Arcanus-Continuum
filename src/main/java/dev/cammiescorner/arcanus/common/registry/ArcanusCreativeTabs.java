package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.item.StaffItem;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.UUID;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

public class ArcanusCreativeTabs {
	public static final RegistryHandler<CreativeModeTab> CREATIVE_TABS = RegistryHandler.create(Registries.CREATIVE_MODE_TAB, Arcanus.MOD_ID);

	public static final RegistrySupplier<CreativeModeTab> ARCANUS = CREATIVE_TABS.register("arcanus", () -> FabricItemGroup.builder().title(Component.translatable(CREATIVE_TAB_ARCANUS)).icon(() -> new ItemStack(ArcanusItems.WIZARD_HAT.get())).displayItems((params, entries) -> {
		entries.accept(ArcanusItems.SPELL_SCROLL.get());
		entries.accept(ArcanusItems.SPELL_BOOK.get());

		entries.accept(Blocks.LECTERN);
		entries.accept(ArcanusBlocks.CHALK.get());
		entries.accept(ArcanusBlocks.MAGIC_DOOR.get());
		entries.accept(ArcanusBlocks.ARCANE_WORKBENCH.get());
		entries.accept(ArcanusBlocks.ARCANE_PLINTH.get());
		entries.accept(ArcanusBlocks.PEDESTAL.get());
		entries.accept(ArcanusBlocks.JAR.get());

		entries.accept(ArcanusItems.WIZARD_SPAWN_EGG.get());
		entries.accept(ArcanusItems.OPOSSUM_SPAWN_EGG.get());
	}).build());

	public static final RegistrySupplier<CreativeModeTab> GEAR = CREATIVE_TABS.register("arcanus_gear", () -> FabricItemGroup.builder().title(Component.translatable(CREATIVE_TAB_GEAR)).icon(() -> new ItemStack(ArcanusItems.CRYSTAL_STAFF.get())).displayItems((params, entries) -> {
		UUID dummyId = UUID.fromString("6147825f-5493-4154-87c5-5c03c6b0a7c2");

		// staves
		entries.accept(StaffItem.setCraftedBy(new ItemStack(ArcanusItems.WOODEN_STAFF.get()), dummyId));
		entries.accept(StaffItem.setCraftedBy(new ItemStack(ArcanusItems.CRYSTAL_STAFF.get()), dummyId));
		entries.accept(StaffItem.setCraftedBy(new ItemStack(ArcanusItems.DIVINATION_STAFF.get()), dummyId));
		entries.accept(StaffItem.setCraftedBy(new ItemStack(ArcanusItems.CRESCENT_STAFF.get()), dummyId));
		entries.accept(StaffItem.setCraftedBy(new ItemStack(ArcanusItems.ANCIENT_STAFF.get()), dummyId));
		entries.accept(StaffItem.setCraftedBy(new ItemStack(ArcanusItems.WAND.get()), dummyId));
		entries.accept(StaffItem.setCraftedBy(new ItemStack(ArcanusItems.THAUMATURGES_GAUNTLET.get()), dummyId));
		entries.accept(StaffItem.setCraftedBy(new ItemStack(ArcanusItems.MIND_STAFF.get()), dummyId));
		entries.accept(StaffItem.setCraftedBy(new ItemStack(ArcanusItems.MAGIC_TOME.get()), dummyId));
		entries.accept(StaffItem.setCraftedBy(new ItemStack(ArcanusItems.MAGE_PISTOL.get()), dummyId));

		// trinkets
		entries.accept(ArcanusItems.BOOK_POUCH.get());

		// armor
		entries.accept(ArcanusItems.WIZARD_HAT.get());
		entries.accept(ArcanusItems.WIZARD_ROBES.get());
		entries.accept(ArcanusItems.WIZARD_PANTS.get());
		entries.accept(ArcanusItems.WIZARD_BOOTS.get());
		entries.accept(ArcanusItems.RED_CULT_HOOD.get());
		entries.accept(ArcanusItems.RED_CULT_ROBES.get());
		entries.accept(ArcanusItems.RED_CULT_PANTS.get());
		entries.accept(ArcanusItems.RED_CULT_BOOTS.get());
		entries.accept(ArcanusItems.GREEN_CULT_HOOD.get());
		entries.accept(ArcanusItems.GREEN_CULT_ROBES.get());
		entries.accept(ArcanusItems.GREEN_CULT_PANTS.get());
		entries.accept(ArcanusItems.GREEN_CULT_BOOTS.get());
		entries.accept(ArcanusItems.BLUE_CULT_HOOD.get());
		entries.accept(ArcanusItems.BLUE_CULT_ROBES.get());
		entries.accept(ArcanusItems.BLUE_CULT_PANTS.get());
		entries.accept(ArcanusItems.BLUE_CULT_BOOTS.get());
		entries.accept(ArcanusItems.WHITE_CULT_HOOD.get());
		entries.accept(ArcanusItems.WHITE_CULT_ROBES.get());
		entries.accept(ArcanusItems.WHITE_CULT_PANTS.get());
		entries.accept(ArcanusItems.WHITE_CULT_BOOTS.get());
		entries.accept(ArcanusItems.BLACK_CULT_HOOD.get());
		entries.accept(ArcanusItems.BLACK_CULT_ROBES.get());
		entries.accept(ArcanusItems.BLACK_CULT_PANTS.get());
		entries.accept(ArcanusItems.BLACK_CULT_BOOTS.get());
//		entries.accept(ArcanusItems.BATTLE_MAGE_HELMET.get());
//		entries.accept(ArcanusItems.BATTLE_MAGE_CHESTPLATE.get());
//		entries.accept(ArcanusItems.BATTLE_MAGE_LEGGINGS.get());
//		entries.accept(ArcanusItems.BATTLE_MAGE_BOOTS.get());
//		entries.accept(ArcanusItems.BATTLE_MAGE_UPGRADE_SMITHING_TEMPLATE.get());
	}).build());

	public static final RegistrySupplier<CreativeModeTab> SCROLLS = CREATIVE_TABS.register("arcanus_scrolls", () -> FabricItemGroup.builder().title(Component.translatable(CREATIVE_TAB_SCROLLS)).icon(() -> new ItemStack(ArcanusItems.SCROLL_OF_KNOWLEDGE.get())).displayItems((params, entries) -> {
		List<? extends SpellComponent> components = ArcanusSpellComponents.SPELL_COMPONENTS.getEntriesOrdered().stream()
			.map(RegistrySupplier::get)
			.sorted(ArcanusCreativeTabs::sortComponents)
			.toList();

		for(SpellComponent spellComponent : components) {
			if(spellComponent == ArcanusSpellComponents.EMPTY.get())
				continue;

			ItemStack stack = new ItemStack(ArcanusItems.SCROLL_OF_KNOWLEDGE.get());

			stack.set(ArcanusDataComponents.SPELL_COMPONENT.get(), spellComponent);
			entries.accept(stack);
		}
	}).build());

	private static int sortComponents(SpellComponent component1, SpellComponent component2) {
		if(component1 instanceof SpellShape && component2 instanceof SpellEffect)
			return -1;
		if(component2 instanceof SpellShape && component1 instanceof SpellEffect)
			return 1;

		return 0;
	}
}
