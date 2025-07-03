package dev.cammiescorner.arcanus.api.spell.mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.Arcanus;

import java.util.Map;

public record ManaCost(double redMana, double greenMana, double blueMana, double whiteMana, double blackMana) {
	public static final Codec<ManaCost> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.DOUBLE.optionalFieldOf("red_mana", 0d).forGetter(ManaCost::redMana),
		Codec.DOUBLE.optionalFieldOf("green_mana", 0d).forGetter(ManaCost::greenMana),
		Codec.DOUBLE.optionalFieldOf("blue_mana", 0d).forGetter(ManaCost::blueMana),
		Codec.DOUBLE.optionalFieldOf("white_mana", 0d).forGetter(ManaCost::whiteMana),
		Codec.DOUBLE.optionalFieldOf("black_mana", 0d).forGetter(ManaCost::blackMana)
	).apply(instance, ManaCost::new));

	public Map<ManaType, Double> manaCosts() {
		return Arcanus.constructManaMap(redMana, greenMana, blueMana, whiteMana, blackMana);
	}
}
