package dev.cammiescorner.arcanus.component.entity;

import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class CastingComponent implements AutoSyncedComponent {
	private final LivingEntity entity;
	private boolean casting = false;

	public CastingComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readData(ValueInput readView) {
		casting = readView.getBooleanOr("IsCasting", false);
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.putBoolean("IsCasting", casting);
	}

	public boolean isCasting() {
		return casting;
	}

	public void setCasting(boolean casting) {
		this.casting = casting;

		ArcanusComponents.CASTING_COMPONENT.sync(entity);
	}
}
