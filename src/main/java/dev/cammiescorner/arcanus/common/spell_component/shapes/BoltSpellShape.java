package dev.cammiescorner.arcanus.common.spell_component.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class BoltSpellShape extends SpellShape {
	public BoltSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.BoltShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.BoltShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.BoltShapeProperties.arcanaCosts(),
			() -> ArcanusConfig.SpellShapes.BoltShapeProperties.arcanaModifier,
			() -> ArcanusConfig.SpellShapes.BoltShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.BoltShapeProperties.coolDownModifier,
			() -> ArcanusConfig.SpellShapes.BoltShapeProperties.activatesOnce);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		potency += getPotencyModifier();
		Entity sourceEntity = castSource != null ? castSource : caster;
		double range = ArcanusConfig.SpellShapes.BoltShapeProperties.range;

		if(sourceEntity instanceof LivingEntity livingEntity) {
			HitResult target = ArcanusHelper.raycast(sourceEntity, range, true, false);
			List<Entity> entityTargets = level.getEntities(sourceEntity, new AABB(target.getLocation(), target.getLocation()).inflate(2));

			ArcanusComponents.setBoltPos(livingEntity, target.getLocation());
			ArcanusComponents.setShouldRenderBolt(livingEntity, true);
			ArcanusComponents.setBoltAge(livingEntity, 0);

			if(target.getType() == HitResult.Type.BLOCK) {
				for(SpellEffect effect : new HashSet<>(effects))
					effect.effect(caster, sourceEntity, level, target, effects, stack, potency);
			}

			for(Entity entityTarget : entityTargets) {
				for(SpellEffect effect : new HashSet<>(effects))
					effect.effect(caster, sourceEntity, level, new EntityHitResult(entityTarget), effects, stack, potency);
			}

			castNext(caster, target.getLocation(), null, level, stack, spellGroups, groupIndex, potency);
		}
	}
}
