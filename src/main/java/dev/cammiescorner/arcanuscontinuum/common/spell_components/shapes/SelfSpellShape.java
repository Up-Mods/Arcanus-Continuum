package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class SelfSpellShape extends SpellShape {
	public SelfSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, World world, StaffItem staffItem, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex) {
		HitResult hit = new EntityHitResult(caster);

		for(SpellEffect effect : new HashSet<>(effects))
			effect.effect(caster, world, hit, effects, staffItem, stack, caster.getAttributeValue(ArcanusEntityAttributes.SPELL_POTENCY));

		castNext(caster, hit.getPos(), caster, world, staffItem, stack, effects, spellGroups, groupIndex);
	}
}
