package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import dev.cammiescorner.arcanuscontinuum.ArcanusConfig;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.Smite;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.util.ArcanusHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SmiteSpellShape extends SpellShape {
	public SmiteSpellShape() {
		super(
			ArcanusConfig.SpellShapes.SmiteShapeProperties.enabled,
			ArcanusConfig.SpellShapes.SmiteShapeProperties.weight,
			ArcanusConfig.SpellShapes.SmiteShapeProperties.manaCost,
			ArcanusConfig.SpellShapes.SmiteShapeProperties.manaMultiplier,
			ArcanusConfig.SpellShapes.SmiteShapeProperties.coolDown,
			ArcanusConfig.SpellShapes.SmiteShapeProperties.minimumLevel,
			ArcanusConfig.SpellShapes.SmiteShapeProperties.potencyModifier
		);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		Entity sourceEntity = castSource != null ? castSource : caster;
		potency += getPotencyModifier();

		if(caster != null) {
			List<? extends Smite> list = level.getEntities(EntityTypeTest.forClass(Smite.class), entity -> caster.getUUID().equals(entity.getCasterId()));

			for(int i = 0; i < list.size() - 50; i++)
				list.get(i).kill();

			Smite smite = ArcanusEntities.SMITE.get().create(level);

			if(smite != null) {
				smite.setProperties(caster.getUUID(), sourceEntity, castFrom, stack, effects, potency);
				ArcanusHelper.copyMagicColor(smite, caster);
				level.addFreshEntity(smite);
				castNext(caster, smite.position(), smite, level, stack, spellGroups, groupIndex, potency);
			}
		}
	}
}
