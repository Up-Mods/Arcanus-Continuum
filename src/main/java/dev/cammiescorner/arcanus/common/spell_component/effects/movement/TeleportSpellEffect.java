package dev.cammiescorner.arcanus.common.spell_component.effects.movement;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeleportSpellEffect extends SpellEffect {
	public TeleportSpellEffect() {
		super(
			() -> ArcanusConfig.MovementEffects.TeleportEffectProperties.enabled,
			() -> SpellType.MOVEMENT,
			() -> ArcanusConfig.MovementEffects.TeleportEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.MovementEffects.TeleportEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(caster != null) {
			Vec3 pos = target.getLocation();
			double maxDistance = ArcanusConfig.MovementEffects.TeleportEffectProperties.baseTeleportDistance * potency;

			if(target.getType() == HitResult.Type.BLOCK) {
				BlockHitResult blockHit = (BlockHitResult) target;
				pos = pos.add(blockHit.getDirection().getStepX() * 0.5, blockHit.getDirection() == Direction.DOWN ? -caster.getBbHeight() : 0, blockHit.getDirection().getStepZ() * 0.5);
			}

			if(caster.position().distanceTo(pos) <= maxDistance) {
				level.broadcastEntityEvent(caster, EntityEvent.TELEPORT);

				if(caster.isPassenger())
					caster.dismountTo(pos.x(), pos.y(), pos.z());
				else
					caster.teleportTo(pos.x(), pos.y(), pos.z());

				level.broadcastEntityEvent(caster, EntityEvent.TELEPORT);
			}
		}
	}
}
