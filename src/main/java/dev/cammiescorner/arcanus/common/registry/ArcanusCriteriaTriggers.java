package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.criterion.WizardLevelCriterion;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

public class ArcanusCriteriaTriggers {
	public static final RegistryHandler<CriterionTrigger<?>> CRITERIA_TRIGGERS = RegistryHandler.create(Registries.TRIGGER_TYPE, Arcanus.MOD_ID);

	public static final RegistrySupplier<WizardLevelCriterion> WIZARD_LEVEL_CRITERION = CRITERIA_TRIGGERS.register("wizard_level", WizardLevelCriterion::new);
}
