package dev.cammiescorner.arcanus.common.creative_tabs;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import dev.upcraft.sparkweave.api.item.CreativeTabHelper;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class ArcanusCreativeTab {
	public static final ResourceKey<CreativeModeTab> RESOURCE_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Arcanus.id("00_arcanus_tab"));

	public static CreativeModeTab buildTab() {
		return CreativeTabHelper.newBuilder(RESOURCE_KEY).icon(ArcanusItems.ARCANIST_HAT.get()::getDefaultInstance).displayItems((params, output) -> {
			output.accept(ArcanusItems.ARCANIST_HAT.get());
			output.accept(ArcanusItems.ARCANIST_ROBES.get());
			output.accept(ArcanusItems.ARCANIST_PANTS.get());
			output.accept(ArcanusItems.ARCANIST_BOOTS.get());

			output.accept(ArcanusItems.SPELL_SCROLL.get());
			output.accept(ArcanusItems.SPELL_BOOK.get());

			output.accept(Blocks.LECTERN);
			output.accept(ArcanusItems.EMYRWOOD_LOG.get());
			output.accept(ArcanusItems.EMYRWOOD_WOOD.get());
			output.accept(ArcanusItems.STRIPPED_EMYRWOOD_LOG.get());
			output.accept(ArcanusItems.STRIPPED_EMYRWOOD_WOOD.get());
			output.accept(ArcanusItems.EMYRWOOD_PLANKS.get());
			output.accept(ArcanusItems.EMYRWOOD_STAIRS.get());
			output.accept(ArcanusItems.EMYRWOOD_SLAB.get());
			output.accept(ArcanusBlocks.ARCANE_WORKBENCH.get());
			output.accept(ArcanusBlocks.PEDESTAL.get());
			output.accept(ArcanusBlocks.ARCANE_PLINTH.get());

			output.accept(ArcanusItems.ARCANIST_SPAWN_EGG.get());
			output.accept(ArcanusItems.OPOSSUM_SPAWN_EGG.get());

			List<? extends SpellComponent> components = ArcanusSpellComponents.SPELL_COMPONENTS.getEntriesOrdered().stream()
				.map(RegistrySupplier::get)
				.sorted(ArcanusCreativeTab::sortComponents)
				.toList();

			for(SpellComponent spellComponent : components) {
				if(spellComponent == ArcanusSpellComponents.EMPTY.get())
					continue;

				ItemStack stack = new ItemStack(ArcanusItems.SCROLL_OF_KNOWLEDGE.get());

				stack.set(ArcanusDataComponents.SPELL_COMPONENT.get(), spellComponent);
				output.accept(stack);
			}
		}).build();
	}

	private static int sortComponents(SpellComponent component1, SpellComponent component2) {
		if(component1 instanceof SpellShape && component2 instanceof SpellEffect)
			return -1;
		if(component2 instanceof SpellShape && component1 instanceof SpellEffect)
			return 1;

		return 0;
	}
}
