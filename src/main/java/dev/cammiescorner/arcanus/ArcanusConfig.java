package dev.cammiescorner.arcanus;

import com.teamresourceful.resourcefulconfig.api.annotations.*;
import dev.cammiescorner.arcanus.api.spells.Weight;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

@Config(Arcanus.MOD_ID)
public final class ArcanusConfig {
	@ConfigEntry(id = "castingSpeedHasCoolDown", translation = CONFIG_CASTING_HAS_SPEED_LIMIT)
	public static boolean castingSpeedHasCoolDown = false;

	@ConfigEntry(id = "sizeChangingIsPermanent", translation = CONFIG_SIZE_CHANGE_IS_PERMA)
	public static boolean sizeChangingIsPermanent = false;

	@Category("enchantments")
	public static final class Enchantments {
		@Category("manaPool")
		public static final class ManaPool {
			@ConfigEntry(id = "maxEnchantmentLevel", translation = CONFIG_MAX_ENCHANT_LEVEL)
			public static int maxLevel = 5;

			@ConfigEntry(id = "manaPerLevel", translation = CONFIG_EXTRA_MANA_PER_LEVEL)
			public static double manaPerLevel = 0.05;

			@ConfigEntry(id = "manaModifierOperation", translation = CONFIG_MANA_MODIFIER_OP)
			public static AttributeModifier.Operation manaModifierOperation = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
		}
	}

	@Category("spellShapeProperties")
	public static final class SpellShapes {
		@Category("selfShapeProperties")
		public static final class SelfShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_LIGHT;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 0.85;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 10;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 1;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("touchShapeProperties")
		public static final class TouchShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_LIGHT;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 15;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 1;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0.2;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("missileShapeProperties")
		public static final class MissileShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.LIGHT;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 10;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 3;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "projectileSpeed", translation = "config.arcanus.projectileSpeed")
			public static float projectileSpeed = 4f;

