package dev.cammiescorner.arcanus.common.spell_components.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellGroup;
import dev.cammiescorner.arcanus.api.spells.SpellShape;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class TouchSpellShape extends SpellShape {
	public TouchSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.TouchShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.TouchShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.TouchShapeProperties.manaCost,
			() -> ArcanusConfig.SpellShapes.TouchShapeProperties.manaMultiplier,
			() -> ArcanusConfig.SpellShapes.TouchShapeProperties.coolDown,
			() -> ArcanusConfig.SpellShapes.TouchShapeProperties.minimumLevel,
			() -> ArcanusConfig.SpellShapes.TouchShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.TouchShapeProperties.procsOnce
		);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		potency += getPotencyModifier();
		double range = caster != null ? caster.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).getValue() : 4.5;
		Entity sourceEntity = castSource != null ? castSource : caster;
		HitResult target = ArcanusHelper.raycast(sourceEntity, range, true, true);

		if(target.getType() != HitResult.Type.MISS)
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(caster, sourceEntity, level, target, effects, stack, potency);

		Entity targetEntity = target.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) target).getEntity() : castSource;
		castNext(caster, target.getLocation(), targetEntity, level, stack, spellGroups, groupIndex, potency);
	}
}
