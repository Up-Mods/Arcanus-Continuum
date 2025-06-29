package dev.cammiescorner.arcanus.common.spell_components.effects.attack;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellType;
import dev.cammiescorner.arcanus.common.registry.ArcanusMobEffects;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
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

public class StockpileSpellEffect extends SpellEffect {
	public StockpileSpellEffect() {
		super(
			() -> ArcanusConfig.AttackEffects.StockpileEffectProperties.enabled,
			() -> SpellType.ATTACK,
			() -> ArcanusConfig.AttackEffects.StockpileEffectProperties.weight,
			() -> ArcanusConfig.AttackEffects.StockpileEffectProperties.manaCosts(),
			() -> ArcanusConfig.AttackEffects.StockpileEffectProperties.coolDown,
			() -> ArcanusConfig.AttackEffects.StockpileEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();

			if(entity instanceof Player playerTarget && caster instanceof Player playerCaster && !playerCaster.canHarmPlayer(playerTarget))
				return;

			if(entity instanceof LivingEntity livingEntity)
				livingEntity.addEffect(new MobEffectInstance(ArcanusMobEffects.STOCKPILE.holder(), ArcanusConfig.AttackEffects.StockpileEffectProperties.baseEffectDuration + ArcanusConfig.AttackEffects.StockpileEffectProperties.effectDurationModifier * (int) (effects.stream().filter(ArcanusSpellComponents.STOCKPILE::is).count() * potency), 0, true, false));
		}
	}
}
