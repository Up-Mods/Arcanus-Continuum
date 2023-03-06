package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class SelfSpellShape extends SpellShape {
	public SelfSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	public SelfSpellShape(Weight weight, double manaCost, double manaMultiplier, int coolDown, int minLevel) {
		super(weight, manaCost, manaMultiplier, coolDown, minLevel);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		HitResult hit = new EntityHitResult(caster);

		for(SpellEffect effect : new HashSet<>(effects))
			effect.effect(caster, caster, world, hit, effects, stack, potency);

		castNext(caster, hit.getPos(), caster, world, stack, spellGroups, groupIndex, potency);
	}
}
