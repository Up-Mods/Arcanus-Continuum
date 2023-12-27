package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.AggressorbEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.util.ArcanusHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AggressorbSpellShape extends SpellShape {
	public AggressorbSpellShape(boolean isEnabled, Weight weight, double manaCost, double manaMultiplier, int coolDown, int minLevel, double potencyModifier) {
		super(isEnabled, weight, manaCost, manaMultiplier, coolDown, minLevel, potencyModifier);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		potency += getPotencyModifier();
		double range = caster != null ? ReachEntityAttributes.getAttackRange(caster, 24) : 24;
		Entity sourceEntity = castSource != null ? castSource : caster;
		HitResult target = ArcanusHelper.raycast(sourceEntity, range, true, true);
		LivingEntity targetEntity = target.getType() == HitResult.Type.ENTITY && ((EntityHitResult) target).getEntity() instanceof LivingEntity livingEntity ? livingEntity : caster;

		if(targetEntity != null && ArcanusComponents.orbCount(targetEntity) < 8) {
			AggressorbEntity aggressorb = ArcanusEntities.AGGRESSORB.get().create(world);

			if(aggressorb != null) {
				aggressorb.setProperties(caster, targetEntity, stack, effects, spellGroups, groupIndex, Arcanus.DEFAULT_MAGIC_COLOUR, potency);
				aggressorb.setPosition(castFrom);

				if(caster instanceof PlayerEntity player)
					aggressorb.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));

				world.spawnEntity(aggressorb);
			}
		}
	}
}
