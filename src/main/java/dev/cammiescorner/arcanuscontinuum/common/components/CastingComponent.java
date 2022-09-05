package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class CastingComponent implements AutoSyncedComponent {
	private final LivingEntity entity;
	private boolean casting = false;

	public CastingComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		casting = tag.getBoolean("IsCasting");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putBoolean("IsCasting", casting);
	}

	public boolean isCasting() {
		return casting;
	}

	public void setCasting(boolean casting) {
		this.casting = casting;

		ArcanusComponents.CASTING_COMPONENT.sync(entity);
	}
}
