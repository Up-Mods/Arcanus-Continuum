package dev.cammiescorner.arcanus.component.entity;

import dev.cammiescorner.arcanus.entity.magic.StockpileOrb;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

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
	public void readData(ValueInput readView) {
		orbs.clear();
		orbs.addAll(readView.read("Orbs", UUIDUtil.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.store("Orbs", UUIDUtil.CODEC.listOf(), orbs);
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
