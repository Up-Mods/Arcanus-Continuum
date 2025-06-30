package dev.cammiescorner.arcanus;

import com.teamresourceful.resourcefulconfig.api.annotations.*;
import dev.cammiescorner.arcanus.api.spells.ManaType;
import dev.cammiescorner.arcanus.api.spells.Weight;
import dev.cammiescorner.arcanus.client.util.MirrorHudElement;

import java.util.Map;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

@Config(value = Arcanus.MOD_ID, categories = {
	ArcanusConfig.SpellShapes.class,
	ArcanusConfig.AttackEffects.class,
	ArcanusConfig.SupportEffects.class,
	ArcanusConfig.UtilityEffects.class,
	ArcanusConfig.MovementEffects.class
})
@ConfigInfo(title = "Arcanus")
public final class ArcanusConfig {
	@ConfigEntry(id = "castingSpeedHasCoolDown", translation = CONFIG_CASTING_HAS_SPEED_LIMIT)
	public static boolean castingSpeedHasCoolDown = false;

	@ConfigEntry(id = "sizeChangingIsPermanent", translation = CONFIG_SIZE_CHANGE_IS_PERMA)
	public static boolean sizeChangingIsPermanent = false;

	@ConfigEntry(id = "scaleManaBarsWithMaxMana", translation = CONFIG_SCALE_MANA_BARS)
	public static boolean scaleManaBarsWithMaxMana = true;

	@ConfigEntry(id = "manaBarsMaxLength", translation = CONFIG_MANA_BARS_MAX_LENGTH)
	public static int manaBarsMaxLength = 200;

	@ConfigEntry(id = "manaBarsOnBottom", translation = CONFIG_MANA_BARS_ON_TOP)
	public static boolean manaBarsOnTop = true;

	@ConfigEntry(id = "rightSideManaBars", translation = CONFIG_RIGHT_SIDE_MANA_BARS)
	public static MirrorHudElement rightSideManaBars = MirrorHudElement.IF_LEFT_HANDED;

	@ConfigEntry(id = "numericalManaDisplay", translation = CONFIG_NUMERICAL_MANA_DISPLAY)
	public static boolean numericalManaDisplay = false;

	@Category(value = "Spell Shapes", categories = {
		SpellShapes.SelfShapeProperties.class,
		SpellShapes.MissileShapeProperties.class,
		SpellShapes.LobShapeProperties.class,
		SpellShapes.BoltShapeProperties.class,
		SpellShapes.BeamShapeProperties.class,
		SpellShapes.RuneShapeProperties.class,
		SpellShapes.BurstShapeProperties.class,
		SpellShapes.GuidedShotShapeProperties.class,
		SpellShapes.CounterShapeProperties.class,
		SpellShapes.AOEShapeProperties.class,
		SpellShapes.SmiteShapeProperties.class,
		SpellShapes.EntangledOrbShapeProperties.class,
		SpellShapes.AggressorbShapeProperties.class
	})
	public static final class SpellShapes {
		@Category("Self")
		public static final class SelfShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_LIGHT;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 0.85;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 10;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("Missile")
		public static final class MissileShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.LIGHT;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 10;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "projectileSpeed", translation = CONFIG_PROJECTILE_SPEED)
			public static float projectileSpeed = 4f;

