package dev.cammiescorner.arcanuscontinuum.common.compat;

import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import eu.midnightdust.lib.config.MidnightConfig;

public class ArcanusConfig extends MidnightConfig {
	@Entry public static SpellShapeProperties selfShapeProperties = new SpellShapeProperties(true, Weight.VERY_LIGHT, 0, 0.9, 13, 1);
	@Entry public static SpellShapeProperties touchShapeProperties = new SpellShapeProperties(true, Weight.VERY_LIGHT, 0, 1, 20, 1);
	@Entry public static SpellShapeProperties projectileShapeProperties = new SpellShapeProperties(true, Weight.LIGHT, 0, 1, 30, 3);
	@Entry public static SpellShapeProperties lobShapeProperties = new SpellShapeProperties(true, Weight.LIGHT, 0, 1, 20, 3);
	@Entry public static SpellShapeProperties boltShapeProperties = new SpellShapeProperties(true, Weight.MEDIUM, 0, 1, 20, 5);
	@Entry public static SpellShapeProperties beamShapeProperties = new SpellShapeProperties(true, Weight.MEDIUM, 0, 1.25, 30, 5);
	@Entry public static SpellShapeProperties runeShapeProperties = new SpellShapeProperties(true, Weight.HEAVY, 0, 1, 50, 7);
	@Entry public static SpellShapeProperties explosionShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 1.25, 60, 7);
	@Entry public static SpellShapeProperties aoeShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 4, 60, 9);
	@Entry public static SpellShapeProperties smiteShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 1.75, 60, 9);
	@Entry public static SpellShapeProperties guardianOrbShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 3.5, 150, 10);

	@Entry public static SpellEffectProperties damageEffectProperties = new SpellEffectProperties(true, Weight.NONE, 4, 0, 1);
	@Entry public static SpellEffectProperties fireEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 2);
	@Entry public static SpellEffectProperties electricEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 2);
	@Entry public static SpellEffectProperties iceEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 2);
	@Entry public static SpellEffectProperties vulnerabilityEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 5);
	@Entry public static SpellEffectProperties manaLockEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 9);
	@Entry public static SpellEffectProperties witheringEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6.5, 0, 7);
	@Entry public static SpellEffectProperties necromancyEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 9);
	@Entry public static SpellEffectProperties manaSplitEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6.5, 0, 6);
	@Entry public static SpellEffectProperties copperCurseEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 8);
	@Entry public static SpellEffectProperties discombobulateEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 5);

	@Entry public static SpellEffectProperties healEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 1);
	@Entry public static SpellEffectProperties dispelEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 6);
	@Entry public static SpellEffectProperties regenerateEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 7);
	@Entry public static SpellEffectProperties fortifyEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6.5, 0, 5);
	@Entry public static SpellEffectProperties manaShieldEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 10);
	@Entry public static SpellEffectProperties temporalDilationEffectProperties = new SpellEffectProperties(true, Weight.NONE, 10, 0, 10);

	@Entry public static SpellEffectProperties pushEffectProperties = new SpellEffectProperties(true, Weight.NONE, 1, 0, 3);
	@Entry public static SpellEffectProperties pullEffectProperties = new SpellEffectProperties(true, Weight.NONE, 1, 0, 3);
	@Entry public static SpellEffectProperties powerEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 4);
	@Entry public static SpellEffectProperties anonymityEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 5);
	@Entry public static SpellEffectProperties mineEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7, 0, 4);
	@Entry public static SpellEffectProperties growthEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 4);
	@Entry public static SpellEffectProperties shrinkEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2.5, 0, 9);
	@Entry public static SpellEffectProperties enlargeEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2.5, 0, 9);
	@Entry public static SpellEffectProperties spatialPocketEffectProperties = new SpellEffectProperties(true, Weight.NONE, 10, 0, 7);
	@Entry public static SpellEffectProperties wardingEffectProperties = new SpellEffectProperties(true, Weight.NONE, 4, 0, 6);

	@Entry public static SpellEffectProperties buildEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 7);
	@Entry public static SpellEffectProperties levitateEffectProperties = new SpellEffectProperties(true, Weight.NONE, 3, 0, 6);
	@Entry public static SpellEffectProperties speedEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7, 0, 8);
	@Entry public static SpellEffectProperties teleportEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7.5, 0, 10);
	@Entry public static SpellEffectProperties exchangeEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 10);
	@Entry public static SpellEffectProperties bouncyEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 8);
	@Entry public static SpellEffectProperties featherEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 8);

	public static class SpellShapeProperties {
		@Entry public boolean enabled;
		@Entry public Weight weight;
		@Entry public double manaCost;
		@Entry public double manaMultiplier;
		@Entry public int coolDown;
		@Entry public int minimumLevel;

		public SpellShapeProperties(boolean enabled, Weight weight, double manaCost, double manaMultiplier, int coolDown, int minimumLevel) {
			this.enabled = enabled;
			this.weight = weight;
			this.manaCost = manaCost;
			this.manaMultiplier = manaMultiplier;
			this.coolDown = coolDown;
			this.minimumLevel = minimumLevel;
		}
	}

	public static class SpellEffectProperties {
		@Entry public boolean enabled;
		@Entry public Weight weight;
		@Entry public double manaCost;
		@Entry public int coolDown;
		@Entry public int minimumLevel;

		public SpellEffectProperties(boolean enabled, Weight weight, double manaCost, int coolDown, int minimumLevel) {
			this.enabled = enabled;
			this.weight = weight;
			this.manaCost = manaCost;
			this.coolDown = coolDown;
			this.minimumLevel = minimumLevel;
		}
	}
}
