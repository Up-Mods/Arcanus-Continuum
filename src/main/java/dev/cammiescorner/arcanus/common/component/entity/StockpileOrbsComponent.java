package dev.cammiescorner.arcanus.common.component.entity;

import dev.cammiescorner.arcanus.common.entity.magic.StockpileOrb;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.core.HolderLookup;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class StockpileOrbsComponent implements AutoSyncedComponent {
	private final LivingEntity entity;
	private final List<UUID> orbs = new ArrayList<>();
	private final List<UUID> viewOfOrbs = Collections.unmodifiableList(orbs);

	public StockpileOrbsComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		orbs.clear();

		ListTag nbtList = tag.getList("Orbs", Tag.TAG_INT_ARRAY);

		for(Tag nbtElement : nbtList)
			orbs.add(NbtUtils.loadUUID(nbtElement));
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		ListTag nbtList = new ListTag();

		for(UUID uuid : orbs)
			nbtList.add(NbtUtils.createUUID(uuid));

		tag.put("Orbs", nbtList);
	}

	public List<UUID> getOrbs() {
		return viewOfOrbs;
	}

	public int orbCount() {
		return orbs.size();
	}

	public int orbIndex(StockpileOrb orb) {
		return orbs.indexOf(orb.getUUID());
	}

	public void addOrbToEntity(UUID orbId) {
		if(entity.level() instanceof ServerLevel world)
			orbs.removeIf(uuid -> world.getEntity(uuid) == null);

		orbs.add(orbId);
		entity.syncComponent(ArcanusComponents.STOCKPILE_ORB_COMPONENT);
	}

	public void removeOrbFromEntity(UUID orbId) {
		orbs.remove(orbId);
		entity.syncComponent(ArcanusComponents.STOCKPILE_ORB_COMPONENT);
	}
}