			@ConfigEntry(id = "baseLifeSpan", translation = "config.arcanus.baseLifeSpan")
			@ConfigOption.Range(min = 1, max = 24000)
			public static int baseLifeSpan = 20;
		}

		@Category("lobShapeProperties")
		public static final class LobShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.LIGHT;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 20;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 3;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "projectileSpeed", translation = "config.arcanus.projectileSpeed")
			public static float projectileSpeed = 2f;
		}

		@Category("boltShapeProperties")
		public static final class BoltShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.MEDIUM;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 15;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 5;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "range", translation = "config.arcanus.range")
			@ConfigOption.Range(min = 0, max = 32)
			public static double range = 6;
		}

		@Category("beamShapeProperties")
		public static final class BeamShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.MEDIUM;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1.25;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 30;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 5;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0.25;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "range", translation = "config.arcanus.range")
			@ConfigOption.Range(min = 0, max = 32)
			public static double range = 16;

			@ConfigEntry(id = "delay", translation = "config.arcanus.delay")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int delay = 40;
		}

		@Category("runeShapeProperties")
		public static final class RuneShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.HEAVY;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 50;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 7;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "delay", translation = "config.arcanus.delay")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int delay = 60;
		}

		@Category("burstShapeProperties")
		public static final class BurstShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_HEAVY;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1.25;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 60;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 7;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "radius", translation = "config.arcanus.burstShape.radius")
			public static float radius = 4f;
		}

		@Category("guidedShotShapeProperties")
		public static final class GuidedShotShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = false;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.MEDIUM;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 100;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 7;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("counterShapeProperties")
		public static final class CounterShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.HEAVY;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1.2;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 300;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 7;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;
		}

		@Category("aoeShapeProperties")
		public static final class AOEShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_HEAVY;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 4;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 60;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 9;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "baseLifeSpan", translation = "config.arcanus.baseLifeSpan")
			@ConfigOption.Range(min = 1, max = 24000)
			public static int baseLifeSpan = 100;

			@ConfigEntry(id = "timesToApplyEffects", translation = "config.arcanus.timesToApplyEffects")
			public static int timesToApplyEffects = 3;

			@ConfigEntry(id = "timesToCastNextShape", translation = "config.arcanus.timesToCastNextShape")
			public static int timesToCastNextShape = 3;
		}

		@Category("smiteShapeProperties")
		public static final class SmiteShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_HEAVY;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1.75;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 60;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 9;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0.5;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("entangledOrbShapeProperties")
		public static final class EntangledOrbShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_HEAVY;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 1.5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 100;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 10;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "maximumManaLock", translation = "config.arcanus.maximumManaLock")
			@ConfigOption.Range(min = 0, max = 1)
			public static double maximumManaLock = 0.5;

			@ConfigEntry(id = "baseManaDrain", translation = "config.arcanus.baseManaDrain")
			@ConfigOption.Range(min = 0, max = 200)
			public static double baseManaDrain = 3;
		}

		@Category("aggressorbShapeProperties")
		public static final class AggressorbShapeProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.VERY_HEAVY;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 0;

			@ConfigEntry(id = "manaMultiplier", translation = CONFIG_MANA_MULTIPLIER)
			public static double manaMultiplier = 0.8;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 200;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 0, max = 10)
			public static int minimumLevel = 10;

			@ConfigEntry(id = "potencyModifier", translation = CONFIG_POTENCY_MODIFIER)
			public static double potencyModifier = 0;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "maximumAggressorbs", translation = "config.arcanus.maximumAggressorbs")
			@ConfigOption.Range(min = 0, max = 16)
			public static int maximumAggressorbs = 6;

			@ConfigEntry(id = "aggressorbsPerCast", translation = "config.arcanus.aggressorbsPerCast")
			@ConfigOption.Range(min = 0, max = 16)
			public static int aggressorbsPerCast = 2;

			@ConfigEntry(id = "projectileSpeed", translation = "config.arcanus.projectileSpeed")
			public static float projectileSpeed = 3f;
		}
	}

	@Category("attackEffectsCategory")
	public static final class AttackEffects {
		@Category("damageEffectProperties")
		public static final class DamageEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 4;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 1;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseDamage", translation = "config.arcanus.baseDamage")
			@ConfigOption.Range(min = 0, max = 1000)
			public static float baseDamage = 1.5f;
		}

		@Category("fireEffectProperties")
		public static final class FireEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 2;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 2;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseTimeOnFire", translation = "config.arcanus.baseTimeOnFire")
			@ConfigOption.Range(min = 0, max = 100)
			public static int baseTimeOnFire = 3;
		}

		@Category("electricEffectProperties")
		public static final class ElectricEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 2;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 2;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseStunTime", translation = "config.arcanus.baseStunTime")
			@ConfigOption.Range(min = 0, max = 100)
			public static int baseStunTime = 2;

			@ConfigEntry(id = "wetEntityDamageMultiplier", translation = "config.arcanus.wetEntityDamageMultiplier")
			@ConfigOption.Range(min = 1, max = 1000)
			public static float wetEntityDamageMultiplier = 2f;
		}

		@Category("iceEffectProperties")
		public static final class IceEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 2;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 2;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseFreezingTime", translation = "config.arcanus.baseFreezingTime")
			@ConfigOption.Range(min = 0, max = 100)
			public static int baseFreezingTime = 20;
		}

		@Category("vulnerabilityEffectProperties")
		public static final class VulnerabilityEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 5;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 300;
		}

		@Category("manaLockEffectProperties")
		public static final class ManaLockEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 8;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 9;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 200;
		}

		@Category("witheringEffectProperties")
		public static final class WitheringEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 6.5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 7;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 60;
		}

		@Category("necromancyEffectProperties")
		public static final class NecromancyEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 8;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 9;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "baseHealth", translation = "config.arcanus.baseHealth")
			@ConfigOption.Range(min = 1, max = 100)
			public static int baseHealth = 10;
		}

		@Category("manaSplitEffectProperties")
		public static final class ManaSplitEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 6.5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 6;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("copperCurseEffectProperties")
		public static final class CopperCurseEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 8;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 8;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 24000;

			@ConfigEntry(id = "effectDurationModifier", translation = "config.arcanus.effectDurationModifier")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int effectDurationModifier = 12000;

			@ConfigEntry(id = "baseChanceToActivate", translation = "config.arcanus.baseChanceToActivate")
			@ConfigOption.Range(min = 0, max = 1)
			public static double baseChanceToActivate = 0.0625;
		}

		@Category("discombobulateEffectProperties")
		public static final class DiscombobulateEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 6;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 5;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 60;

			@ConfigEntry(id = "effectDurationModifier", translation = "config.arcanus.effectDurationModifier")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int effectDurationModifier = 15;
		}

		@Category("stockpileEffectProperties")
		public static final class StockpileEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 4.5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 6;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;

			@ConfigEntry(id = "effectDurationModifier", translation = "config.arcanus.effectDurationModifier")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int effectDurationModifier = 30;

			@ConfigEntry(id = "damageNeededToIncrease", translation = "config.arcanus.damageNeededToIncrease")
			@ConfigOption.Range(min = 0, max = 1000)
			public static float damageNeededToIncrease = 10f;
		}
	}

	@Category("supportEffectsCategory")
	public static final class SupportEffects {
		@Category("healEffectProperties")
		public static final class HealEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 1;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseHealAmount", translation = "config.arcanus.baseHealAmount")
			@ConfigOption.Range(min = 0, max = 1000)
			public static float baseHealAmount = 3f;
		}

		@Category("dispelEffectProperties")
		public static final class DispelEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 6;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("regenerateEffectProperties")
		public static final class RegenerateEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 6;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 7;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;
		}

		@Category("fortifyEffectProperties")
		public static final class FortifyEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 6.5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 5;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 500;
		}

		@Category("hasteEffectProperties")
		public static final class HasteEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 7;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 4;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 200;
		}

		@Category("manaShieldEffectProperties")
		public static final class ManaShieldEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 8;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 10;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "baseLifeSpan", translation = "config.arcanus.baseLifeSpan")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseLifeSpan = 100;

			@ConfigEntry(id = "lifeSpanModifier", translation = "config.arcanus.lifeSpanModifier")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int lifeSpanModifier = 40;
		}

		@Category("dangerSenseEffectProperties")
		public static final class DangerSenseEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 8;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;

			@ConfigEntry(id = "baseChanceToActivate", translation = "config.arcanus.baseChanceToActivate")
			@ConfigOption.Range(min = 0, max = 1)
			public static double baseChanceToActivate = 0.035;
		}

		@Category("temporalDilationEffectProperties")
		public static final class TemporalDilationEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 8;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 10;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}
	}

	@Category("utilityEffectsCategory")
	public static final class UtilityEffects {
		@Category("buildEffectProperties")
		public static final class BuildEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 7;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseLifeSpan", translation = "config.arcanus.baseLifeSpan")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseLifeSpan = 220;
		}

		@Category("powerEffectProperties")
		public static final class PowerEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 2;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 4;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "basePower", translation = "config.arcanus.basePower")
			@ConfigOption.Range(min = 0, max = 16)
			public static int basePower = 4;
		}

		@Category("anonymityEffectProperties")
		public static final class AnonymityEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 5;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 220;
		}

		@Category("mineEffectProperties")
		public static final class MineEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 7;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 4;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("growthEffectProperties")
		public static final class GrowthEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 6;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 4;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;
		}

		@Category("shrinkEffectProperties")
		public static final class ShrinkEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 2.5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 9;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseShrinkAmount", translation = "config.arcanus.baseShrinkAmount")
			@ConfigOption.Range(min = 0, max = 1)
			public static float baseShrinkAmount = 0.5f;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;
		}

		@Category("enlargeEffectProperties")
		public static final class EnlargeEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 2.5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 9;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEnlargeAmount", translation = "config.arcanus.baseEnlargeAmount")
			@ConfigOption.Range(min = 1, max = 2)
			public static float baseEnlargeAmount = 1.5f;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;
		}

		@Category("spatialRiftEffectProperties")
		public static final class SpatialRiftEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 10;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 7;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "canSuckEntitiesIn", translation = "config.arcanus.canSuckEntitiesIn")
			public static boolean canSuckEntitiesIn = true;

			@ConfigEntry(id = "portalGrowTime", translation = "config.arcanus.portalGrowTime")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int portalGrowTime = 100;

			@ConfigEntry(id = "pocketWidth", translation = "config.arcanus.pocketWidth")
			@Comment(value = "Needs to be an even number")
			@ConfigOption.Range(min = 2, max = 48)
			public static int pocketWidth = 24;

			@ConfigEntry(id = "pocketHeight", translation = "config.arcanus.pocketHeight")
			@Comment(value = "Needs to be an even number")
			@ConfigOption.Range(min = 2, max = 48)
			public static int pocketHeight = 24;

			@ConfigEntry(id = "baseLifeSpan", translation = "config.arcanus.baseLifeSpan")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseLifeSpan = 700;
		}

		@Category("wardingEffectProperties")
		public static final class WardingEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 4;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 6;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "canBeRemovedByOthers", translation = "config.arcanus.canBeRemovedByOthers")
			public static boolean canBeRemovedByOthers = true;
		}
	}

	@Category("movementEffectsCategory")
	public static final class MovementEffects {
		@Category("pushEffectProperties")
		public static final class PushEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 3;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "basePushAmount", translation = "config.arcanus.basePushAmount")
			@ConfigOption.Range(min = 0, max = 10)
			public static double basePushAmount = 0.2;
		}

		@Category("pullEffectProperties")
		public static final class PullEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 1;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 3;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "basePullAmount", translation = "config.arcanus.basePullAmount")
			@ConfigOption.Range(min = 0, max = 10)
			public static double basePullAmount = 0.2;
		}

		@Category("levitateEffectProperties")
		public static final class LevitateEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 3;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 6;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 60;
		}

		@Category("speedEffectProperties")
		public static final class SpeedEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 7;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 8;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 300;
		}

		@Category("teleportEffectProperties")
		public static final class TeleportEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 7.5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 10;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = true;

			@ConfigEntry(id = "baseTeleportDistance", translation = "config.arcanus.baseTeleportDistance")
			@ConfigOption.Range(min = 0, max = 32)
			public static double baseTeleportDistance = 5;
		}

		@Category("bouncyEffectProperties")
		public static final class BouncyEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 8;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 220;
		}

		@Category("featherEffectProperties")
		public static final class FeatherEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 8;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 100;
		}

		@Category("floatEffectProperties")
		public static final class FloatEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = true;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 10;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 5;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "removedUponTakingDamage", translation = "config.arcanus.removedUponTakingDamage")
			public static boolean removedUponTakingDamage = true;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 1200;
		}

		@Category("manaWingsEffectProperties")
		public static final class ManaWingsEffectProperties {
			@ConfigEntry(id = "enabled", translation = CONFIG_ENABLED)
			public static boolean enabled = false;

			@ConfigEntry(id = "weight", translation = CONFIG_WEIGHT)
			public static Weight weight = Weight.NONE;

			@ConfigEntry(id = "manaCost", translation = CONFIG_MANA_COST)
			@ConfigOption.Range(min = 0, max = 200)
			public static double manaCost = 8.5;

			@ConfigEntry(id = "coolDown", translation = CONFIG_COOL_DOWN)
			@ConfigOption.Range(min = 0, max = 24000)
			public static int coolDown = 0;

			@ConfigEntry(id = "minimumLevel", translation = CONFIG_MIN_LEVEL)
			@ConfigOption.Range(min = 1, max = 10)
			public static int minimumLevel = 10;

			@ConfigEntry(id = "procsOnce", translation = CONFIG_PROCS_ONCE)
			public static boolean procsOnce = false;

			@ConfigEntry(id = "removedUponTakingDamage", translation = "config.arcanus.removedUponTakingDamage")
			public static boolean removedUponTakingDamage = true;

			@ConfigEntry(id = "baseEffectDuration", translation = "config.arcanus.baseEffectDuration")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int baseEffectDuration = 200;

			@ConfigEntry(id = "effectDurationModifier", translation = "config.arcanus.effectDurationModifier")
			@ConfigOption.Range(min = 0, max = 24000)
			public static int effectDurationModifier = 100;
		}
	}
}
