package dev.cammiescorner.arcanus.common.spell_component.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.entity.magic.MagicOrb;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicOrbShape extends SpellShape {
	public MagicOrbShape() {
		super(
			() -> ArcanusConfig.SpellShapes.MagicOrbShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.MagicOrbShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.MagicOrbShapeProperties.arcanaCosts(),
			() -> ArcanusConfig.SpellShapes.MagicOrbShapeProperties.arcanaModifier,
			() -> ArcanusConfig.SpellShapes.MagicOrbShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.MagicOrbShapeProperties.coolDownModifier,
			() -> ArcanusConfig.SpellShapes.MagicOrbShapeProperties.procsOnce);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		LivingEntity targetEntity = castSource instanceof LivingEntity livingEntity ? livingEntity : caster;

		if(targetEntity != null) {
			MagicOrb orb = ArcanusEntities.MAGIC_ORB.get().create(level);
			orb.setProperties(caster, targetEntity, stack, effects, spellGroups, groupIndex, potency + getPotencyModifier());
			orb.setPos(castFrom);
			level.addFreshEntity(orb);

			if(caster != null) {
				List<? extends MagicOrb> oldOrbs = level.getEntities(ArcanusEntities.MAGIC_ORB.get(), existingOrb -> existingOrb != orb && existingOrb.getCaster().getUUID().equals(caster.getUUID()));
				oldOrbs.forEach(Entity::discard);
			}
		}
	}
}
