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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CounterSpellShape extends SpellShape {
	public CounterSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.CounterShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.CounterShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.CounterShapeProperties.arcanaCosts(),
			() -> ArcanusConfig.SpellShapes.CounterShapeProperties.arcanaModifier,
			() -> ArcanusConfig.SpellShapes.CounterShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.CounterShapeProperties.coolDownModifier,
			() -> ArcanusConfig.SpellShapes.CounterShapeProperties.procsOnce);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		potency += getPotencyModifier();
		LivingEntity targetEntity = castSource instanceof LivingEntity livingEntity ? livingEntity : caster;

		if(targetEntity != null) {
			ArcanusComponents.setCounterProperties(targetEntity, caster, stack, effects, spellGroups, groupIndex, ArcanusHelper.getMagicColor(caster), potency, level.getGameTime());
		}
	}
}
