package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.util.ArcanusHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class TouchSpellShape extends SpellShape {
	public TouchSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, StaffItem staffItem, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex) {
		double range = ReachEntityAttributes.getAttackRange(caster, caster instanceof PlayerEntity player && player.isCreative() ? 5 : 4.5);
		Entity sourceEntity = castSource != null ? castSource : caster;
		HitResult target = ArcanusHelper.raycast(sourceEntity, range, true, true);

		if(target.getType() != HitResult.Type.MISS) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(caster, world, target, effects, staffItem, stack, caster.getAttributeValue(ArcanusEntityAttributes.SPELL_POTENCY));
		}

		Entity targetEntity = target.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) target).getEntity() : castSource;

		castNext(caster, target.getPos(), targetEntity, world, staffItem, stack, effects, spellGroups, groupIndex);
	}
}
