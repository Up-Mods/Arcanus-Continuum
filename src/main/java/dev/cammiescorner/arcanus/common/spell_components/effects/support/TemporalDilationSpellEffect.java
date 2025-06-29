package dev.cammiescorner.arcanus.common.spell_components.effects.support;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellType;
import dev.cammiescorner.arcanus.common.entities.magic.TemporalDilationField;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TemporalDilationSpellEffect extends SpellEffect {
	public TemporalDilationSpellEffect() {
		super(
			() -> ArcanusConfig.SupportEffects.TemporalDilationEffectProperties.enabled,
			() -> SpellType.SUPPORT,
			() -> ArcanusConfig.SupportEffects.TemporalDilationEffectProperties.weight,
			() -> ArcanusConfig.SupportEffects.TemporalDilationEffectProperties.manaCosts(),
			() -> ArcanusConfig.SupportEffects.TemporalDilationEffectProperties.coolDown,
			() -> ArcanusConfig.SupportEffects.TemporalDilationEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		Entity castSource = sourceEntity != null ? sourceEntity : caster;

		if(!level.isClientSide() && castSource != null) {
			TemporalDilationField dilationField = ArcanusEntities.TEMPORAL_DILATION_FIELD.get().create(level);
			double count = (effects.stream().filter(ArcanusSpellComponents.TEMPORAL_DILATION::is).count() - 1) * potency;

			if(dilationField != null) {
				dilationField.extendMaxAge((int) count * 20);
				dilationField.setPos(target.getLocation().add(0, -4.5, 0));
				ArcanusHelper.copyMagicColor(dilationField, caster);
				level.addFreshEntity(dilationField);
			}
		}
	}
}