			@ConfigEntry(id = "baseLifeSpan", translation = CONFIG_BASE_LIFE_SPAN)
			@ConfigOption.Range(min = 1, max = 24000)
			public static int baseLifeSpan = 20;
		}

		@Category("Lob")
		public static final class LobShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.LIGHT;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 20;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "projectileSpeed", translation = CONFIG_PROJECTILE_SPEED)
			public static float projectileSpeed = 2f;
		}

		@Category("Bolt")
		public static final class BoltShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.MEDIUM;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 15;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "range", translation = CONFIG_RANGE)
			@ConfigOption.Range(min = 0, max = 32)
			public static double range = 6;
		}

		@Category("Beam")
		public static final class BeamShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.MEDIUM;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1.25;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 30;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0.25;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "range", translation = CONFIG_RANGE)
			@ConfigOption.Range(min = 0, max = 32)
			public static double range = 16;

			@ConfigEntry(id = "delay", translation = CONFIG_DELAY)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int delay = 40;
		}

		@Category("Rune")
		public static final class RuneShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.HEAVY;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 50;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "delay", translation = CONFIG_DELAY)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int delay = 60;
		}

		@Category("Burst")
		public static final class BurstShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_HEAVY;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1.25;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 60;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "radius", translation = CONFIG_BURST_SHAPE_RADIUS)
			public static float radius = 4f;
		}

		@Category("Guided Shot")
		public static final class GuidedShotShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = false;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.MEDIUM;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 100;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("Counter")
		public static final class CounterShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.HEAVY;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1.2;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 300;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;
		}

		@Category("AOE")
		public static final class AOEShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_HEAVY;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 4;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 60;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "baseLifeSpan", translation = CONFIG_BASE_LIFE_SPAN)
			@ConfigOption.Range(min = 1, max = 24000)
			public static int baseLifeSpan = 100;

			@ConfigEntry(id = "timesToApplyEffects", translation = CONFIG_TIMES_TO_APPLY_EFFECTS)
			public static int timesToApplyEffects = 3;

			@ConfigEntry(id = "timesToCastNextShape", translation = CONFIG_TIMES_TO_CAST_NEXT_SHAPE)
			public static int timesToCastNextShape = 3;
		}

		@Category("Smite")
		public static final class SmiteShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_HEAVY;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1.75;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 60;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0.5;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("Entangled Orb")
		public static final class EntangledOrbShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_HEAVY;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1.5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 100;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "maximumManaLock", translation = CONFIG_MAX_MANA_LOCK)
			@ConfigOption.Range(min = 0, max = 1)
			public static double maximumManaLock = 0.5;

			@ConfigEntry(id = "baseManaDrain", translation = CONFIG_BASE_MANA_DRAIN)
			public static double baseManaDrain = 3;
		}

		@Category("Aggressorb")
		public static final class AggressorbShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_HEAVY;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 0.8;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 200;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "maximumAggressorbs", translation = CONFIG_MAX_AGGRESSORBS)
			@ConfigOption.Range(min = 0, max = 16)
			public static int maximumAggressorbs = 6;

			@ConfigEntry(id = "aggressorbsPerCast", translation = CONFIG_AGGRESSORBS_PER_CAST)
			@ConfigOption.Range(min = 0, max = 16)
			public static int aggressorbsPerCast = 2;

			@ConfigEntry(id = "projectileSpeed", translation = CONFIG_PROJECTILE_SPEED)
			public static float projectileSpeed = 3f;
		}
	}

	@Category(value = "Attack Effects", categories = {
		AttackEffects.DamageEffectProperties.class,
		AttackEffects.FireEffectProperties.class,
		AttackEffects.ElectricEffectProperties.class,
		AttackEffects.IceEffectProperties.class,
		AttackEffects.VulnerabilityEffectProperties.class,
		AttackEffects.ManaLockEffectProperties.class,
		AttackEffects.WitheringEffectProperties.class,
		AttackEffects.NecromancyEffectProperties.class,
		AttackEffects.ManaSplitEffectProperties.class,
		AttackEffects.CopperCurseEffectProperties.class,
		AttackEffects.DiscombobulateEffectProperties.class,
		AttackEffects.StockpileEffectProperties.class
	})
	public static final class AttackEffects {
		@Category("Damage")
		public static final class DamageEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseDamage", translation = CONFIG_BASE_DAMAGE)
			@ConfigOption.Range(min = 0, max = 1000)
			public static float baseDamage = 2f;
		}

		@Category("Fire")
		public static final class FireEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseTimeOnFire", translation = CONFIG_BASE_TIME_ON_FIRE)
			@ConfigOption.Range(min = 0, max = 100)
			public static int baseTimeOnFire = 60;
		}

		@Category("Electric")
		public static final class ElectricEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseStunTime", translation = CONFIG_BASE_STUN_TIME)
			@ConfigOption.Range(min = 0, max = 100)
			public static int baseStunTime = 10;

			@ConfigEntry(id = "wetEntityDamageMultiplier", translation = CONFIG_WET_ENTITY_DAMAGE_MULTIPLIER)
			@ConfigOption.Range(min = 1, max = 1000)
			public static float wetEntityDamageMultiplier = 1.5f;
		}

		@Category("Ice")
		public static final class IceEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseFreezingTime", translation = CONFIG_BASE_FREEZING_TIME)
			@ConfigOption.Range(min = 0, max = 100)
			public static int baseFreezingTime = 60;
		}

		@Category("Vulnerability")
		public static final class VulnerabilityEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 300;
		}

		@Category("Mana Lock")
		public static final class ManaLockEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 200;
		}

		@Category("Withering")
		public static final class WitheringEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 60;
		}

		@Category("Necromancy")
		public static final class NecromancyEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "baseHealth", translation = CONFIG_BASE_HEALTH)
			@ConfigOption.Range(min = 1, max = 100)
			public static int baseHealth = 10;
		}

		@Category("Mana Split")
		public static final class ManaSplitEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("Copper Curse")
		public static final class CopperCurseEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 24000;

			@ConfigEntry(id = "effectDurationModifier", translation = CONFIG_EFFECT_DURATION_MODIFIER)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int effectDurationModifier = 12000;

			@ConfigEntry(id = "baseChanceToActivate", translation = CONFIG_BASE_ACTIVATION_CHANCE)
			@ConfigOption.Range(min = 0, max = 1)
			public static double baseChanceToActivate = 0.0625;
		}

		@Category("Discombobulate")
		public static final class DiscombobulateEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 60;

			@ConfigEntry(id = "effectDurationModifier", translation = CONFIG_EFFECT_DURATION_MODIFIER)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int effectDurationModifier = 15;
		}

		@Category("Stockpile")
		public static final class StockpileEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;

			@ConfigEntry(id = "effectDurationModifier", translation = CONFIG_EFFECT_DURATION_MODIFIER)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int effectDurationModifier = 30;

			@ConfigEntry(id = "damageNeededToIncrease", translation = CONFIG_DAMAGE_TO_INCREASE)
			@ConfigOption.Range(min = 0, max = 1000)
			public static float damageNeededToIncrease = 10f;
		}
	}

	@Category(value = "Support Effects", categories = {
		SupportEffects.HealEffectProperties.class,
		SupportEffects.DispelEffectProperties.class,
		SupportEffects.RegenerateEffectProperties.class,
		SupportEffects.FortifyEffectProperties.class,
		SupportEffects.HasteEffectProperties.class,
		SupportEffects.ManaShieldEffectProperties.class,
		SupportEffects.DangerSenseEffectProperties.class,
		SupportEffects.TemporalDilationEffectProperties.class
	})
	public static final class SupportEffects {
		@Category("Heal")
		public static final class HealEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseHealAmount", translation = CONFIG_BASE_HEAL_AMOUNT)
			@ConfigOption.Range(min = 0, max = 1000)
			public static float baseHealAmount = 3f;
		}

		@Category("Dispel")
		public static final class DispelEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("Regenerate")
		public static final class RegenerateEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;
		}

		@Category("Fortify")
		public static final class FortifyEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 500;
		}

		@Category("Haste")
		public static final class HasteEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 200;
		}

		@Category("Mana Shield")
		public static final class ManaShieldEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "baseLifeSpan", translation = CONFIG_BASE_LIFE_SPAN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseLifeSpan = 100;

			@ConfigEntry(id = "lifeSpanModifier", translation = CONFIG_LIFE_SPAN_MODIFIER)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int lifeSpanModifier = 40;
		}

		@Category("Danger Sense")
		public static final class DangerSenseEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;

			@ConfigEntry(id = "baseChanceToActivate", translation = CONFIG_BASE_ACTIVATION_CHANCE)
			@ConfigOption.Range(min = 0, max = 1)
			public static double baseChanceToActivate = 0.035;
		}

		@Category("Temporal Dilation")
		public static final class TemporalDilationEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}
	}

	@Category(value = "Utility Effects", categories = {
		UtilityEffects.BuildEffectProperties.class,
		UtilityEffects.AnonymityEffectProperties.class,
		UtilityEffects.MineEffectProperties.class,
		UtilityEffects.GrowthEffectProperties.class,
		UtilityEffects.ShrinkEffectProperties.class,
		UtilityEffects.EnlargeEffectProperties.class,
		UtilityEffects.SpatialRiftEffectProperties.class,
		UtilityEffects.WardingEffectProperties.class
	})
	public static final class UtilityEffects {
		@Category("Build")
		public static final class BuildEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseLifeSpan", translation = CONFIG_BASE_LIFE_SPAN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseLifeSpan = 220;
		}

		@Category("Anonymity")
		public static final class AnonymityEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 220;
		}

		@Category("Mine")
		public static final class MineEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("Growth")
		public static final class GrowthEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("Shrink")
		public static final class ShrinkEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseShrinkAmount", translation = CONFIG_BASE_SHRINK_AMOUNT)
			@ConfigOption.Range(min = 0, max = 1)
			public static float baseShrinkAmount = 0.5f;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;
		}

		@Category("Enlarge")
		public static final class EnlargeEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEnlargeAmount", translation = CONFIG_BASE_ENLARGE_AMOUNT)
			@ConfigOption.Range(min = 1, max = 2)
			public static float baseEnlargeAmount = 1.5f;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;
		}

		@Category("Spatial Rift")
		public static final class SpatialRiftEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "canSuckEntitiesIn", translation = CONFIG_CAN_SUCK_ENTITIES_IN)
			public static boolean canSuckEntitiesIn = true;

			@ConfigEntry(id = "portalGrowTime", translation = CONFIG_PORTAL_GROW_TIME)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int portalGrowTime = 100;

			@ConfigEntry(id = "pocketWidth", translation = CONFIG_POCKET_WIDTH)
			@Comment(value = "Needs to be an even number")
			@ConfigOption.Range(min = 2, max = 48)
			public static int pocketWidth = 24;

			@ConfigEntry(id = "pocketHeight", translation = CONFIG_POCKET_HEIGHT)
			@Comment(value = "Needs to be an even number")
			@ConfigOption.Range(min = 2, max = 48)
			public static int pocketHeight = 24;

			@ConfigEntry(id = "baseLifeSpan", translation = CONFIG_BASE_LIFE_SPAN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseLifeSpan = 300;
		}

		@Category("Warding")
		public static final class WardingEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "canBeRemovedByOthers", translation = CONFIG_CAN_BE_REMOVED_BY_OTHERS)
			public static boolean canBeRemovedByOthers = true;
		}
	}

	@Category(value = "Movement Effects", categories = {
		MovementEffects.PushEffectProperties.class,
		MovementEffects.PullEffectProperties.class,
		MovementEffects.LevitateEffectProperties.class,
		MovementEffects.SpeedEffectProperties.class,
		MovementEffects.TeleportEffectProperties.class,
		MovementEffects.BouncyEffectProperties.class,
		MovementEffects.FeatherEffectProperties.class,
		MovementEffects.FloatEffectProperties.class
	})
	public static final class MovementEffects {
		@Category("Push")
		public static final class PushEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "basePushStrength", translation = CONFIG_BASE_PUSH_STRENGTH)
			@ConfigOption.Range(min = 0, max = 10)
			public static double basePushStrength = 0.2;
		}

		@Category("Pull")
		public static final class PullEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "basePullStrength", translation = CONFIG_BASE_PULL_STRENGTH)
			@ConfigOption.Range(min = 0, max = 10)
			public static double basePullStrength = 0.2;
		}

		@Category("Levitate")
		public static final class LevitateEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 60;
		}

		@Category("Speed")
		public static final class SpeedEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 300;
		}

		@Category("Teleport")
		public static final class TeleportEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "baseTeleportDistance", translation = CONFIG_BASE_TELEPORT_DISTANCE)
			@ConfigOption.Range(min = 0, max = 32)
			public static double baseTeleportDistance = 5;
		}

		@Category("Bouncy")
		public static final class BouncyEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 220;
		}

		@Category("Feather")
		public static final class FeatherEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;
		}

		@Category("Float")
		public static final class FloatEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "redManaCost", translation = CONFIG_RED_MANA_COST)
			public static double redManaCost = 0;

			@ConfigEntry(id = "greenManaCost", translation = CONFIG_GREEN_MANA_COST)
			public static double greenManaCost = 0;

			@ConfigEntry(id = "blueManaCost", translation = CONFIG_BLUE_MANA_COST)
			public static double blueManaCost = 0;

			@ConfigEntry(id = "whiteManaCost", translation = CONFIG_WHITE_MANA_COST)
			public static double whiteManaCost = 0;

			@ConfigEntry(id = "blackManaCost", translation = CONFIG_BLACK_MANA_COST)
			public static double blackManaCost = 0;

			public static Map<ManaType, Double> manaCosts() {
				return Arcanus.constructManaMap(redManaCost, greenManaCost, blueManaCost, whiteManaCost, blackManaCost);
			}

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "removedUponTakingDamage", translation = CONFIG_REMOVED_ON_DAMAGE_TAKEN)
			public static boolean removedUponTakingDamage = true;

			@ConfigEntry(id = "baseEffectDuration", translation = CONFIG_BASE_EFFECT_DURATION)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 1200;
		}
	}
}
