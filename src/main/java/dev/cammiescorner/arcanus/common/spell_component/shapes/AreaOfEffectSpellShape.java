package dev.cammiescorner.arcanus.common.spell_component.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.entity.magic.AreaOfEffect;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AreaOfEffectSpellShape extends SpellShape {
	public AreaOfEffectSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.AOEShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.AOEShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.AOEShapeProperties.manaCosts(),
			() -> ArcanusConfig.SpellShapes.AOEShapeProperties.manaMultiplier,
			() -> ArcanusConfig.SpellShapes.AOEShapeProperties.coolDown,
			() -> ArcanusConfig.SpellShapes.AOEShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.AOEShapeProperties.procsOnce
		);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		potency += getPotencyModifier();

		if(caster != null) {
			List<? extends AreaOfEffect> list = level.getEntities(EntityTypeTest.forClass(AreaOfEffect.class), entity -> caster.getUUID().equals(entity.getCasterId()));
			AreaOfEffect areaOfEffect = ArcanusEntities.AOE.get().create(level);

			for(int i = 0; i < list.size() - 20; i++)
				list.get(i).kill();

			if(areaOfEffect != null) {
				areaOfEffect.setProperties(caster.getUUID(), castFrom, stack, effects, potency, spellGroups, groupIndex);
				ArcanusHelper.copyMagicColor(areaOfEffect, caster);
				level.addFreshEntity(areaOfEffect);
			}
		}
	}
}
