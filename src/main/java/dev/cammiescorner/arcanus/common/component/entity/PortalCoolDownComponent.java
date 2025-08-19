package dev.cammiescorner.arcanus.common.component.entity;

import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PortalCoolDownComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final Entity entity;
	private int coolDown = 0;

	public PortalCoolDownComponent(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		coolDown = tag.getInt("CoolDown");
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		tag.putInt("CoolDown", coolDown);
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
