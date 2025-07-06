package dev.cammiescorner.arcanus.api.util;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;

public interface ManaProvider {

	double getMana(ManaType type);
}
