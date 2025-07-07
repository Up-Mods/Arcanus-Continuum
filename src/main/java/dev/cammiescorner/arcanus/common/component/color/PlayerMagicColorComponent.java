package dev.cammiescorner.arcanus.common.component.color;

import dev.cammiescorner.arcanus.common.component.MagicColorComponent;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class PlayerMagicColorComponent implements MagicColorComponent {
	private final Player player;

	public PlayerMagicColorComponent(Player player) {
		this.player = player;
	}

	@Override
	public Color getColor() {
		return ArcanusHelper.getMagicColor(player.getGameProfile().getId());
	}

	@Override
	public Color getPocketDimensionColor() {
		return ArcanusHelper.getPocketDimensionColor(player.getGameProfile().getId());
	}

	@Override
	public UUID getSourceId() {
		return player.getGameProfile().getId();
	}

	@Override
	public void setSourceId(UUID ownerId) {
		// NO-OP
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		// NO-OP
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		// NO-OP
	}
}
