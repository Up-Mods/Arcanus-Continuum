package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WitheringSpellEffect extends SpellEffect {
	public WitheringSpellEffect(SpellType type, ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, particle, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, World world, HitResult target, List<SpellEffect> effects, StaffItem staffItem, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			if(entityHit.getEntity() instanceof LivingEntity livingEntity)
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60 * (int) (effects.stream().filter(effect -> effect == ArcanusSpellComponents.WITHERING).count() * potency), 0, true, false));
		}
	}
}
