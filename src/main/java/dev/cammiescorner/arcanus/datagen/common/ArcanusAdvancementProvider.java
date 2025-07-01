package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.common.data.ArcanusAdvancements;
import dev.cammiescorner.arcanus.common.data.ArcanusLootTables;
import dev.cammiescorner.arcanus.common.data.ArcanusStructures;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.upcraft.sparkweave.api.registry.RegistryHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ArcanusAdvancementProvider extends FabricAdvancementProvider {
	public ArcanusAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> writer) {
		Advancement.Builder.recipeAdvancement().addCriterion("tick", PlayerTrigger.TriggerInstance.tick()).rewards(AdvancementRewards.Builder.loot(ArcanusLootTables.COMPENDIUM_ARCANUS)).save(writer, ArcanusAdvancements.GRANT_COMPENDIUM_ARCANUS.toString());

		var arcaneRoot = Advancement.Builder.advancement().display(ArcanusItems.CRYSTAL_STAFF.get(), Component.translatable("advancements.arcanus.arcane.root.title"), Component.translatable("advancements.arcanus.arcane.root.description"), ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"), AdvancementType.TASK, false, false, false).addCriterion("tick", PlayerTrigger.TriggerInstance.tick()).save(writer, ArcanusAdvancements.ARCANE_ROOT.toString());
		var aMagicalCrystal = Advancement.Builder.advancement().parent(arcaneRoot).display(Items.AMETHYST_SHARD, Component.translatable("advancements.arcanus.arcane.a_magical_crystal.title"), Component.translatable("advancements.arcanus.arcane.a_magical_crystal.description"), null, AdvancementType.GOAL, true, true, false).addCriterion("has_amethyst", InventoryChangeTrigger.TriggerInstance.hasItems(Items.AMETHYST_SHARD)).save(writer, ArcanusAdvancements.A_MAGICAL_CRYSTAL.toString());
		var arcaneArtifice = Advancement.Builder.advancement().parent(aMagicalCrystal).display(ArcanusBlocks.ARCANE_WORKBENCH.get(), Component.translatable("advancements.arcanus.arcane.arcane_artifice.title"), Component.translatable("advancements.arcanus.arcane.arcane_artifice.description"), null, AdvancementType.GOAL, true, true, false).addCriterion("has_arcane_workbench", InventoryChangeTrigger.TriggerInstance.hasItems(ArcanusBlocks.ARCANE_WORKBENCH.get())).save(writer, ArcanusAdvancements.ARCANE_ARTIFICE.toString());
		var magicalMeetAndGreet = Advancement.Builder.advancement().parent(aMagicalCrystal).display(ArcanusItems.WIZARD_HAT.get(), Component.translatable("advancements.arcanus.arcane.magical_meet_and_greet.title"), Component.translatable("advancements.arcanus.arcane.magical_meet_and_greet.description"), null, AdvancementType.GOAL, true, true, false).addCriterion("discovered_wizard_tower", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(RegistryHelper.getBuiltinRegistry(Registries.STRUCTURE).getHolderOrThrow(ArcanusStructures.WIZARD_TOWER)))).save(writer, ArcanusAdvancements.MAGICAL_MEET_AND_GREET.toString());
	}
}
