package dev.cammiescorner.arcanus.api.spell;

import net.minecraft.util.StringRepresentable;

public enum Pattern implements StringRepresentable {
	LEFT("\u25fb", "L"), RIGHT("\u26aa", "R");

	private final String symbol, letter;
	public static final StringRepresentable.EnumCodec<Pattern> CODEC = new EnumCodec<>(values(), Pattern::valueOf);

	Pattern(String symbol, String letter) {
		this.symbol = symbol;
		this.letter = letter;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getLetter() {
		return letter;
	}

	@Override
	public String getSerializedName() {
		return letter;
	}
}
