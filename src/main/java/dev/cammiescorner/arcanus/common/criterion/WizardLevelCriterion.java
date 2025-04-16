package dev.cammiescorner.arcanus.common.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class WizardLevelCriterion extends SimpleCriterionTrigger<WizardLevelCriterion.TriggerInstance> {
	public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.fieldOf("level").forGetter(TriggerInstance::getWizardLevel)
	).apply(instance, TriggerInstance::new));

	public void trigger(ServerPlayer player) {
		this.trigger(player, triggerInstance -> ArcanusComponents.WIZARD_LEVEL_COMPONENT.get(player).getLevel() >= triggerInstance.level);
	}

	@Override
	public Codec<TriggerInstance> codec() {
		return CODEC;
	}

	public static class TriggerInstance implements SimpleInstance {
		private final int level;

		public TriggerInstance(int level) {
			this.level = level;
		}

		public int getWizardLevel() {
			return level;
		}

		public static Criterion<?> hasWizardLevel(int level) {
			return new Criterion<>(null, new TriggerInstance(level));
		}

		@Override
		public Optional<ContextAwarePredicate> player() {
			return Optional.empty();
		}
	}
}
