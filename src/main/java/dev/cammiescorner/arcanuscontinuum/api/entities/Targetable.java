package dev.cammiescorner.arcanuscontinuum.api.entities;

import dev.upcraft.sparkweave.api.util.fakeplayer.FakePlayerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface Targetable {
	default boolean arcanus$canBeTargeted() {
		if (this instanceof Entity entity && entity.isAlive()) {
			//noinspection RedundantIfStatement
			if (this instanceof Player player && FakePlayerHelper.isFakePlayer(player)) {
				return false;
			}

			return true;
		}

		return false;
	}
}
