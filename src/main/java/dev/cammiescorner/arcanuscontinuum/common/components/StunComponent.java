package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class StunComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private int stunTimer;

	public StunComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		if(stunTimer > 0)
			setStunTimer(getStunTimer() - 1);
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		stunTimer = tag.getInt("StunTimer");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("StunTimer", stunTimer);
	}

	public int getStunTimer() {
		return stunTimer;
	}

	public void setStunTimer(int stunTimer) {
		this.stunTimer = stunTimer;
		ArcanusComponents.STUN_COMPONENT.sync(entity);
	}
}
