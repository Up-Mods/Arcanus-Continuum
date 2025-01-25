package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import dev.cammiescorner.arcanuscontinuum.ArcanusConfig;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.GuidedShot;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
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
			ArcanusConfig.SpellShapes.GuidedShotShapeProperties.enabled,
			ArcanusConfig.SpellShapes.GuidedShotShapeProperties.weight,
			ArcanusConfig.SpellShapes.GuidedShotShapeProperties.manaCost,
			ArcanusConfig.SpellShapes.GuidedShotShapeProperties.manaMultiplier,
			ArcanusConfig.SpellShapes.GuidedShotShapeProperties.coolDown,
			ArcanusConfig.SpellShapes.GuidedShotShapeProperties.minimumLevel,
			ArcanusConfig.SpellShapes.GuidedShotShapeProperties.potencyModifier,
			ArcanusConfig.SpellShapes.GuidedShotShapeProperties.procsOnce
		);
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
