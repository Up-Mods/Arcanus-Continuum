package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.*;
import dev.cammiescorner.arcanuscontinuum.common.spell_components.effects.*;
import dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;

public class ArcanusSpellComponents {
	//-----Spell Map-----//
	public static final LinkedHashMap<SpellComponent, Identifier> COMPONENTS = new LinkedHashMap<>();

	//-----Empty Spell-----//
	public static final SpellComponent EMPTY = create("empty", SpellShape.EMPTY);

	//-----Spell Forms-----//
	public static final SpellShape SELF = create("self_shape", new SelfSpellShape(Weight.VERY_LIGHT, 0, 1, 1));
	public static final SpellShape TOUCH = create("touch_shape", new TouchSpellShape(Weight.VERY_LIGHT, 0.5, 5, 1));
	public static final SpellShape PROJECTILE = create("projectile_shape", new ProjectileSpellShape(Weight.VERY_LIGHT, 0.5, 5, 1));
	public static final SpellShape LOB = create("lob_shape", new ProjectileSpellShape(Weight.VERY_LIGHT, 0.5, 5, 1));
	public static final SpellShape BOLT = create("bolt_shape", new HitscanSpellShape(Weight.VERY_LIGHT, 0.5, 5, 1));
	public static final SpellShape BEAM = create("beam_shape", new HitscanSpellShape(Weight.VERY_LIGHT, 0.5, 5, 1)); // TODO charge + lock-on
	public static final SpellShape RUNE = create("rune_shape", new RuneSpellShape(Weight.VERY_LIGHT, 0.5, 5, 1));
	public static final SpellShape SMITE = create("smite_shape", new SmiteSpellShape(Weight.VERY_LIGHT, 0.5, 5, 1));
	public static final SpellShape AOE = create("aoe_shape", new TouchSpellShape(Weight.VERY_LIGHT, 0.5, 5, 1)); //TODO entity
	public static final SpellShape EXPLOSION = create("explosion_shape", new ExplosionSpellShape(Weight.VERY_LIGHT, 0.5, 5, 1));

	//-----Spell Effects-----//
	public static final SpellEffect DAMAGE = create("damage_effect", new DamageSpellEffect(SpellType.ATTACK, ParticleTypes.CRIT, Weight.NONE, 3, 5, 1));
	public static final SpellEffect FIRE = create("fire_effect", new FireSpellEffect(SpellType.ATTACK, ParticleTypes.FLAME, Weight.NONE, 5, 5, 1));
	public static final SpellEffect ELECTRIC = create("electric_effect", new HealSpellEffect(SpellType.ATTACK, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect ICE = create("ice_effect", new IceSpellEffect(SpellType.ATTACK, ParticleTypes.SNOWFLAKE, Weight.NONE, 5, 5, 1));
	public static final SpellEffect VULNERABILITY = create("vulnerability_effect", new VulnerabilitySpellEffect(SpellType.ATTACK, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect MANA_LOCK = create("mana_lock_effect", new ManaLockSpellEffect(SpellType.ATTACK, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect WITHERING = create("withering_effect", new WitheringSpellEffect(SpellType.ATTACK, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect NECROMANCY = create("necromancy_effect", new HealSpellEffect(SpellType.ATTACK, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect MANA_SPLIT = create("mana_split_effect", new ManaSplitSpellEffect(SpellType.ATTACK, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));

	public static final SpellEffect HEAL = create("heal_effect", new HealSpellEffect(SpellType.HEAL, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect DISPEL = create("dispel_effect", new DispelSpellEffect(SpellType.HEAL, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect REGENERATE = create("regenerate_effect", new RegenerateSpellEffect(SpellType.HEAL, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect FORTIFY = create("fortify_effect", new FortifySpellEffect(SpellType.HEAL, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));

	public static final SpellEffect PUSH = create("push_effect", new PushSpellEffect(SpellType.UTILITY, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect PULL = create("pull_effect", new PullSpellEffect(SpellType.UTILITY, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect POWER = create("power_effect", new PowerSpellEffect(SpellType.UTILITY, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect ANONYMITY = create("anonymity_effect", new HealSpellEffect(SpellType.UTILITY, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect MINE = create("mine_effect", new MineSpellEffect(SpellType.UTILITY, ParticleTypes.DAMAGE_INDICATOR, Weight.NONE, 5, 5, 1));
	public static final SpellEffect GROWTH = create("growth_effect", new GrowthSpellEffect(SpellType.UTILITY, ParticleTypes.HAPPY_VILLAGER, Weight.NONE, 5, 5, 1));

	public static final SpellEffect BUILD = create("build_effect", new BuildSpellEffect(SpellType.MOVEMENT, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect LEVITATE = create("levitate_effect", new LevitateSpellEffect(SpellType.MOVEMENT, ParticleTypes.CLOUD, Weight.NONE, 5, 5, 1));
	public static final SpellEffect TELEPORT = create("teleport_effect", new TeleportSpellEffect(SpellType.MOVEMENT, ParticleTypes.PORTAL, Weight.NONE, 5, 5, 1));
	public static final SpellEffect EXCHANGE = create("exchange_effect", new ExchangeSpellEffect(SpellType.MOVEMENT, ParticleTypes.END_ROD, Weight.NONE, 5, 5, 1));
	public static final SpellEffect BOUNCY = create("bouncy_effect", new HealSpellEffect(SpellType.MOVEMENT, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));
	public static final SpellEffect FEATHER = create("feather_effect", new FeatherSpellEffect(SpellType.MOVEMENT, ParticleTypes.HEART, Weight.NONE, 5, 5, 1));

	//-----Registry-----//
	public static void register() {
		COMPONENTS.keySet().forEach(item -> Registry.register(Arcanus.SPELL_COMPONENTS, COMPONENTS.get(item), item));
	}

	private static <T extends SpellComponent> T create(String name, T component) {
		COMPONENTS.put(component, Arcanus.id(name));

		return component;
	}
}
