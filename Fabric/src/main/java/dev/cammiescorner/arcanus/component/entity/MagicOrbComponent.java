package dev.cammiescorner.arcanus.component.entity;

import net.minecraft.core.UUIDUtil;
import net.minecraft.util.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

import java.util.UUID;

public class MagicOrbComponent implements CardinalComponent {
	private final LivingEntity entity;
	private UUID orbId = Util.NIL_UUID;

	public MagicOrbComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readData(ValueInput readView) {
		orbId = readView.read("OrbId", UUIDUtil.CODEC).orElse(Util.NIL_UUID);
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.store("OrbId", UUIDUtil.CODEC, orbId);
	}

	public UUID getOrbId() {
		return orbId;
	}

	public void setOrb(UUID orbId) {
		this.orbId = orbId;
	}
}
