package dev.cammiescorner.arcanuscontinuum.common.components.entity;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PortalCoolDownComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final PlayerEntity player;
	private int coolDown = 0;

	public PortalCoolDownComponent(PlayerEntity player) {
		this.player = player;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		coolDown = tag.getInt("CoolDown");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("CoolDown", coolDown);
	}

	@Override
	public void serverTick() {
		if(coolDown > 0 && --coolDown == 0)
			ArcanusComponents.PORTAL_COOL_DOWN_COMPONENT.sync(player);
	}

	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
		ArcanusComponents.PORTAL_COOL_DOWN_COMPONENT.sync(player);
	}

	public boolean hasCoolDown() {
		return coolDown > 0;
	}
}
