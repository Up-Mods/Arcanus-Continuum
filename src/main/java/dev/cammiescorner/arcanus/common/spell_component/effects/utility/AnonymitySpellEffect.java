package dev.cammiescorner.arcanus.common.spell_component.effects.utility;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.common.registry.ArcanusMobEffects;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnonymitySpellEffect extends SpellEffect {
	public AnonymitySpellEffect() {
		super(
			() -> ArcanusConfig.UtilityEffects.AnonymityEffectProperties.enabled,
			() -> SpellType.UTILITY,
			() -> ArcanusConfig.UtilityEffects.AnonymityEffectProperties.weight,
			() -> ArcanusConfig.UtilityEffects.AnonymityEffectProperties.manaCosts(),
			() -> ArcanusConfig.UtilityEffects.AnonymityEffectProperties.coolDown,
			() -> ArcanusConfig.UtilityEffects.AnonymityEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			if(entityHit.getEntity() instanceof LivingEntity livingEntity)
				livingEntity.addEffect(new MobEffectInstance(ArcanusMobEffects.ANONYMITY.holder(), ArcanusConfig.UtilityEffects.AnonymityEffectProperties.baseEffectDuration * (int) (effects.stream().filter(ArcanusSpellComponents.ANONYMITY::is).count() * potency), 0, true, false));
		}
	}
}
