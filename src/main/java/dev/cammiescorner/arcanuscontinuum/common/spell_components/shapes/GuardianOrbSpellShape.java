package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.GuardianOrbEntity;
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

public class GuardianOrbSpellShape extends SpellShape {
	public GuardianOrbSpellShape(boolean isEnabled, Weight weight, double manaCost, double manaMultiplier, int coolDown, int minLevel, double potencyModifier) {
		super(isEnabled, weight, manaCost, manaMultiplier, coolDown, minLevel, potencyModifier);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		potency += getPotencyModifier();
		double range = caster != null ? ReachEntityAttributes.getAttackRange(caster, 24) : 24;
		Entity sourceEntity = castSource != null ? castSource : caster;
		HitResult target = ArcanusHelper.raycast(sourceEntity, range, true, true);
		GuardianOrbEntity guardianOrb = ArcanusEntities.GUARDIAN_ORB.get().create(world);
		LivingEntity targetEntity = target.getType() == HitResult.Type.ENTITY && ((EntityHitResult) target).getEntity() instanceof LivingEntity livingEntity ? livingEntity : caster;

		if(targetEntity != null && guardianOrb != null) {
			List<? extends GuardianOrbEntity> oldOrbs = world.getEntitiesByType(ArcanusEntities.GUARDIAN_ORB.get(), t -> caster != null && t.getCaster().getUuid() == caster.getUuid());
			oldOrbs.forEach(Entity::kill);

			guardianOrb.setProperties(caster, targetEntity, stack, effects, spellGroups, groupIndex, Arcanus.DEFAULT_MAGIC_COLOUR, potency * 0.85);
			guardianOrb.setPosition(castFrom);

			if(caster instanceof PlayerEntity player)
				guardianOrb.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));

			world.spawnEntity(guardianOrb);
		}
	}
}
