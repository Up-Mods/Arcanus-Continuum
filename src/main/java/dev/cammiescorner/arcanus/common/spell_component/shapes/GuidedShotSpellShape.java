package dev.cammiescorner.arcanus.common.spell_component.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.entity.magic.GuidedShot;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GuidedShotSpellShape extends SpellShape {
	public GuidedShotSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.GuidedShotShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.GuidedShotShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.GuidedShotShapeProperties.arcanaCosts(),
			() -> ArcanusConfig.SpellShapes.GuidedShotShapeProperties.arcanaModifier,
			() -> ArcanusConfig.SpellShapes.GuidedShotShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.GuidedShotShapeProperties.coolDownModifier,
			() -> ArcanusConfig.SpellShapes.GuidedShotShapeProperties.procsOnce);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		GuidedShot guidedShot = ArcanusEntities.GUIDED_SHOT.get().create(level);
		potency += getPotencyModifier();

		if(guidedShot != null) {
			guidedShot.setPos(castFrom);
			guidedShot.setProperties(caster, stack, effects, spellGroups, groupIndex, potency);
			level.addFreshEntity(guidedShot);
		}
	}
}
