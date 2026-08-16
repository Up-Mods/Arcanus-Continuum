package dev.cammiescorner.arcanus.common.spell_component.effects.attack;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.common.registry.ArcanusMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscombobulateSpellEffect extends SpellEffect {
	public DiscombobulateSpellEffect() {
		super(
			() -> ArcanusConfig.AttackEffects.DiscombobulateEffectProperties.enabled,
			() -> SpellType.ATTACK,
			() -> ArcanusConfig.AttackEffects.DiscombobulateEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.AttackEffects.DiscombobulateEffectProperties.activatesOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();

			if(entity instanceof Player playerTarget && caster instanceof Player playerCaster && !playerCaster.canHarmPlayer(playerTarget)) {
				return;
			}

			if(entity instanceof LivingEntity livingEntity && livingEntity.arcanus$canBeTargeted()) {
				livingEntity.addEffect(new MobEffectInstance(ArcanusMobEffects.DISCOMBOBULATE.holder(), ArcanusConfig.AttackEffects.DiscombobulateEffectProperties.baseEffectDuration + (int) (ArcanusConfig.AttackEffects.DiscombobulateEffectProperties.effectDurationModifier * potency), 0, true, false));
			}
		}
	}
}
