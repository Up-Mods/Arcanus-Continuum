package dev.cammiescorner.arcanus.datagen.common.advancement;

import dev.cammiescorner.arcanus.data.ArcanusAdvancements;
import dev.cammiescorner.arcanus.data.ArcanusLootTables;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.criterion.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;

import java.util.function.Consumer;

public class ArcanusBookAdvancements implements AdvancementSubProvider {

	@Override
	public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> output) {
		Advancement.Builder.recipeAdvancement()
			.addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
			.rewards(AdvancementRewards.Builder.loot(ArcanusLootTables.COMPENDIUM_ARCANUS))
			.save(output, ArcanusAdvancements.GRANT_COMPENDIUM_ARCANUS);
	}
}
