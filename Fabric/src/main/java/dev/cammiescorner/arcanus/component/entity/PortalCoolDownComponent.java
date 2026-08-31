package dev.cammiescorner.arcanus.component.entity;

import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PortalCoolDownComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final Entity entity;
	private int coolDown = 0;

	public PortalCoolDownComponent(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void readData(ValueInput readView) {
		coolDown = readView.getIntOr("CoolDown", 0);
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.putInt("CoolDown", coolDown);
	}

	@Override
	public void serverTick() {
		if(coolDown > 0 && --coolDown == 0)
			ArcanusComponents.PORTAL_COOL_DOWN_COMPONENT.sync(entity);
	}

	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
		ArcanusComponents.PORTAL_COOL_DOWN_COMPONENT.sync(entity);
	}

	public boolean hasCoolDown() {
		return coolDown > 0;
	}
}
