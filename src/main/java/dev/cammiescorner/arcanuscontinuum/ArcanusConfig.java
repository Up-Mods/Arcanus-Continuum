package dev.cammiescorner.arcanuscontinuum;

import com.mojang.datafixers.util.Pair;
import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
@Config(Arcanus.MOD_ID)
public final class ArcanusConfig {
	private static final Map<Pair<Class<?>, String>, Supplier<?>> CONFIG_CACHE = new HashMap<>();

	@ConfigEntry(id = "castingSpeedHasCoolDown", type = EntryType.BOOLEAN, translation = "") public static boolean castingSpeedHasCoolDown = false;

	@InlineCategory public static SpellShapeProperties selfShapeProperties = new SpellShapeProperties(true, Weight.VERY_LIGHT, 0, 0.85, 10, 1, 0);
	@InlineCategory public static SpellShapeProperties touchShapeProperties = new SpellShapeProperties(true, Weight.VERY_LIGHT, 0, 1, 15, 1, 0.2);
	@InlineCategory public static SpellShapeProperties projectileShapeProperties = new SpellShapeProperties(true, Weight.LIGHT, 0, 1, 10, 3, -0.25) {
		@ConfigEntry(id = "projectileSpeed", type = EntryType.FLOAT, translation = "") public float projectileSpeed = 4f;
		@ConfigEntry(id = "lifeSpan", type = EntryType.INTEGER, translation = "") public int lifeSpan = 20;
	};
	@InlineCategory public static SpellShapeProperties lobShapeProperties = new SpellShapeProperties(true, Weight.LIGHT, 0, 1, 20, 3, 0) {
		@ConfigEntry(id = "projectileSpeed", type = EntryType.FLOAT, translation = "") public float projectileSpeed = 2f;
	};
	@InlineCategory public static SpellShapeProperties boltShapeProperties = new SpellShapeProperties(true, Weight.MEDIUM, 0, 1, 15, 5, 0) {
		@ConfigEntry(id = "range", type = EntryType.DOUBLE, translation = "") public double range = 6;
	};
	// TODO come back to this
	@InlineCategory public static SpellShapeProperties beamShapeProperties = new SpellShapeProperties(true, Weight.MEDIUM, 0, 1.25, 30, 5, 0.25) {
		@ConfigEntry(id = "range", type = EntryType.DOUBLE, translation = "") public double range = 16;
		@ConfigEntry(id = "delay", type = EntryType.INTEGER, translation = "") public int delay = 40;
	};
	@InlineCategory public static SpellShapeProperties runeShapeProperties = new SpellShapeProperties(true, Weight.HEAVY, 0, 1, 50, 7, 0) {
		@ConfigEntry(id = "range", type = EntryType.INTEGER, translation = "") public int delay = 60;
	};
	@InlineCategory public static SpellShapeProperties explosionShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 1.25, 60, 7, 0) {
		@ConfigEntry(id = "strength", type = EntryType.FLOAT, translation = "") public float strength = 2.5f;
	};
	@InlineCategory public static SpellShapeProperties aoeShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 4, 60, 9, 0) {
		@ConfigEntry(id = "lifeSpan", type = EntryType.INTEGER, translation = "") public int lifeSpan = 100;
	};
	@InlineCategory public static SpellShapeProperties smiteShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 1.75, 60, 9, 0.5);
	@InlineCategory public static SpellShapeProperties guardianOrbShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 1.5, 100, 10, 0) {
		@ConfigEntry(id = "maximumManaLock", type = EntryType.DOUBLE, translation = "") public double maximumManaLock = 0.9;
	};
	@InlineCategory public static SpellShapeProperties aggressorbShapeProperties = new SpellShapeProperties(true, Weight.VERY_HEAVY, 0, 0.8, 200, 10, 0) {
		@ConfigEntry(id = "maximumAggressorbs", type = EntryType.INTEGER, translation = "") public int maximumAggressorbs = 6;
		@ConfigEntry(id = "projectileSpeed", type = EntryType.FLOAT, translation = "") public float projectileSpeed = 3f;
	};

	@InlineCategory public static SpellEffectProperties damageEffectProperties = new SpellEffectProperties(true, Weight.NONE, 4, 0, 1) {
		@ConfigEntry(id = "baseDamage", type = EntryType.FLOAT, translation = "") public float baseDamage = 1.5f;
	};
	@InlineCategory public static SpellEffectProperties fireEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 2) {
		@ConfigEntry(id = "baseTimeOnFire", type = EntryType.INTEGER, translation = "") public int baseTimeOnFire = 3;
	};
	@InlineCategory public static SpellEffectProperties electricEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 2) {
		@ConfigEntry(id = "baseStunTime", type = EntryType.FLOAT, translation = "") public float baseStunTime = 2;
		@ConfigEntry(id = "wetEntityDamageMultiplier", type = EntryType.FLOAT, translation = "") public float wetEntityDamageMultiplier = 2f;
	};
	@InlineCategory public static SpellEffectProperties iceEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 2) {
		@ConfigEntry(id = "baseFreezingTime", type = EntryType.INTEGER, translation = "") public int baseFreezingTime = 20;
	};
	@InlineCategory public static SpellEffectProperties vulnerabilityEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 5) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 300;
	};
	@InlineCategory public static SpellEffectProperties manaLockEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 9) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 200;
	};
	@InlineCategory public static SpellEffectProperties witheringEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6.5, 0, 7) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 60;
	};
	@InlineCategory public static SpellEffectProperties necromancyEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 9) {
		@ConfigEntry(id = "baseHealth", type = EntryType.INTEGER, translation = "") public int baseHealth = 11;
	};
	@InlineCategory public static SpellEffectProperties manaSplitEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6.5, 0, 6);
	@InlineCategory public static SpellEffectProperties copperCurseEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 8) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 24000;
		@ConfigEntry(id = "effectDurationModifier", type = EntryType.INTEGER, translation = "") public int effectDurationModifier = 12000;
		@ConfigEntry(id = "baseChanceToActivate", type = EntryType.DOUBLE, translation = "") public double baseChanceToActivate = 0.0625;
	};
	@InlineCategory public static SpellEffectProperties discombobulateEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 5) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 60;
		@ConfigEntry(id = "effectDurationModifier", type = EntryType.INTEGER, translation = "") public int effectDurationModifier = 15;
	};
	@InlineCategory public static SpellEffectProperties stockpileEffectProperties = new SpellEffectProperties(true, Weight.NONE, 4.5, 0, 6) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 100;
		@ConfigEntry(id = "effectDurationModifier", type = EntryType.INTEGER, translation = "") public int effectDurationModifier = 30;
		@ConfigEntry(id = "damageNeededToIncrease", type = EntryType.FLOAT, translation = "") public float damageNeededToIncrease = 10f;
	};

	@InlineCategory public static SpellEffectProperties healEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 1) {
		@ConfigEntry(id = "baseHealAmount", type = EntryType.FLOAT, translation = "") public float baseHealAmount = 3f;
	};
	@InlineCategory public static SpellEffectProperties dispelEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 6);
	@InlineCategory public static SpellEffectProperties regenerateEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 7) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 100;
	};
	@InlineCategory public static SpellEffectProperties fortifyEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6.5, 0, 5) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 500;
	};
	@InlineCategory public static SpellEffectProperties hasteEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7, 0, 4) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 200;
	};
	@InlineCategory public static SpellEffectProperties manaShieldEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8, 0, 10) {
		@ConfigEntry(id = "baseLifeSpan", type = EntryType.INTEGER, translation = "") public int baseLifeSpan = 100;
		@ConfigEntry(id = "lifeSpanModifier", type = EntryType.INTEGER, translation = "") public int lifeSpanModifier = 40;
	};
	@InlineCategory public static SpellEffectProperties dangerSenseEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 8) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 100;
		@ConfigEntry(id = "baseChanceToActivate", type = EntryType.DOUBLE, translation = "") public double baseChanceToActivate = 0.035;
	};
