package dev.cammiescorner.arcanus.effect;

import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.networking.clientbound.ClientboundStatusEffectPacket;
import dev.cammiescorner.arcanus.registry.ArcanusMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ArcanusStatusEffect extends MobEffect {
	public final boolean shouldSync;
	public final boolean shouldTick;

	public ArcanusStatusEffect(MobEffectCategory type, int color, boolean shouldSync, boolean shouldTick) {
		super(type, color);
		this.shouldSync = shouldSync;
		this.shouldTick = shouldTick;
	}

	public ArcanusStatusEffect(MobEffectCategory type, int color) {
		this(type, color, false, false);
	}

	@Override
	public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
		super.onEffectAdded(livingEntity, amplifier);

		if(livingEntity.level() instanceof ServerLevel level) {
			if(shouldSync)
				Network.getNetworkHandler().sendToAllClients(new ClientboundStatusEffectPacket(livingEntity.getId(), Holder.direct(this), true), level.getServer());

			if(this == ArcanusMobEffects.FLOAT.get())
				livingEntity.setNoGravity(true);
		}
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return shouldTick;
	}
}
