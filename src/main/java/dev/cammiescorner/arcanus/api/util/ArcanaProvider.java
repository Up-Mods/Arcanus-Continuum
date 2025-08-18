package dev.cammiescorner.arcanus.api.util;

import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;

import java.util.function.Supplier;

public interface ArcanaProvider {
	default double getMana(Supplier<PrimalArcana> primalArcana) {
		return getMana(primalArcana.get());
	}

	double getMana(PrimalArcana primalArcana);
}
