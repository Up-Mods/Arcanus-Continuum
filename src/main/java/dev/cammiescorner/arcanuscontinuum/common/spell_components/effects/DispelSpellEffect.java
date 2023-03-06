package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DispelSpellEffect extends SpellEffect {
	public DispelSpellEffect(SpellType type, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			if(entityHit.getEntity() instanceof LivingEntity livingEntity) {
				List<StatusEffect> statusEffects = livingEntity.getStatusEffects().stream().map(StatusEffectInstance::getEffectType).filter(effect -> !effect.isBeneficial()).toList();
				long dispelCount = effects.stream().filter(effect -> effect == ArcanusSpellComponents.DISPEL).count();

				for(int i = 0; i < Math.min(statusEffects.size(), (int) (dispelCount * 2 * potency)); i++)
					livingEntity.removeStatusEffect(statusEffects.get(i));
			}
		}
	}
}
