package dev.cammiescorner.arcanuscontinuum.api.entities;

public interface Targetable {
	default boolean arcanuscontinuum$canBeTargeted() {
		return true;
	}
}
