package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.common.data.ArcanusAdvancements;
import dev.cammiescorner.arcanus.common.data.ArcanusLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ArcanusAdvancementProvider extends FabricAdvancementProvider {
	public ArcanusAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> writer) {
		Advancement.Builder.recipeAdvancement().addCriterion("tick", PlayerTrigger.TriggerInstance.tick()).rewards(AdvancementRewards.Builder.loot(ArcanusLootTables.COMPENDIUM_ARCANUS)).save(writer, ArcanusAdvancements.GRANT_COMPENDIUM_ARCANUS.toString());
	}
}
