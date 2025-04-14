package dev.cammiescorner.arcanus.common.spell_components.effects.movement;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellType;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpeedSpellEffect extends SpellEffect {
	public SpeedSpellEffect() {
		super(
			ArcanusConfig.MovementEffects.SpeedEffectProperties.enabled,
			SpellType.MOVEMENT,
			ArcanusConfig.MovementEffects.SpeedEffectProperties.weight,
			ArcanusConfig.MovementEffects.SpeedEffectProperties.manaCost,
			ArcanusConfig.MovementEffects.SpeedEffectProperties.coolDown,
			ArcanusConfig.MovementEffects.SpeedEffectProperties.minimumLevel,
			ArcanusConfig.MovementEffects.SpeedEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			if(entityHit.getEntity() instanceof LivingEntity livingEntity)
				livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, ArcanusConfig.MovementEffects.SpeedEffectProperties.baseEffectDuration, (int) ((effects.stream().filter(ArcanusSpellComponents.SPEED::is).count() - 1) * potency), true, false));
		}
	}
}
