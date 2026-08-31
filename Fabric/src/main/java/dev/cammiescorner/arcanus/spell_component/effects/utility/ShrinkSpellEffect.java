package dev.cammiescorner.arcanus.spell_component.effects.utility;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.registry.ArcanusMobEffects;
import dev.cammiescorner.arcanus.registry.ArcanusSpellComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShrinkSpellEffect extends SpellEffect {
	public ShrinkSpellEffect() {
		super(
			() -> ArcanusConfig.UtilityEffects.ShrinkEffectProperties.enabled,
			() -> SpellType.UTILITY,
			() -> ArcanusConfig.UtilityEffects.ShrinkEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.UtilityEffects.ShrinkEffectProperties.activatesOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(ArcanusSpellComponents.SHRINK != null && target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();

			if(entity instanceof LivingEntity livingEntity) {
				MobEffectInstance enlargeEffect = livingEntity.getEffect(ArcanusMobEffects.ENLARGE.holder());

				if(enlargeEffect != null) {
					int duration = enlargeEffect.getDuration();
					int amplifier = Math.max(enlargeEffect.getAmplifier() - 1, 0);

					livingEntity.removeEffect(ArcanusMobEffects.ENLARGE.holder());

					if(amplifier > 0)
						livingEntity.addEffect(new MobEffectInstance(ArcanusMobEffects.ENLARGE.holder(), duration, amplifier, false, false, true));
				}
				else {
					MobEffectInstance shrinkEffect = livingEntity.getEffect(ArcanusMobEffects.SHRINK.holder());
					int amplifier = shrinkEffect != null ? Math.min(shrinkEffect.getAmplifier() + 1, 3) : 0;
					int duration = ArcanusConfig.sizeChangingIsPermanent ? -1 : (int) (ArcanusConfig.UtilityEffects.ShrinkEffectProperties.baseEffectDuration * potency);

					livingEntity.addEffect(new MobEffectInstance(ArcanusMobEffects.SHRINK.holder(), duration, amplifier, false, false, true));
				}
			}
		}
	}
}
