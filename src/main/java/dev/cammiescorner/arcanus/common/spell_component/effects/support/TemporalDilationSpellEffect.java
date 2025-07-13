package dev.cammiescorner.arcanus.common.spell_component.effects.support;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.common.entity.magic.TemporalDilationField;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
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
			() -> ArcanusConfig.SupportEffects.TemporalDilationEffectProperties.manaCosts(),
			() -> ArcanusConfig.SupportEffects.TemporalDilationEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		Entity castSource = sourceEntity != null ? sourceEntity : caster;

		if(!level.isClientSide() && castSource != null) {
			TemporalDilationField dilationField = ArcanusEntities.TEMPORAL_DILATION_FIELD.get().create(level);

			if(dilationField != null) {
				dilationField.extendMaxAge((int) (20 * potency));
				dilationField.setPos(target.getLocation().add(0, -4.5, 0));
				ArcanusHelper.copyMagicColor(dilationField, caster);
				level.addFreshEntity(dilationField);
			}
		}
	}
}
