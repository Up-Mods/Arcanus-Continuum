package dev.cammiescorner.arcanus.common.spell_component.effects.movement;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PullSpellEffect extends SpellEffect {
	public PullSpellEffect() {
		super(
			() -> ArcanusConfig.MovementEffects.PullEffectProperties.enabled,
			() -> SpellType.MOVEMENT,
			() -> ArcanusConfig.MovementEffects.PullEffectProperties.weight,
			() -> ArcanusConfig.MovementEffects.PullEffectProperties.manaCosts(),
			() -> ArcanusConfig.MovementEffects.PullEffectProperties.coolDown,
			() -> ArcanusConfig.MovementEffects.PullEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();
			double amount = -effects.stream().filter(ArcanusSpellComponents.PULL::is).count() * ArcanusConfig.MovementEffects.PullEffectProperties.basePullStrength * potency;

			if(sourceEntity != null) {
				if(entity.equals(caster))
					entity.addDeltaMovement(sourceEntity.getLookAngle().scale(amount));
				else
					entity.addDeltaMovement(entity.position().subtract(sourceEntity.position()).normalize().scale(amount));

				entity.hurtMarked = true;
			}
		}
	}
}
