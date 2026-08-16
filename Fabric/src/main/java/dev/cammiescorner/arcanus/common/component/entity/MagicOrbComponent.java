package dev.cammiescorner.arcanus.common.component.entity;

import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.Component;

import java.util.UUID;

public class MagicOrbComponent implements Component {
	private final LivingEntity entity;
	private UUID orbId = Util.NIL_UUID;

	public MagicOrbComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		orbId = tag.getUUID("OrbId");
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		tag.putUUID("OrbId", orbId);
	}

	public UUID getOrbId() {
		return orbId;
	}

	public void setOrb(UUID orbId) {
		this.orbId = orbId;
	}
}
