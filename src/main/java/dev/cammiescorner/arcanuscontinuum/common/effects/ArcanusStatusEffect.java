package dev.cammiescorner.arcanuscontinuum.common.effects;

import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncStatusEffectPacket;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ArcanusStatusEffect extends StatusEffect {
	public ArcanusStatusEffect(StatusEffectType type, int color) {
		super(type, color);
	}

	@Override
	public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onApplied(entity, attributes, amplifier);

		if(entity.getWorld() instanceof ServerWorld) {
			if(this == ArcanusStatusEffects.ANONYMITY.get() || this == ArcanusStatusEffects.TEMPORAL_DILATION.get() || this == ArcanusStatusEffects.MANA_WINGS.get())
				SyncStatusEffectPacket.sendToAll(entity, this, true);
		}
	}

	@Override
	public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onRemoved(entity, attributes, amplifier);

		if(entity.getWorld() instanceof ServerWorld) {
			if(this == ArcanusStatusEffects.ANONYMITY.get() || this == ArcanusStatusEffects.TEMPORAL_DILATION.get() || this == ArcanusStatusEffects.MANA_WINGS.get())
				SyncStatusEffectPacket.sendToAll(entity, this, false);
		}
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return this == ArcanusStatusEffects.TEMPORAL_DILATION.get();
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		World world = entity.getWorld();

		if(this == ArcanusStatusEffects.TEMPORAL_DILATION.get()) {
			float radius = 4;

			List<Entity> targets = world.getOtherEntities(entity, new Box(-radius, -radius, -radius, radius, radius, radius).offset(entity.getPos().add(0, entity.getHeight() / 2, 0)), target -> target.squaredDistanceTo(entity) <= radius * radius && !(target instanceof LivingEntity livingTarget && livingTarget.hasStatusEffect(ArcanusStatusEffects.TEMPORAL_DILATION.get())));
			int interval = MathHelper.clamp((amplifier + 1) * 2, 2, 10); // stacks to a maximum of 50% (5 temporal dilation effects)

			for(Entity target : targets) {
				ArcanusComponents.setSlowTime(target, true);
				ArcanusComponents.setBlockUpdates(target, world.getTime() % interval != 0);
				ArcanusComponents.setBlockUpdatesInterval(target, interval);

//				if(world.isClient()) {
//					if(target.isOnGround())
//						target.setVelocity(target.getVelocity().getX(), 0, target.getVelocity().getZ());
//
//					if(target instanceof LivingEntity livingEntity) {
//						livingEntity.handSwingProgress = livingEntity.lastHandSwingProgress;
//						livingEntity.setBodyYaw(livingEntity.prevBodyYaw);
//						livingEntity.setHeadYaw(livingEntity.prevHeadYaw);
//					}
//
//					target.setYaw(target.prevYaw);
//					target.setPitch(target.prevPitch);
//
//					if(ArcanusComponents.areUpdatesBlocked(target)) {
//						Vec3d pos = target.getPos().add(target.getVelocity().multiply((20d - interval) / 20d));
//
//						target.prevX = target.getX();
//						target.prevY = target.getY();
//						target.prevZ = target.getZ();
//
//						target.setPosition(pos);
//					}
//					else {
//						target.setPosition(target.getPos().add(target.getVelocity().multiply((20d - interval) / 20d)));
//
//						target.prevX = target.getX() - target.getVelocity().getX() * ((20d - interval) / 20d);
//						target.prevY = target.getY() - target.getVelocity().getY() * ((20d - interval) / 20d);
//						target.prevZ = target.getZ() - target.getVelocity().getZ() * ((20d - interval) / 20d);
//					}
//				}
			}

			List<Entity> targetsOutOfRange = world.getOtherEntities(entity, new Box(-radius - 3, -radius - 3, -radius - 3, radius + 3, radius + 3, radius + 3).offset(entity.getPos()), target -> !targets.contains(target) && !(target instanceof LivingEntity livingTarget && livingTarget.hasStatusEffect(ArcanusStatusEffects.TEMPORAL_DILATION.get())));
			targetsOutOfRange.forEach(target -> ArcanusComponents.setBlockUpdates(target, false));
		}
	}
}
