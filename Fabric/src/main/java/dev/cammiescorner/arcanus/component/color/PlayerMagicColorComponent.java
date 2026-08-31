package dev.cammiescorner.arcanus.component.color;

import dev.cammiescorner.arcanus.component.MagicColorComponent;
import dev.cammiescorner.arcanus.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.UUID;

public class PlayerMagicColorComponent implements MagicColorComponent {
	private final Player player;

	public PlayerMagicColorComponent(Player player) {
		this.player = player;
	}

	@Override
	public Color getColor() {
		return ArcanusHelper.getMagicColor(player.getGameProfile().id());
	}

	@Override
	public Color getPocketDimensionColor() {
		return ArcanusHelper.getPocketDimensionColor(player.getGameProfile().id());
	}

	@Override
	public UUID getSourceId() {
		return player.getGameProfile().id();
	}

	@Override
	public void setSourceId(UUID ownerId) {
		// NO-OP
	}

	@Override
	public void readData(ValueInput readView) {
		// NO-OP
	}

	@Override
	public void writeData(ValueOutput writeView) {
		// NO-OP
	}
}
