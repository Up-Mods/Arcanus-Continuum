package dev.cammiescorner.arcanus.util;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.data_component.ArcanaStack;
import net.minecraft.core.Direction;

import java.util.List;

public interface ArcanaContainer {
	ArcanaStack getArcanaStack(int index);

	void setArcanaStack(ArcanaStack arcanaStack, int index);

	void addArcanaStack(ArcanaStack arcanaStack);

	int indexOf(ArcanaStack arcanaStack);

	int size();

	double maximumArcana();

	boolean isEmpty();

	boolean isFull();

	boolean contains(Arcana arcana);

	// TODO add a way to differentiate between input ArcanaStacks and output Arcana Stacks
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
