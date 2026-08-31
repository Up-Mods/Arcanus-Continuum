package dev.cammiescorner.arcanus.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

public class ArcanusCriteriaTriggers {
	public static final RegistryHandler<CriterionTrigger<?>> CRITERIA_TRIGGERS = RegistryHandler.create(Registries.TRIGGER_TYPE, Arcanus.MOD_ID);
}
