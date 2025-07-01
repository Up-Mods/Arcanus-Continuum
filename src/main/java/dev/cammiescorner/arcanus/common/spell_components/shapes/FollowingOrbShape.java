package dev.cammiescorner.arcanus.common.spell_components.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spells.components.SpellShape;
import dev.cammiescorner.arcanus.common.entities.magic.FollowingOrb;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FollowingOrbShape extends SpellShape {
	public FollowingOrbShape() {
		super(
			() -> ArcanusConfig.SpellShapes.FollowingOrbShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.FollowingOrbShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.FollowingOrbShapeProperties.manaCosts(),
			() -> ArcanusConfig.SpellShapes.FollowingOrbShapeProperties.manaMultiplier,
			() -> ArcanusConfig.SpellShapes.FollowingOrbShapeProperties.coolDown,
			() -> ArcanusConfig.SpellShapes.FollowingOrbShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.FollowingOrbShapeProperties.procsOnce
		);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		LivingEntity targetEntity = castSource instanceof LivingEntity livingEntity ? livingEntity : caster;

		if(targetEntity != null) {
			FollowingOrb orb = ArcanusEntities.FOLLOWING_ORB.get().create(level);
			orb.setProperties(caster, targetEntity, stack, effects, spellGroups, groupIndex, potency + getPotencyModifier());
			orb.setPos(castFrom);
			level.addFreshEntity(orb);

			if(caster != null) {
				List<? extends FollowingOrb> oldOrbs = level.getEntities(ArcanusEntities.FOLLOWING_ORB.get(), existingOrb -> existingOrb != orb && existingOrb.getCaster().getUUID().equals(caster.getUUID()));
				oldOrbs.forEach(Entity::discard);
			}
		}
	}
}
