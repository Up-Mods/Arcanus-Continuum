package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanuscontinuum.common.util.EntityAwareExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExplosionSpellShape extends SpellShape {

	public ExplosionSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, World world, StaffItem staffItem, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex) {
		Explosion exp = world.createExplosion(castSource, DamageSource.CACTUS, null, castFrom, 5, effects.contains(ArcanusSpellComponents.FIRE), World.ExplosionSourceType.NONE);
		((EntityAwareExplosion) exp).getAffectedEntities().forEach(entity -> {
			for(SpellEffect effect : effects)
				effect.effect(caster, world, new EntityHitResult(entity), effects, staffItem, stack, caster.getAttributeValue(ArcanusEntityAttributes.SPELL_POTENCY));
		});

		castNext(caster, castFrom, castSource, world, staffItem, stack, effects, spellGroups, groupIndex);
	}
}
