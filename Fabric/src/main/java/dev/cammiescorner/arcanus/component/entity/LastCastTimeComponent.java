package dev.cammiescorner.arcanus.component.entity;

import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class LastCastTimeComponent implements AutoSyncedComponent {
	private final LivingEntity entity;
	private long lastCastTime = 0;

	public LastCastTimeComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readData(ValueInput readView) {
		lastCastTime = readView.getLongOr("LastCastTime", 0L);
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.putLong("LastCastTime", lastCastTime);
	}

	public long getLastCastTime() {
		return lastCastTime;
	}

	public void setLastCastTime(long lastCastTime) {
		this.lastCastTime = lastCastTime;
		ArcanusComponents.LAST_CAST_TIME_COMPONENT.sync(entity);
	}
}
