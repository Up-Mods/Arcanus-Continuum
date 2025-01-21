package dev.cammiescorner.arcanuscontinuum.api.entities;

import dev.upcraft.sparkweave.api.util.fakeplayer.FakePlayerHelper;
import net.minecraft.world.entity.player.Player;

public interface Targetable {
	default boolean arcanus$canBeTargeted() {
		if(this instanceof Player player) {
			return !FakePlayerHelper.isFakePlayer(player);
		}

		return false;
	}
}
