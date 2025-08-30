package dev.cammiescorner.arcanus.api.util;

import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;

import java.util.function.Supplier;

public interface PrimalArcanaProvider {
	default double getArcana(Supplier<PrimalArcana> primalArcana) {
		return getArcana(primalArcana.get());
	}

	double getArcana(PrimalArcana primalArcana);
}
