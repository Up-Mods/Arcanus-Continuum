package dev.cammiescorner.arcanus.common.spell_component.effects.support;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.common.registry.ArcanusMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FortifySpellEffect extends SpellEffect {
	public FortifySpellEffect() {
		super(
			() -> ArcanusConfig.SupportEffects.FortifyEffectProperties.enabled,
			() -> SpellType.SUPPORT,
			() -> ArcanusConfig.SupportEffects.FortifyEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.SupportEffects.FortifyEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			if(entityHit.getEntity() instanceof LivingEntity livingEntity)
				livingEntity.addEffect(new MobEffectInstance(ArcanusMobEffects.FORTIFY.holder(), ArcanusConfig.SupportEffects.FortifyEffectProperties.baseEffectDuration, (int) Math.min(potency - 1, 0), true, false));
		}
	}
}
