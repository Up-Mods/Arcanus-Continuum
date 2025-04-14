package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.common.criterion.WizardLevelCriterion;
import net.minecraft.advancements.CriteriaTriggers;

public class ArcanusCriteriaTriggers {

	public static final WizardLevelCriterion WIZARD_LEVEL_CRITERION = new WizardLevelCriterion();

	public static void register() {
		CriteriaTriggers.register(WIZARD_LEVEL_CRITERION);
	}
}
