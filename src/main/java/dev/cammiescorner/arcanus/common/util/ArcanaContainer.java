package dev.cammiescorner.arcanus.common.util;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.common.data_component.ArcanaStack;
import net.minecraft.core.Direction;

import java.util.List;

public interface ArcanaContainer {
	ArcanaStack getArcanaStack(int index);

	void setArcanaStack(ArcanaStack arcanaStack, int index);

	void addArcanaStack(ArcanaStack arcanaStack);

	int indexOf(ArcanaStack arcanaStack);

	int size();

	boolean isEmpty();

	boolean contains(Arcana arcana);

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
