package dev.cammiescorner.arcanuscontinuum.common.compat;

import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import eu.midnightdust.lib.config.MidnightConfig;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class ArcanusConfig extends MidnightConfig {
	@Entry public static boolean castingSpeedHasCoolDown = false;

	@Entry public static SpellShapeProperties selfShapeProperties = new SpellShapeProperties(true, Weight.VERY_LIGHT, 0, 0.85, 10, 1, 0);
	@Entry public static SpellShapeProperties touchShapeProperties = new SpellShapeProperties(true, Weight.VERY_LIGHT, 0, 1, 15, 1, 0.2);
	@Entry public static SpellShapeProperties projectileShapeProperties = new SpellShapeProperties(true, Weight.LIGHT, 0, 1, 10, 3, -0.75) {
		@Entry public float projectileSpeed = 4f;
		@Entry public int lifeSpan = 20;
	};
	@Entry public static SpellShapeProperties lobShapeProperties = new SpellShapeProperties(true, Weight.LIGHT, 0, 1, 20, 3, 0) {
		@Entry public float projectileSpeed = 2f;
	};
	@Entry public static SpellShapeProperties boltShapeProperties = new SpellShapeProperties(true, Weight.MEDIUM, 0, 1, 15, 5, 0) {
		@Entry public double range = 8;
	};
	@Entry public static SpellShapeProperties beamShapeProperties = new SpellShapeProperties(true, Weight.MEDIUM, 0, 1.25, 30, 5, 0.25) {
		@Entry public double range = 16;
		@Entry public int delay = 40;
	};
	@Entry public static SpellShapeProperties runeShapeProperties = new SpellShapeProperties(true, Weight.HEAVY, 0, 1, 50, 7, 0) {
		@Entry public int delay = 60;
	};
	@Entry public static SpellShapeProperties explosionShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 1.25, 60, 7, 0) {
		@Entry public float strength = 2.5f;
	};
	@Entry public static SpellShapeProperties aoeShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 4, 60, 9, 0) {
		@Entry public int lifeSpan = 100;
	};
	@Entry public static SpellShapeProperties smiteShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 1.75, 60, 9, 0.5);
	@Entry public static SpellShapeProperties guardianOrbShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 1.5, 100, 10, 0) {
		@Entry public double maximumManaLock = 0.9;
	};
	@Entry public static SpellShapeProperties aggressorbShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 0.8, 200, 10, 0) {
		@Entry public int maximumAggressorbs = 6;
		@Entry public float projectileSpeed = 3f;
	};

	@Entry public static SpellEffectProperties damageEffectProperties = new SpellEffectProperties(true, Weight.NONE, 4, 0, 1) {
		@Entry public float baseDamage = 1.5f;
	};
	@Entry public static SpellEffectProperties fireEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 2) {
		@Entry public int baseTimeOnFire = 3;
	};
	@Entry public static SpellEffectProperties electricEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 2) {
		@Entry public float baseStunTime = 2;
		@Entry public float wetEntityDamageMultiplier = 2f;
	};
	@Entry public static SpellEffectProperties iceEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 2) {
		@Entry public int baseFreezingTime = 20;
	};
	@Entry public static SpellEffectProperties vulnerabilityEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 5) {
		@Entry public int baseEffectDuration = 300;
	};
	@Entry public static SpellEffectProperties manaLockEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 9) {
		@Entry public int baseEffectDuration = 200;
	};
	@Entry public static SpellEffectProperties witheringEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6.5, 0, 7) {
		@Entry public int baseEffectDuration = 60;
	};
	@Entry public static SpellEffectProperties necromancyEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 9) {
		@Entry public int baseHealth = 11;
	};
	@Entry public static SpellEffectProperties manaSplitEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6.5, 0, 6);
	@Entry public static SpellEffectProperties copperCurseEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 8) {
		@Entry public int baseEffectDuration = 24000;
		@Entry public int effectDurationModifier = 12000;
		@Entry public double baseChanceToActivate = 0.0625;
	};
	@Entry public static SpellEffectProperties discombobulateEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 5) {
		@Entry public int baseEffectDuration = 60;
		@Entry public int effectDurationModifier = 15;
	};
	@Entry public static SpellEffectProperties stockpileEffectProperties = new SpellEffectProperties(true, Weight.NONE, 4.5, 0, 6) {
		@Entry public int baseEffectDuration = 100;
		@Entry public int effectDurationModifier = 30;
		@Entry public float damageNeededToIncrease = 10f;
	};

	@Entry public static SpellEffectProperties healEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 1) {
		@Entry public float baseHealAmount = 3f;
	};
	@Entry public static SpellEffectProperties dispelEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 6);
	@Entry public static SpellEffectProperties regenerateEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 7) {
		@Entry public int baseEffectDuration = 100;
	};
	@Entry public static SpellEffectProperties fortifyEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6.5, 0, 5) {
		@Entry public int baseEffectDuration = 500;
	};
	@Entry public static SpellEffectProperties hasteEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7, 0, 4) {
		@Entry public int baseEffectDuration = 200;
	};
	@Entry public static SpellEffectProperties manaShieldEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 10) {
		@Entry public int baseLifeSpan = 100;
		@Entry public int lifeSpanModifier = 40;
	};
	@Entry public static SpellEffectProperties dangerSenseEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 8) {
		@Entry public int baseEffectDuration = 100;
		@Entry public double baseChanceToActivate = 0.025;
	};
