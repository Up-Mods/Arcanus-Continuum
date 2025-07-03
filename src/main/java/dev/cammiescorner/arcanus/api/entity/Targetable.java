package dev.cammiescorner.arcanus.api.entity;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface Targetable {
	default boolean arcanus$canBeTargeted() {
		if(this instanceof Entity entity && entity.isAlive()) {
			// noinspection RedundantIfStatement
			if(this instanceof Player player && player instanceof FakePlayer)
				return false;

			return true;
		}

		return false;
	}
}
