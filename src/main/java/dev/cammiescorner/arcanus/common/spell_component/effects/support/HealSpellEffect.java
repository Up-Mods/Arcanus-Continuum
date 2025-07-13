package dev.cammiescorner.arcanus.common.spell_component.effects.support;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HealSpellEffect extends SpellEffect {
	public HealSpellEffect() {
		super(
			() -> ArcanusConfig.SupportEffects.HealEffectProperties.enabled,
			() -> SpellType.SUPPORT,
			() -> ArcanusConfig.SupportEffects.HealEffectProperties.manaCosts(),
			() -> ArcanusConfig.SupportEffects.HealEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY && target instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof LivingEntity livingEntity)
			livingEntity.heal((float) (ArcanusConfig.SupportEffects.HealEffectProperties.baseHealAmount * potency));
	}

}
