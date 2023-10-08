package dev.cammiescorner.arcanuscontinuum.common.effects;

import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncStatusEffectPacket;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.server.network.ServerPlayerEntity;

public class ArcanusStatusEffect extends StatusEffect {
	public ArcanusStatusEffect(StatusEffectType type, int color) {
		super(type, color);
	}

	@Override
	public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onApplied(entity, attributes, amplifier);

		if(entity instanceof ServerPlayerEntity player && this == ArcanusStatusEffects.ANONYMITY.get())
			SyncStatusEffectPacket.sendToAll(player, ArcanusStatusEffects.ANONYMITY.get(), true);
	}

	@Override
	public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onRemoved(entity, attributes, amplifier);

		if(entity instanceof ServerPlayerEntity player && this == ArcanusStatusEffects.ANONYMITY.get())
			SyncStatusEffectPacket.sendToAll(player, ArcanusStatusEffects.ANONYMITY.get(), false);
	}
}
