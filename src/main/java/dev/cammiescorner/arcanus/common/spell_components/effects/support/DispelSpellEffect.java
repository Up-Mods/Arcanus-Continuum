package dev.cammiescorner.arcanus.common.spell_components.effects.support;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellType;
import dev.cammiescorner.arcanus.common.data.ArcanusEntityTags;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DispelSpellEffect extends SpellEffect {
	public DispelSpellEffect() {
		super(
			() -> ArcanusConfig.SupportEffects.DispelEffectProperties.enabled,
			() -> SpellType.SUPPORT,
			() -> ArcanusConfig.SupportEffects.DispelEffectProperties.weight,
			() -> ArcanusConfig.SupportEffects.DispelEffectProperties.manaCosts(),
			() -> ArcanusConfig.SupportEffects.DispelEffectProperties.coolDown,
			() -> ArcanusConfig.SupportEffects.DispelEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			if(entityHit.getEntity() instanceof LivingEntity livingEntity) {
				List<Holder<MobEffect>> statusEffects = livingEntity.getActiveEffects().stream().map(MobEffectInstance::getEffect).filter(effect -> !effect.value().isBeneficial()).toList();
				long dispelCount = effects.stream().filter(ArcanusSpellComponents.DISPEL::is).count();

				for(int i = 0; i < Math.min(statusEffects.size(), (int) (dispelCount * 2 * potency)); i++)
					livingEntity.removeEffect(statusEffects.get(i));

				if(ArcanusComponents.isCounterActive(livingEntity))
					ArcanusComponents.removeCounter(livingEntity);
			}

			if(entityHit.getEntity().getType().is(ArcanusEntityTags.DISPELLABLE))
				entityHit.getEntity().kill();
		}
	}
}
