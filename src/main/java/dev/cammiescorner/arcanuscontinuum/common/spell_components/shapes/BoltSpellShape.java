package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.util.ArcanusHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

public class BoltSpellShape extends SpellShape {
	private static final double RANGE_MODIFIER = 2D;
	private static final double MAX_ANGLE_DIFF = Math.toRadians(5);

	public BoltSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	public BoltSpellShape(Weight weight, double manaCost, double manaMultiplier, int coolDown, int minLevel) {
		super(weight, manaCost, manaMultiplier, coolDown, minLevel);
	}

	@Override
	public void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		double range = ReachEntityAttributes.getAttackRange(caster, caster instanceof PlayerEntity player && player.isCreative() ? 5 : 4.5) * RANGE_MODIFIER;
		Entity sourceEntity = castSource != null ? castSource : caster;
		Box box = new Box(castFrom.add(-range, -range, -range), castFrom.add(range, range, range));
		List<Entity> affectedEntities = world.getOtherEntities(castSource, box);

		Predicate<Entity> predicate = entity -> {
			Vec3d look = caster.getRotationVector();
			Vec3d direction = entity.getPos().subtract(castFrom);
			double angle = Math.acos(look.dotProduct(direction) / (look.length() * direction.length()));
			return angle > MAX_ANGLE_DIFF;
		};

		Entity hit = getClosestEntity(affectedEntities, range, castFrom, castSource == caster ? predicate : entity -> true);

		if(hit != null)
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(caster, sourceEntity, world, new EntityHitResult(hit), effects, stack, potency);
		else {
			HitResult target = ArcanusHelper.raycast(sourceEntity, range, false, true);

			if(target.getType() == HitResult.Type.BLOCK)
				for(SpellEffect effect : new HashSet<>(effects))
					effect.effect(caster, sourceEntity, world, target, effects, stack, potency);
		}

		castNext(caster, hit != null ? hit.getPos() : castFrom, castSource, world, stack, spellGroups, groupIndex, potency);
	}

	@Nullable
	private static Entity getClosestEntity(List<Entity> entityList, double range, Vec3d pos, Predicate<Entity> predicate) {
		double d = -1.0;
		Entity value = null;

		for(Entity entity : entityList) {
			if(predicate.test(entity)) {
				double e = entity.getPos().distanceTo(pos);

				if(e <= range && (d == -1.0 || e < d)) {
					d = e;
					value = entity;
				}
			}
		}

		return value;
	}
}
