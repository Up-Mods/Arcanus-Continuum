package dev.cammiescorner.arcanus.common.util;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import net.minecraft.core.Direction;

// TODO this shtuff should support lists of arcana but im too lazy to do it now
public interface ArcanaMachine {
	boolean connectsToDirection(Direction direction);

	Arcana getArcana();

	void setArcana(Arcana arcana);

	double getArcanaAmount();

	void setArcanaAmount(double amount);
}