//	@InlineCategory public static SpellEffectProperties temporalDilationEffectProperties = new SpellEffectProperties(true, Weight.NONE, 10, 0, 10) {
//		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 100;
//		@ConfigEntry(id = "affectsPlayers", type = EntryType.BOOLEAN, translation = "") public boolean affectsPlayers = true;
//	};

	@InlineCategory public static SpellEffectProperties pushEffectProperties = new SpellEffectProperties(true, Weight.NONE, 1, 0, 3) {
		@ConfigEntry(id = "pushAmount", type = EntryType.DOUBLE, translation = "") public double pushAmount = 0.2;
	};
	@InlineCategory public static SpellEffectProperties pullEffectProperties = new SpellEffectProperties(true, Weight.NONE, 1, 0, 3) {
		@ConfigEntry(id = "pullAmount", type = EntryType.DOUBLE, translation = "") public double pullAmount = 0.2;
	};
	@InlineCategory public static SpellEffectProperties powerEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2, 0, 4) {
		@ConfigEntry(id = "basePower", type = EntryType.INTEGER, translation = "") public int basePower = 4;
	};
	@InlineCategory public static SpellEffectProperties anonymityEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 5) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 220;
	};
	@InlineCategory public static SpellEffectProperties mineEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7, 0, 4);
	@InlineCategory public static SpellEffectProperties growthEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 4);
	@InlineCategory public static SpellEffectProperties shrinkEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2.5, 0, 9) {
		@ConfigEntry(id = "shrinkAmount", type = EntryType.DOUBLE, translation = "") public double shrinkAmount = 0.5;
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.DOUBLE, translation = "") public double baseEffectDuration = 100;
	};
	@InlineCategory public static SpellEffectProperties enlargeEffectProperties = new SpellEffectProperties(true, Weight.NONE, 2.5, 0, 9) {
		@ConfigEntry(id = "enlargeAmount", type = EntryType.DOUBLE, translation = "") public double enlargeAmount = 1.5;
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 100;
	};
	@InlineCategory public static SpellEffectProperties spatialRiftEffectProperties = new SpellEffectProperties(true, Weight.NONE, 10, 0, 7) {
		@ConfigEntry(id = "canSuckEntitiesIn", type = EntryType.BOOLEAN, translation = "") public boolean canSuckEntitiesIn = true;
		@ConfigEntry(id = "portalGrowTime", type = EntryType.INTEGER, translation = "") public int portalGrowTime = 100;
		@ConfigEntry(id = "pocketWidth", type = EntryType.INTEGER, translation = "") public int pocketWidth = 24;
		@ConfigEntry(id = "pocketHeight", type = EntryType.INTEGER, translation = "") public int pocketHeight = 24;
	};
	@InlineCategory public static SpellEffectProperties wardingEffectProperties = new SpellEffectProperties(true, Weight.NONE, 4, 0, 6) {
		@ConfigEntry(id = "canBeRemovedByOthers", type = EntryType.BOOLEAN, translation = "") public boolean canBeRemovedByOthers = true;
	};

	@InlineCategory public static SpellEffectProperties buildEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 7) {
		@ConfigEntry(id = "baseLifeSpan", type = EntryType.INTEGER, translation = "") public int baseLifeSpan = 220;
	};
	@InlineCategory public static SpellEffectProperties levitateEffectProperties = new SpellEffectProperties(true, Weight.NONE, 3, 0, 6) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 60;
	};
	@InlineCategory public static SpellEffectProperties speedEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7, 0, 8) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 300;
	};
	@InlineCategory public static SpellEffectProperties teleportEffectProperties = new SpellEffectProperties(true, Weight.NONE, 7.5, 0, 10) {
		@ConfigEntry(id = "baseTeleportDistance", type = EntryType.INTEGER, translation = "") public int baseTeleportDistance = 5;
	};
	@InlineCategory public static SpellEffectProperties exchangeEffectProperties = new SpellEffectProperties(true, Weight.NONE, 6, 0, 7) {
		@ConfigEntry(id = "baseTeleportDistance", type = EntryType.INTEGER, translation = "") public int baseTeleportDistance = 5;
	};
	@InlineCategory public static SpellEffectProperties bouncyEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 8) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 220;
	};
	@InlineCategory public static SpellEffectProperties featherEffectProperties = new SpellEffectProperties(true, Weight.NONE, 5, 0, 8) {
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 100;
	};
	@InlineCategory public static SpellEffectProperties antiGravityEffectProperties = new SpellEffectProperties(true, Weight.NONE, 10, 0, 5) {
		@ConfigEntry(id = "removedUponTakingDamage", type = EntryType.BOOLEAN, translation = "") public boolean removedUponTakingDamage = true;
		@ConfigEntry(id = "baseMovementSpeed", type = EntryType.FLOAT, translation = "") public float baseMovementSpeed = 0.1f;
	};
	@InlineCategory public static SpellEffectProperties manaWingsEffectProperties = new SpellEffectProperties(true, Weight.NONE, 8.5, 0, 10) {
		@ConfigEntry(id = "removedUponTakingDamage", type = EntryType.BOOLEAN, translation = "") public boolean removedUponTakingDamage = true;
		@ConfigEntry(id = "baseEffectDuration", type = EntryType.INTEGER, translation = "") public int baseEffectDuration = 200;
		@ConfigEntry(id = "effectDurationModifier", type = EntryType.INTEGER, translation = "") public int effectDurationModifier = 100;
	};

	public static class SpellShapeProperties {
		@ConfigEntry(id = "enabled", type = EntryType.BOOLEAN, translation = "") public boolean enabled;
		@ConfigEntry(id = "weight", type = EntryType.ENUM, translation = "") public Weight weight;
		@ConfigEntry(id = "manaCost", type = EntryType.DOUBLE, translation = "") public double manaCost;
		@ConfigEntry(id = "manaMultiplier", type = EntryType.DOUBLE, translation = "") public double manaMultiplier;
		@ConfigEntry(id = "coolDown", type = EntryType.INTEGER, translation = "") public int coolDown;
		@ConfigEntry(id = "minimumLevel", type = EntryType.INTEGER, translation = "") public int minimumLevel;
		@ConfigEntry(id = "potencyModifier", type = EntryType.DOUBLE, translation = "") public double potencyModifier;

		public SpellShapeProperties(boolean enabled, Weight weight, double manaCost, double manaMultiplier, int coolDown, int minimumLevel, double potencyModifier) {
			this.enabled = enabled;
			this.weight = weight;
			this.manaCost = manaCost;
			this.manaMultiplier = manaMultiplier;
			this.coolDown = coolDown;
			this.minimumLevel = minimumLevel;
			this.potencyModifier = potencyModifier;
		}

		@SuppressWarnings("unchecked")
		public <T> T getProperty(String name) {
			return (T) CONFIG_CACHE.computeIfAbsent(new Pair<>(getClass(), name), classStringPair -> {
				try {
					Field field = getClass().getDeclaredField(name);
					field.setAccessible(true);

					return () -> {
						try {
							return (T) field.get(this);
						}
						catch(IllegalAccessException e) {
							throw new RuntimeException(e);
						}
					};
				}
				catch(NoSuchFieldException e) {
					throw new RuntimeException(e);
				}
			}).get();
		}
	}

	public static class SpellEffectProperties {
		@ConfigEntry(id = "enabled", type = EntryType.BOOLEAN, translation = "") public boolean enabled;
		@ConfigEntry(id = "weight", type = EntryType.ENUM, translation = "") public Weight weight;
		@ConfigEntry(id = "manaCost", type = EntryType.DOUBLE, translation = "") public double manaCost;
		@ConfigEntry(id = "coolDown", type = EntryType.INTEGER, translation = "") public int coolDown;
		@ConfigEntry(id = "minimumLevel", type = EntryType.INTEGER, translation = "") public int minimumLevel;

		public SpellEffectProperties(boolean enabled, Weight weight, double manaCost, int coolDown, int minimumLevel) {
			this.enabled = enabled;
			this.weight = weight;
			this.manaCost = manaCost;
			this.coolDown = coolDown;
			this.minimumLevel = minimumLevel;
		}

		@SuppressWarnings("unchecked")
		public <T> T getProperty(String name) {
			return (T) CONFIG_CACHE.computeIfAbsent(new Pair<>(getClass(), name), classStringPair -> {
				try {
					Field field = getClass().getDeclaredField(name);
					field.setAccessible(true);

					return () -> {
						try {
							return (T) field.get(this);
						}
						catch(IllegalAccessException e) {
							throw new RuntimeException(e);
						}
					};
				}
				catch(NoSuchFieldException e) {
					throw new RuntimeException(e);
				}
			}).get();
		}
	}
}
