package dev.cammiescorner.arcanus.common.util;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import net.minecraft.core.Direction;

import java.util.List;

// TODO this shtuff should support lists of arcana but im too lazy to do it now
public interface ArcanaContainer {
	Arcana getArcana();

	void setArcana(Arcana arcana);

	double getArcanaAmount();

	void setArcanaAmount(double amount);

	default double getMaxArcanaAmount() {
		return 64;
	}

	default List<Direction> inputDirections() {
		return List.of(Direction.values());
	}

	default List<Direction> outputDirections() {
		return List.of(Direction.values());
	}

	default boolean connectsToDirection(Direction direction) {
		return inputDirections().contains(direction) || outputDirections().contains(direction);
	}
}
