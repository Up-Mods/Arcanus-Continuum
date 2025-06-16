package dev.cammiescorner.arcanus.common.spell_components.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellGroup;
import dev.cammiescorner.arcanus.api.spells.SpellShape;
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
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.manaCost,
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.manaMultiplier,
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.coolDown,
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.minimumLevel,
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.SelfShapeProperties.procsOnce
		);
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
