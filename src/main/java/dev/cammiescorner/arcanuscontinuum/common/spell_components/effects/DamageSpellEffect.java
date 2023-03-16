package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusDamageSource;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DamageSpellEffect extends SpellEffect {
	public DamageSpellEffect(SpellType type, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			float damage = 1.5F;

			if(entityHit.getEntity() instanceof LivingEntity livingEntity) {
				if(livingEntity.isWet() && effects.contains(ArcanusSpellComponents.ELECTRIC))
					damage *= 1.15F;

				livingEntity.timeUntilRegen = 0;
				livingEntity.damage(ArcanusDamageSource.getMagicDamage(caster), (float) (damage * effects.stream().filter(effect -> effect == ArcanusSpellComponents.DAMAGE).count() * potency));
			}
		}
	}
}
