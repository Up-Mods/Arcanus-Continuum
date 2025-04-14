package dev.cammiescorner.arcanus.common.spell_components.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellGroup;
import dev.cammiescorner.arcanus.api.spells.SpellShape;
import dev.cammiescorner.arcanus.common.entities.magic.Missile;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class MissileSpellShape extends SpellShape {
	public MissileSpellShape() {
		super(
			ArcanusConfig.SpellShapes.MissileShapeProperties.enabled,
			ArcanusConfig.SpellShapes.MissileShapeProperties.weight,
			ArcanusConfig.SpellShapes.MissileShapeProperties.manaCost,
			ArcanusConfig.SpellShapes.MissileShapeProperties.manaMultiplier,
			ArcanusConfig.SpellShapes.MissileShapeProperties.coolDown,
			ArcanusConfig.SpellShapes.MissileShapeProperties.minimumLevel,
			ArcanusConfig.SpellShapes.MissileShapeProperties.potencyModifier,
			ArcanusConfig.SpellShapes.MissileShapeProperties.procsOnce
		);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		float projectileSpeed = ArcanusConfig.SpellShapes.MissileShapeProperties.projectileSpeed;
		potency += getPotencyModifier();

		if(caster != null) {
			List<? extends Missile> list = level.getEntities(EntityTypeTest.forClass(Missile.class), entity -> caster.equals(entity.getOwner()));
			Entity sourceEntity = castSource != null ? castSource : caster;
			HitResult target = ArcanusHelper.raycast(sourceEntity, 4.5, true, true);

			for(int i = 0; i < list.size() - 20; i++)
				list.get(i).kill();

			if(projectileSpeed > 3f && target instanceof EntityHitResult hitResult) {
				for(SpellEffect effect : new HashSet<>(effects))
					effect.effect(caster, sourceEntity, level, target, effects, stack, potency);

				SpellShape.castNext(caster, target.getLocation(), hitResult.getEntity(), level, stack, spellGroups, groupIndex, potency);
				level.playSound(hitResult.getEntity(), hitResult.getEntity().blockPosition(), SoundEvents.ARROW_HIT, SoundSource.NEUTRAL, 1f, 1.2f / (level.random.nextFloat() * 0.2f + 0.9f));
			}
			else {
				Missile projectile = ArcanusEntities.MISSILE.get().create(level);

				if(projectile != null) {
					projectile.setProperties(caster, castSource, stack, effects, spellGroups, groupIndex, potency);
					level.addFreshEntity(projectile);
				}
			}
		}
	}
}
