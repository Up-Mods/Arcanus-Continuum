package dev.cammiescorner.arcanus.common.component.color;

import com.google.common.base.MoreObjects;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.component.MagicColorComponent;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.core.HolderLookup;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class GenericMagicColorComponent implements MagicColorComponent, AutoSyncedComponent {
	private final Object provider;
	private UUID sourceId = Util.NIL_UUID;

	public GenericMagicColorComponent(Object provider) {
		this.provider = provider;
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		sourceId = tag.getUUID(SOURCE_ID_KEY);
		updateStoredColor();
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		tag.putUUID(SOURCE_ID_KEY, sourceId);
	}

	public Color getColor() {
		return ArcanusHelper.getMagicColor(sourceId);
	}

	public Color getPocketDimensionColor() {
		return ArcanusHelper.getPocketDimensionColor(sourceId);
	}

	@Override
	public UUID getSourceId() {
		return sourceId;
	}

	@Override
	public void setSourceId(@Nullable UUID ownerId) {
		this.sourceId = MoreObjects.firstNonNull(ownerId, Util.NIL_UUID);
		ArcanusComponents.MAGIC_COLOR.sync(provider);
		updateStoredColor();
	}

	private void updateStoredColor() {
		if(!Util.NIL_UUID.equals(sourceId)) {
			Arcanus.WIZARD_DATA.get(sourceId);
		}
	}
}
