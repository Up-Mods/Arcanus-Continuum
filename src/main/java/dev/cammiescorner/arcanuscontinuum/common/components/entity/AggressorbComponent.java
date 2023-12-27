package dev.cammiescorner.arcanuscontinuum.common.components.entity;

import dev.cammiescorner.arcanuscontinuum.common.entities.magic.AggressorbEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AggressorbComponent implements ServerTickingComponent, AutoSyncedComponent {
	private final LivingEntity entity;
	private final List<UUID> orbs = new ArrayList<>();
	private final int delay = 5;
	private int timer = 0;

	public AggressorbComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		if(entity.getWorld() instanceof ServerWorld world) {
			for(int i = 0; i < orbs.size(); i++) {
				AggressorbEntity orb = world.getEntity(orbs.get(i)) instanceof AggressorbEntity e ? e : null;

				if(orb == null || orb.isBoundToTarget()) {
					timer = 0;
					continue;
				}

				if(timer == delay * (i + 1)) {
					orb.setPosition(entity.getEyePos());
					orb.fire(entity.getRotationVec(1f), 0.04f);
				}
				else {
					i--;
					timer++;
				}
			}
		}
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		orbs.clear();

		NbtList nbtList = tag.getList("Orbs", NbtElement.INT_ARRAY_TYPE);

		for(NbtElement nbtElement : nbtList)
			orbs.add(NbtHelper.toUuid(nbtElement));

		timer = tag.getInt("Timer");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList nbtList = new NbtList();

		for(UUID uuid : orbs)
			nbtList.add(NbtHelper.fromUuid(uuid));

		tag.put("Orbs", nbtList);
		tag.putInt("Timer", timer);
	}

	public List<UUID> getOrbs() {
		return List.copyOf(orbs);
	}

	public int orbCount() {
		return orbs.size();
	}

	public int orbIndex(AggressorbEntity orb) {
		return orbs.indexOf(orb.getUuid());
	}

	public void addOrbToEntity(UUID orbId) {
		orbs.add(orbId);
		entity.syncComponent(ArcanusComponents.AGGRESSORB_COMPONENT);
	}

	public void removeOrbFromEntity(UUID orbId) {
		orbs.remove(orbId);
		entity.syncComponent(ArcanusComponents.AGGRESSORB_COMPONENT);
	}
}
