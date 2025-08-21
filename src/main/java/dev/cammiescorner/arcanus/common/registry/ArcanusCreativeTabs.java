package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.data_component.StaffCapComponent;
import dev.cammiescorner.arcanus.common.item.StaffCapItem;
import dev.cammiescorner.arcanus.common.item.StaffCoreItem;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

public class ArcanusCreativeTabs {
	public static final RegistryHandler<CreativeModeTab> CREATIVE_TABS = RegistryHandler.create(Registries.CREATIVE_MODE_TAB, Arcanus.MOD_ID);

	public static final RegistrySupplier<CreativeModeTab> ARCANUS = CREATIVE_TABS.register("arcanus", () -> FabricItemGroup.builder().title(Component.translatable(CREATIVE_TAB_ARCANUS)).icon(() -> new ItemStack(ArcanusItems.ARCANIST_HAT.get())).displayItems((params, entries) -> {
		entries.accept(ArcanusItems.SPELL_SCROLL.get());
		entries.accept(ArcanusItems.SPELL_BOOK.get());

		entries.accept(Blocks.LECTERN);
		entries.accept(ArcanusItems.ARCANEUM_INGOT.get());
		entries.accept(ArcanusItems.ARCANEUM_NUGGET.get());
		entries.accept(ArcanusBlocks.MAGIC_DOOR.get());
		entries.accept(ArcanusBlocks.ARCANE_WORKBENCH.get());
		entries.accept(ArcanusBlocks.ARCANE_PLINTH.get());
		entries.accept(ArcanusBlocks.PEDESTAL.get());
		entries.accept(ArcanusBlocks.JAR.get());

		entries.accept(ArcanusItems.ARCANIST_SPAWN_EGG.get());
		entries.accept(ArcanusItems.CULTIST_CLERIC_SPAWN_EGG.get());
		entries.accept(ArcanusItems.CULTIST_KNIGHT_SPAWN_EGG.get());
		entries.accept(ArcanusItems.OPOSSUM_SPAWN_EGG.get());
	}).build());

	public static final RegistrySupplier<CreativeModeTab> GEAR = CREATIVE_TABS.register("arcanus_gear", () -> FabricItemGroup.builder().title(Component.translatable(CREATIVE_TAB_GEAR)).icon(() -> new ItemStack(ArcanusItems.STAFF.get())).displayItems((params, entries) -> {
		// staves
		entries.accept(ArcanusItems.STAFF.get());

		// trinkets
		entries.accept(ArcanusItems.BOOK_POUCH.get());

		// armor
		entries.accept(ArcanusItems.ARCANIST_HAT.get());
		entries.accept(ArcanusItems.ARCANIST_ROBES.get());
		entries.accept(ArcanusItems.ARCANIST_PANTS.get());
		entries.accept(ArcanusItems.ARCANIST_BOOTS.get());
		entries.accept(ArcanusItems.ARTIFICER_HELMET.get());
		entries.accept(ArcanusItems.ARTIFICER_CHESTPLATE.get());
		entries.accept(ArcanusItems.ARTIFICER_LEGGINGS.get());
		entries.accept(ArcanusItems.ARTIFICER_BOOTS.get());
		entries.accept(ArcanusItems.CULTIST_HOOD.get());
		entries.accept(ArcanusItems.CULTIST_ROBES.get());
		entries.accept(ArcanusItems.CULTIST_PANTS.get());
		entries.accept(ArcanusItems.CULTIST_BOOTS.get());

		// staff caps
		BuiltInRegistries.ITEM.stream().filter(item -> item instanceof StaffCapItem && BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(Arcanus.MOD_ID)).forEach(item -> {
			ItemStack stack = item.getDefaultInstance();
			StaffCapComponent component = stack.get(ArcanusDataComponents.STAFF_CAP.get());
			entries.accept(item);

			if(stack.has(ArcanusDataComponents.STAFF_CAP.get()) && component.isInert() && !stack.is(ArcanusItems.NETHERITE_STAFF_CAP.get())) {
				stack.set(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(component.potency()));
				entries.accept(stack);
			}
		});

		// staff cores
		BuiltInRegistries.ITEM.stream().filter(item -> item instanceof StaffCoreItem && BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(Arcanus.MOD_ID)).forEach(entries::accept);
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
