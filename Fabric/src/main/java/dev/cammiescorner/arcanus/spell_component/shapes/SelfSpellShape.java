package dev.cammiescorner.arcanus.spell_component.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class SelfSpellShape extends SpellShape {
	public SelfSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.arcanaCosts(),
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.arcanaModifier,
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.coolDownModifier,
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.activatesOnce);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		potency += getPotencyModifier();

		HitResult hit = new EntityHitResult(caster);

		for(SpellEffect effect : new HashSet<>(effects))
			effect.effect(caster, caster, level, hit, effects, stack, potency);

		castNext(caster, hit.getLocation(), caster, level, stack, spellGroups, groupIndex, potency);
	}
}
