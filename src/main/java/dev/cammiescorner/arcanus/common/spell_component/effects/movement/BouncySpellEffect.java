package dev.cammiescorner.arcanus.common.spell_component.effects.movement;

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

public class BouncySpellEffect extends SpellEffect {
	public BouncySpellEffect() {
		super(
			() -> ArcanusConfig.MovementEffects.BouncyEffectProperties.enabled,
			() -> SpellType.MOVEMENT,
			() -> ArcanusConfig.MovementEffects.BouncyEffectProperties.manaCosts(),
			() -> ArcanusConfig.MovementEffects.BouncyEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			if(entityHit.getEntity() instanceof LivingEntity livingEntity)
				livingEntity.addEffect(new MobEffectInstance(ArcanusMobEffects.BOUNCY.holder(), (int) (ArcanusConfig.MovementEffects.BouncyEffectProperties.baseEffectDuration * potency), 0, true, false));
		}
	}
}
