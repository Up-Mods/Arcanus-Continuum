package dev.cammiescorner.arcanus.common.spell_components.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spells.components.SpellShape;
import dev.cammiescorner.arcanus.common.entities.magic.Lob;
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

public class LobSpellShape extends SpellShape {
	public LobSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.LobShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.LobShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.LobShapeProperties.manaCosts(),
			() -> ArcanusConfig.SpellShapes.LobShapeProperties.manaMultiplier,
			() -> ArcanusConfig.SpellShapes.LobShapeProperties.coolDown,
			() -> ArcanusConfig.SpellShapes.LobShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.LobShapeProperties.procsOnce
		);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		float projectileSpeed = ArcanusConfig.SpellShapes.LobShapeProperties.projectileSpeed;
		potency += getPotencyModifier();

		if(caster != null) {
			List<? extends Lob> list = level.getEntities(EntityTypeTest.forClass(Lob.class), entity -> caster.equals(entity.getOwner()));
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
				Lob lob = ArcanusEntities.LOB.get().create(level);

				if(lob != null) {
					lob.setProperties(caster, castSource, stack, effects, spellGroups, groupIndex, potency);
					level.addFreshEntity(lob);
				}
			}
		}
	}
}
