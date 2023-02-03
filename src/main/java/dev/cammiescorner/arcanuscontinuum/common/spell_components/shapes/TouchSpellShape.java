package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
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

import java.util.List;

public class TouchSpellShape extends SpellShape {
	public TouchSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, World world, StaffItem staffItem, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex) {
		double range = caster.getAttributeValue(ReachEntityAttributes.ATTACK_RANGE);

		Entity sourceEntity = castSource != null ? castSource : caster;
		HitResult target = sourceEntity.raycast(range, 1.0F, true); //FIXME custom raycast

		if(target.getType() != HitResult.Type.MISS) {
			for(SpellEffect effect : effects)
				effect.effect(caster, world, target, staffItem, stack);
		}

		Entity targetEntity = target.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) target).getEntity() : castSource;

		castNext(caster, target.getPos(), targetEntity, world, staffItem, stack, effects, spellGroups, groupIndex);
	}
}
