package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.AreaOfEffectEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AreaOfEffectSpellShape extends SpellShape {
	public AreaOfEffectSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	public AreaOfEffectSpellShape(Weight weight, double manaCost, double manaMultiplier, int coolDown, int minLevel) {
		super(weight, manaCost, manaMultiplier, coolDown, minLevel);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		AreaOfEffectEntity areaOfEffect = ArcanusEntities.AOE.create(world);
		Entity sourceEntity = castSource != null ? castSource : caster;

		if(areaOfEffect != null && caster != null) {
			areaOfEffect.setProperties(caster.getUuid(), sourceEntity, castFrom, stack, effects, potency, spellGroups, groupIndex, Arcanus.DEFAULT_MAGIC_COLOUR);

			if(caster instanceof PlayerEntity player)
				areaOfEffect.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));

			world.spawnEntity(areaOfEffect);
		}
	}
}
