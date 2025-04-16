package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.common.criterion.WizardLevelCriterion;
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
import net.minecraft.resources.ResourceKey;
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
		Advancement.Builder.recipeAdvancement().addCriterion("tick", PlayerTrigger.TriggerInstance.tick()).rewards(AdvancementRewards.Builder.loot(ResourceKey.create(Registries.LOOT_TABLE, ArcanusLootTables.COMPENDIUM_ARCANUS))).save(writer, ArcanusAdvancements.GRANT_COMPENDIUM_ARCANUS.toString());

		var arcaneRoot = Advancement.Builder.advancement().display(ArcanusItems.CRYSTAL_STAFF.get(), Component.translatable("advancements.arcanus.arcane.root.title"), Component.translatable("advancements.arcanus.arcane.root.description"), ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"), AdvancementType.TASK, false, false, false).addCriterion("tick", PlayerTrigger.TriggerInstance.tick()).save(writer, ArcanusAdvancements.ARCANE_ROOT.toString());
		var aMagicalCrystal = Advancement.Builder.advancement().parent(arcaneRoot).display(Items.AMETHYST_SHARD, Component.translatable("advancements.arcanus.arcane.a_magical_crystal.title"), Component.translatable("advancements.arcanus.arcane.a_magical_crystal.description"), null, AdvancementType.GOAL, true, true, false).addCriterion("has_amethyst", InventoryChangeTrigger.TriggerInstance.hasItems(Items.AMETHYST_SHARD)).save(writer, ArcanusAdvancements.A_MAGICAL_CRYSTAL.toString());
		var arcaneArtifice = Advancement.Builder.advancement().parent(aMagicalCrystal).display(ArcanusBlocks.ARCANE_WORKBENCH.get(), Component.translatable("advancements.arcanus.arcane.arcane_artifice.title"), Component.translatable("advancements.arcanus.arcane.arcane_artifice.description"), null, AdvancementType.GOAL, true, true, false).addCriterion("has_arcane_workbench", InventoryChangeTrigger.TriggerInstance.hasItems(ArcanusBlocks.ARCANE_WORKBENCH.get())).save(writer, ArcanusAdvancements.ARCANE_ARTIFICE.toString());
		var magicalMeetAndGreet = Advancement.Builder.advancement().parent(aMagicalCrystal).display(ArcanusItems.WIZARD_HAT.get(), Component.translatable("advancements.arcanus.arcane.magical_meet_and_greet.title"), Component.translatable("advancements.arcanus.arcane.magical_meet_and_greet.description"), null, AdvancementType.GOAL, true, true, false).addCriterion("discovered_wizard_tower", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(RegistryHelper.getBuiltinRegistry(Registries.STRUCTURE).getHolderOrThrow(ArcanusStructures.WIZARD_TOWER)))).save(writer, ArcanusAdvancements.MAGICAL_MEET_AND_GREET.toString());

		var wizardLevel1 = Advancement.Builder.advancement().parent(magicalMeetAndGreet).display(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), Component.translatable("advancements.arcanus.arcane.wizard_level_1.title"), Component.translatable("advancements.arcanus.arcane.wizard_level_1.description"), null, AdvancementType.TASK, true, true, false).addCriterion("has_level_1", WizardLevelCriterion.TriggerInstance.hasWizardLevel(1)).save(writer, ArcanusAdvancements.WIZARD_LEVEL_1.toString());
		var wizardLevel2 = Advancement.Builder.advancement().parent(wizardLevel1).display(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), Component.translatable("advancements.arcanus.arcane.wizard_level_2.title"), Component.translatable("advancements.arcanus.arcane.wizard_level_2.description"), null, AdvancementType.TASK, false, false, false).addCriterion("has_level_2", WizardLevelCriterion.TriggerInstance.hasWizardLevel(2)).save(writer, ArcanusAdvancements.WIZARD_LEVEL_2.toString());
		var wizardLevel3 = Advancement.Builder.advancement().parent(wizardLevel2).display(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), Component.translatable("advancements.arcanus.arcane.wizard_level_3.title"), Component.translatable("advancements.arcanus.arcane.wizard_level_3.description"), null, AdvancementType.TASK, false, false, false).addCriterion("has_level_3", WizardLevelCriterion.TriggerInstance.hasWizardLevel(3)).save(writer, ArcanusAdvancements.WIZARD_LEVEL_3.toString());
		var wizardLevel4 = Advancement.Builder.advancement().parent(wizardLevel3).display(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), Component.translatable("advancements.arcanus.arcane.wizard_level_4.title"), Component.translatable("advancements.arcanus.arcane.wizard_level_4.description"), null, AdvancementType.TASK, false, false, false).addCriterion("has_level_4", WizardLevelCriterion.TriggerInstance.hasWizardLevel(4)).save(writer, ArcanusAdvancements.WIZARD_LEVEL_4.toString());
		var wizardLevel5 = Advancement.Builder.advancement().parent(wizardLevel4).display(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), Component.translatable("advancements.arcanus.arcane.wizard_level_5.title"), Component.translatable("advancements.arcanus.arcane.wizard_level_5.description"), null, AdvancementType.TASK, false, false, false).addCriterion("has_level_5", WizardLevelCriterion.TriggerInstance.hasWizardLevel(5)).save(writer, ArcanusAdvancements.WIZARD_LEVEL_5.toString());
		var wizardLevel6 = Advancement.Builder.advancement().parent(wizardLevel5).display(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), Component.translatable("advancements.arcanus.arcane.wizard_level_6.title"), Component.translatable("advancements.arcanus.arcane.wizard_level_6.description"), null, AdvancementType.TASK, false, false, false).addCriterion("has_level_6", WizardLevelCriterion.TriggerInstance.hasWizardLevel(6)).save(writer, ArcanusAdvancements.WIZARD_LEVEL_6.toString());
		var wizardLevel7 = Advancement.Builder.advancement().parent(wizardLevel6).display(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), Component.translatable("advancements.arcanus.arcane.wizard_level_7.title"), Component.translatable("advancements.arcanus.arcane.wizard_level_7.description"), null, AdvancementType.TASK, false, false, false).addCriterion("has_level_7", WizardLevelCriterion.TriggerInstance.hasWizardLevel(7)).save(writer, ArcanusAdvancements.WIZARD_LEVEL_7.toString());
		var wizardLevel8 = Advancement.Builder.advancement().parent(wizardLevel7).display(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), Component.translatable("advancements.arcanus.arcane.wizard_level_8.title"), Component.translatable("advancements.arcanus.arcane.wizard_level_8.description"), null, AdvancementType.TASK, false, false, false).addCriterion("has_level_8", WizardLevelCriterion.TriggerInstance.hasWizardLevel(8)).save(writer, ArcanusAdvancements.WIZARD_LEVEL_8.toString());
		var wizardLevel9 = Advancement.Builder.advancement().parent(wizardLevel8).display(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), Component.translatable("advancements.arcanus.arcane.wizard_level_9.title"), Component.translatable("advancements.arcanus.arcane.wizard_level_9.description"), null, AdvancementType.TASK, false, false, false).addCriterion("has_level_9", WizardLevelCriterion.TriggerInstance.hasWizardLevel(9)).save(writer, ArcanusAdvancements.WIZARD_LEVEL_9.toString());
		var wizardLevel10 = Advancement.Builder.advancement().parent(wizardLevel9).display(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), Component.translatable("advancements.arcanus.arcane.wizard_level_10.title"), Component.translatable("advancements.arcanus.arcane.wizard_level_10.description"), null, AdvancementType.TASK, true, true, false).addCriterion("has_level_10", WizardLevelCriterion.TriggerInstance.hasWizardLevel(10)).save(writer, ArcanusAdvancements.WIZARD_LEVEL_10.toString());
	}
}
