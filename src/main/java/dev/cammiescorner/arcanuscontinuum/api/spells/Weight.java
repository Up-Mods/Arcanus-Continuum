package dev.cammiescorner.arcanuscontinuum.api.spells;

public enum Weight {
	VERY_LIGHT(0.1), LIGHT(0.2), MEDIUM(0.3), HEAVY(0.4), VERY_HEAVY(0.5);

	private final double speed;

	Weight(double slowdown) {
		this.speed = slowdown;
	}

	public double getSlowdown() {
		return speed;
	}
}
