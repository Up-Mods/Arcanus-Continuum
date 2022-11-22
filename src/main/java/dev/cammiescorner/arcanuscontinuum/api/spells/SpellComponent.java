package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.util.Util;

public class SpellComponent {
	public static final SpellComponent EMPTY = new SpellComponent(Weight.NONE, 0, 20, 0);

	private final Weight weight;
	private final double manaCost;
	private final int coolDown;
	private final int minLevel;
	private String translationKey;

	public SpellComponent(Weight weight, double manaCost, int coolDown, int minLevel) {
		this.weight = weight;
		this.manaCost = manaCost;
		this.coolDown = coolDown;
		this.minLevel = minLevel;
	}

	public Weight getWeight() {
		return weight;
	}

	public double getManaCost() {
		return manaCost;
	}

	public int getCoolDown() {
		return coolDown;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public String getTranslationKey() {
		if(translationKey == null)
			translationKey = Util.createTranslationKey("spell_component", Arcanus.SPELL_COMPONENTS.getId(this));

		return translationKey;
	}
}
