package dev.cammiescorner.arcanus.component.entity;

import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class StunComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private int stunTimer;

	public StunComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		if(stunTimer > 0) {
			setStunTimer(getStunTimer() - 1);

			if(entity instanceof Player player)
				player.resetAttackStrengthTicker();
		}
	}

	@Override
	public void readData(ValueInput readView) {
		stunTimer = readView.getIntOr("StunTimer", 0);
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.putInt("StunTimer", stunTimer);
	}

	public int getStunTimer() {
		return stunTimer;
	}

	public void setStunTimer(int stunTimer) {
		this.stunTimer = stunTimer;
		ArcanusComponents.STUN_COMPONENT.sync(entity);
	}
}
