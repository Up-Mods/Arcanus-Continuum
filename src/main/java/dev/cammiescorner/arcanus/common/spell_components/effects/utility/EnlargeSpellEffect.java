package dev.cammiescorner.arcanus.common.spell_components.effects.utility;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellType;
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

public class EnlargeSpellEffect extends SpellEffect {
	public EnlargeSpellEffect() {
		super(
			() -> ArcanusConfig.UtilityEffects.EnlargeEffectProperties.enabled,
			() -> SpellType.UTILITY,
			() -> ArcanusConfig.UtilityEffects.EnlargeEffectProperties.weight,
			() -> ArcanusConfig.UtilityEffects.EnlargeEffectProperties.manaCosts(),
			() -> ArcanusConfig.UtilityEffects.EnlargeEffectProperties.coolDown,
			() -> ArcanusConfig.UtilityEffects.EnlargeEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(ArcanusSpellComponents.ENLARGE != null && target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();

			if(entity instanceof LivingEntity livingEntity) {
				MobEffectInstance shrinkEffect = livingEntity.getEffect(ArcanusMobEffects.SHRINK.holder());

				if(shrinkEffect != null) {
					int duration = shrinkEffect.getDuration();
					int amplifier = Math.max(shrinkEffect.getAmplifier() - 1, 0);

					livingEntity.removeEffect(ArcanusMobEffects.SHRINK.holder());

					if(amplifier > 0)
						livingEntity.addEffect(new MobEffectInstance(ArcanusMobEffects.SHRINK.holder(), duration, amplifier, false, true, true));
				}
				else {
					MobEffectInstance enlargeEffect = livingEntity.getEffect(ArcanusMobEffects.ENLARGE.holder());
					int amplifier = enlargeEffect != null ? Math.max(enlargeEffect.getAmplifier() + 1, 4) : 0;
					int duration = ArcanusConfig.sizeChangingIsPermanent ? -1 : (int) (ArcanusConfig.UtilityEffects.EnlargeEffectProperties.baseEffectDuration * effects.stream().filter(ArcanusSpellComponents.ENLARGE::is).count() * potency);

					livingEntity.addEffect(new MobEffectInstance(ArcanusMobEffects.ENLARGE.holder(), duration, amplifier, false, true, true));
				}
			}
		}
	}
}