//	@Entry public static SpellEffectProperties temporalDilationEffectProperties = new SpellEffectProperties(true, Weight.NONE, 10, 0, 10) {
//		@Entry public int baseEffectDuration = 100;
//		@Entry public boolean affectsPlayers = true;
//	};

	@Entry public static SpellEffectProperties pushEffectProperties = new SpellEffectProperties(true, Weight.NONE, 1, 0, 3) {
		@Entry public double pushAmount = 0.2;
	};
	@Entry public static SpellEffectProperties pullEffectProperties = new SpellEffectProperties(true, Weight.NONE, 1, 0, 3) {
		@Entry public double pullAmount = 0.2;
	};
	@Entry public static SpellEffectProperties powerEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 4) {
		@Entry public int basePower = 4;
	};
	@Entry public static SpellEffectProperties anonymityEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 5) {
		@Entry public int baseEffectDuration = 220;
	};
	@Entry public static SpellEffectProperties mineEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7, 0, 4);
	@Entry public static SpellEffectProperties growthEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 4);
	@Entry public static SpellEffectProperties shrinkEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2.5, 0, 9) {
		@Entry public double shrinkAmount = 0.5;
		@Entry public double baseEffectDuration = 100;
	};
	@Entry public static SpellEffectProperties enlargeEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2.5, 0, 9) {
		@Entry public double enlargeAmount = 1.5;
		@Entry public int baseEffectDuration = 100;
	};
	@Entry public static SpellEffectProperties spatialRiftEffectProperties = new SpellEffectProperties(true, Weight.NONE, 10, 0, 7) {
		@Entry public boolean canSuckEntitiesIn = true;
		@Entry public int portalGrowTime = 100;
		@Entry public int pocketWidth = 24;
		@Entry public int pocketHeight = 24;
	};
	@Entry public static SpellEffectProperties wardingEffectProperties = new SpellEffectProperties(true, Weight.NONE, 4, 0, 6) {
		@Entry public boolean canBeRemovedByOthers = true;
	};

	@Entry public static SpellEffectProperties buildEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 7) {
		@Entry public int baseLifeSpan = 220;
	};
	@Entry public static SpellEffectProperties levitateEffectProperties = new SpellEffectProperties(true, Weight.NONE, 3, 0, 6) {
		@Entry public int baseEffectDuration = 60;
	};
	@Entry public static SpellEffectProperties speedEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7, 0, 8) {
		@Entry public int baseEffectDuration = 300;
	};
	@Entry public static SpellEffectProperties teleportEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7.5, 0, 10) {
		@Entry public int baseTeleportDistance = 5;
	};
	@Entry public static SpellEffectProperties exchangeEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 7) {
		@Entry public int baseTeleportDistance = 5;
	};
	@Entry public static SpellEffectProperties bouncyEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 8) {
		@Entry public int baseEffectDuration = 220;
	};
	@Entry public static SpellEffectProperties featherEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 8) {
		@Entry public int baseEffectDuration = 100;
	};
	@Entry public static SpellEffectProperties antiGravityEffectProperties = new SpellEffectProperties(true, Weight.NONE, 10, 0, 5) {
		@Entry public boolean removedUponTakingDamage = true;
		@Entry public float baseMovementSpeed = 0.1f;
	};
	@Entry public static SpellEffectProperties manaWingsEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8.5, 0, 10) {
		@Entry public boolean removedUponTakingDamage = true;
		@Entry public int baseEffectDuration = 200;
		@Entry public int effectDurationModifier = 100;
	};

	@SuppressWarnings("unchecked")
	public static <T> Supplier<T> getConfigOption(Object obj, String name) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);

			return () -> {
				try {
					return (T) field.get(obj);
				}
				catch(IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			};
		}
		catch(NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	public static class SpellShapeProperties {
		@Entry public boolean enabled;
		@Entry public Weight weight;
		@Entry public double manaCost;
		@Entry public double manaMultiplier;
		@Entry public int coolDown;
		@Entry public int minimumLevel;
		@Entry public double potencyModifier;

		public SpellShapeProperties(boolean enabled, Weight weight, double manaCost, double manaMultiplier, int coolDown, int minimumLevel, double potencyModifier) {
			this.enabled = enabled;
			this.weight = weight;
			this.manaCost = manaCost;
			this.manaMultiplier = manaMultiplier;
			this.coolDown = coolDown;
			this.minimumLevel = minimumLevel;
			this.potencyModifier = potencyModifier;
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
