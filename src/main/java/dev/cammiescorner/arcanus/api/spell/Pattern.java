package dev.cammiescorner.arcanus.api.spell;

public enum Pattern {
	LEFT("\u25fb", "L"), RIGHT("\u26aa", "R");

	private final String symbol, letter;

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
}
