package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.common.spell_components.effects.*;
import dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.LinkedHashMap;

import static dev.cammiescorner.arcanuscontinuum.common.compat.ArcanusConfig.*;

public class ArcanusSpellComponents {
	//-----Spell Map-----//
	public static final LinkedHashMap<SpellComponent, Identifier> COMPONENTS = new LinkedHashMap<>();

	//-----Empty Spell-----//
	public static final SpellComponent EMPTY = create("empty", SpellShape.EMPTY);

	//-----Spell Forms-----//
	public static final SpellShape SELF = selfShapeProperties.enabled ? create("self_shape", new SelfSpellShape(selfShapeProperties.weight, selfShapeProperties.manaCost, selfShapeProperties.manaMultiplier, selfShapeProperties.coolDown, selfShapeProperties.minimumLevel)) : null;
	public static final SpellShape TOUCH = touchShapeProperties.enabled ? create("touch_shape", new TouchSpellShape(touchShapeProperties.weight, touchShapeProperties.manaCost, touchShapeProperties.manaMultiplier, touchShapeProperties.coolDown, touchShapeProperties.minimumLevel)) : null;
	public static final SpellShape PROJECTILE = projectileShapeProperties.enabled ? create("projectile_shape", new ProjectileSpellShape(projectileShapeProperties.weight, projectileShapeProperties.manaCost, projectileShapeProperties.manaMultiplier, projectileShapeProperties.coolDown, projectileShapeProperties.minimumLevel)) : null;
	public static final SpellShape LOB = lobShapeProperties.enabled ? create("lob_shape", new ProjectileSpellShape(lobShapeProperties.weight, lobShapeProperties.manaCost, lobShapeProperties.manaMultiplier, lobShapeProperties.coolDown, lobShapeProperties.minimumLevel)) : null;
	public static final SpellShape BOLT = boltShapeProperties.enabled ? create("bolt_shape", new BoltSpellShape(boltShapeProperties.weight, boltShapeProperties.manaCost, boltShapeProperties.manaMultiplier, boltShapeProperties.coolDown, boltShapeProperties.minimumLevel)) : null;
	public static final SpellShape BEAM = beamShapeProperties.enabled ? create("beam_shape", new BeamSpellShape(beamShapeProperties.weight, beamShapeProperties.manaCost, beamShapeProperties.manaMultiplier, beamShapeProperties.coolDown, beamShapeProperties.minimumLevel)) : null;
	public static final SpellShape RUNE = runeShapeProperties.enabled ? create("rune_shape", new RuneSpellShape(runeShapeProperties.weight, runeShapeProperties.manaCost, runeShapeProperties.manaMultiplier, runeShapeProperties.coolDown, runeShapeProperties.minimumLevel)) : null;
	public static final SpellShape EXPLOSION = explosionShapeProperties.enabled ? create("explosion_shape", new ExplosionSpellShape(explosionShapeProperties.weight, explosionShapeProperties.manaCost, explosionShapeProperties.manaMultiplier, explosionShapeProperties.coolDown, explosionShapeProperties.minimumLevel)) : null;
	public static final SpellShape AOE = aoeShapeProperties.enabled ? create("aoe_shape", new AreaOfEffectSpellShape(aoeShapeProperties.weight, aoeShapeProperties.manaCost, aoeShapeProperties.manaMultiplier, aoeShapeProperties.coolDown, aoeShapeProperties.minimumLevel)) : null;
	public static final SpellShape SMITE = smiteShapeProperties.enabled ? create("smite_shape", new SmiteSpellShape(smiteShapeProperties.weight, smiteShapeProperties.manaCost, smiteShapeProperties.manaMultiplier, smiteShapeProperties.coolDown, smiteShapeProperties.minimumLevel)) : null;

	//-----Spell Effects-----//
	public static final SpellEffect DAMAGE = damageEffectProperties.enabled ? create("damage_effect", new DamageSpellEffect(SpellType.ATTACK, damageEffectProperties.weight, damageEffectProperties.manaCost, damageEffectProperties.coolDown, damageEffectProperties.minimumLevel)) : null;
	public static final SpellEffect FIRE = fireEffectProperties.enabled ? create("fire_effect", new FireSpellEffect(SpellType.ATTACK, fireEffectProperties.weight, fireEffectProperties.manaCost, fireEffectProperties.coolDown, fireEffectProperties.minimumLevel)) : null;
	public static final SpellEffect ELECTRIC = electricEffectProperties.enabled ? create("electric_effect", new ElectricSpellEffect(SpellType.ATTACK, electricEffectProperties.weight, electricEffectProperties.manaCost, electricEffectProperties.coolDown, electricEffectProperties.minimumLevel)) : null;
	public static final SpellEffect ICE = iceEffectProperties.enabled ? create("ice_effect", new IceSpellEffect(SpellType.ATTACK, iceEffectProperties.weight, iceEffectProperties.manaCost, iceEffectProperties.coolDown, iceEffectProperties.minimumLevel)) : null;
	public static final SpellEffect VULNERABILITY = vulnerabilityEffectProperties.enabled ? create("vulnerability_effect", new VulnerabilitySpellEffect(SpellType.ATTACK, vulnerabilityEffectProperties.weight, vulnerabilityEffectProperties.manaCost, vulnerabilityEffectProperties.coolDown, vulnerabilityEffectProperties.minimumLevel)) : null;
	public static final SpellEffect MANA_LOCK = manaLockEffectProperties.enabled ? create("mana_lock_effect", new ManaLockSpellEffect(SpellType.ATTACK, manaLockEffectProperties.weight, manaLockEffectProperties.manaCost, manaLockEffectProperties.coolDown, manaLockEffectProperties.minimumLevel)) : null;
	public static final SpellEffect WITHERING = witheringEffectProperties.enabled ? create("withering_effect", new WitheringSpellEffect(SpellType.ATTACK, witheringEffectProperties.weight, witheringEffectProperties.manaCost, witheringEffectProperties.coolDown, witheringEffectProperties.minimumLevel)) : null;
	public static final SpellEffect NECROMANCY = necromancyEffectProperties.enabled ? create("necromancy_effect", new NecromancySpellEffect(SpellType.ATTACK, necromancyEffectProperties.weight, necromancyEffectProperties.manaCost, necromancyEffectProperties.coolDown, necromancyEffectProperties.minimumLevel)) : null;
	public static final SpellEffect MANA_SPLIT = manaSplitEffectProperties.enabled ? create("mana_split_effect", new ManaSplitSpellEffect(SpellType.ATTACK, manaSplitEffectProperties.weight, manaSplitEffectProperties.manaCost, manaSplitEffectProperties.coolDown, manaSplitEffectProperties.minimumLevel)) : null;

	public static final SpellEffect HEAL = healEffectProperties.enabled ? create("heal_effect", new HealSpellEffect(SpellType.HEAL, healEffectProperties.weight, healEffectProperties.manaCost, healEffectProperties.coolDown, healEffectProperties.minimumLevel)) : null;
	public static final SpellEffect DISPEL = dispelEffectProperties.enabled ? create("dispel_effect", new DispelSpellEffect(SpellType.HEAL, dispelEffectProperties.weight, dispelEffectProperties.manaCost, dispelEffectProperties.coolDown, dispelEffectProperties.minimumLevel)) : null;
	public static final SpellEffect REGENERATE = regenerateEffectProperties.enabled ? create("regenerate_effect", new RegenerateSpellEffect(SpellType.HEAL, regenerateEffectProperties.weight, regenerateEffectProperties.manaCost, regenerateEffectProperties.coolDown, regenerateEffectProperties.minimumLevel)) : null;
	public static final SpellEffect FORTIFY = fortifyEffectProperties.enabled ? create("fortify_effect", new FortifySpellEffect(SpellType.HEAL, fortifyEffectProperties.weight, fortifyEffectProperties.manaCost, fortifyEffectProperties.coolDown, fortifyEffectProperties.minimumLevel)) : null;
	public static final SpellEffect MANA_SHIELD = manaShieldEffectProperties.enabled ? create("mana_shield_effect", new ManaShieldSpellEffect(SpellType.HEAL, manaShieldEffectProperties.weight, manaShieldEffectProperties.manaCost, manaShieldEffectProperties.coolDown, manaShieldEffectProperties.minimumLevel)) : null;

	public static final SpellEffect PUSH = pushEffectProperties.enabled ? create("push_effect", new PushSpellEffect(SpellType.UTILITY, pushEffectProperties.weight, pushEffectProperties.manaCost, pushEffectProperties.coolDown, pushEffectProperties.minimumLevel)) : null;
	public static final SpellEffect PULL = pullEffectProperties.enabled ? create("pull_effect", new PullSpellEffect(SpellType.UTILITY, pullEffectProperties.weight, pullEffectProperties.manaCost, pullEffectProperties.coolDown, pullEffectProperties.minimumLevel)) : null;
	public static final SpellEffect POWER = powerEffectProperties.enabled ?  create("power_effect", new PowerSpellEffect(SpellType.UTILITY, powerEffectProperties.weight, powerEffectProperties.manaCost, powerEffectProperties.coolDown, powerEffectProperties.minimumLevel)) : null;
	public static final SpellEffect ANONYMITY = anonymityEffectProperties.enabled ? create("anonymity_effect", new AnonymitySpellEffect(SpellType.UTILITY, anonymityEffectProperties.weight, anonymityEffectProperties.manaCost, anonymityEffectProperties.coolDown, anonymityEffectProperties.minimumLevel)) : null;
	public static final SpellEffect MINE = mineEffectProperties.enabled ? create("mine_effect", new MineSpellEffect(SpellType.UTILITY, mineEffectProperties.weight, mineEffectProperties.manaCost, mineEffectProperties.coolDown, mineEffectProperties.minimumLevel)) : null;
	public static final SpellEffect GROWTH = growthEffectProperties.enabled ? create("growth_effect", new GrowthSpellEffect(SpellType.UTILITY, growthEffectProperties.weight, growthEffectProperties.manaCost, growthEffectProperties.coolDown, growthEffectProperties.minimumLevel)) : null;
	public static final SpellEffect SHRINK = QuiltLoader.isModLoaded("pehkui") && shrinkEffectProperties.enabled ? create("shrink_effect", new SizeChangeSpellEffect(SpellType.UTILITY, shrinkEffectProperties.weight, shrinkEffectProperties.manaCost, shrinkEffectProperties.coolDown, shrinkEffectProperties.minimumLevel)) : null;
	public static final SpellEffect ENLARGE = QuiltLoader.isModLoaded("pehkui") && enlargeEffectProperties.enabled ? create("enlarge_effect", new SizeChangeSpellEffect(SpellType.UTILITY, enlargeEffectProperties.weight, enlargeEffectProperties.manaCost, enlargeEffectProperties.coolDown, enlargeEffectProperties.minimumLevel)) : null;

	public static final SpellEffect BUILD = buildEffectProperties.enabled ? create("build_effect", new BuildSpellEffect(SpellType.MOVEMENT, buildEffectProperties.weight, buildEffectProperties.manaCost, buildEffectProperties.coolDown, buildEffectProperties.minimumLevel)) : null;
	public static final SpellEffect LEVITATE = levitateEffectProperties.enabled ? create("levitate_effect", new LevitateSpellEffect(SpellType.MOVEMENT, levitateEffectProperties.weight, levitateEffectProperties.manaCost, levitateEffectProperties.coolDown, levitateEffectProperties.minimumLevel)) : null;
	public static final SpellEffect SPEED = speedEffectProperties.enabled ? create("speed_effect", new SpeedSpellEffect(SpellType.MOVEMENT, speedEffectProperties.weight, speedEffectProperties.manaCost, speedEffectProperties.coolDown, speedEffectProperties.minimumLevel)) : null;
	public static final SpellEffect TELEPORT = teleportEffectProperties.enabled ? create("teleport_effect", new TeleportSpellEffect(SpellType.MOVEMENT, teleportEffectProperties.weight, teleportEffectProperties.manaCost, teleportEffectProperties.coolDown, teleportEffectProperties.minimumLevel)) : null;
	public static final SpellEffect EXCHANGE = exchangeEffectProperties.enabled ? create("exchange_effect", new ExchangeSpellEffect(SpellType.MOVEMENT, exchangeEffectProperties.weight, exchangeEffectProperties.manaCost, exchangeEffectProperties.coolDown, exchangeEffectProperties.minimumLevel)) : null;
	public static final SpellEffect BOUNCY = bouncyEffectProperties.enabled ? create("bouncy_effect", new BouncySpellEffect(SpellType.MOVEMENT, bouncyEffectProperties.weight, bouncyEffectProperties.manaCost, bouncyEffectProperties.coolDown, bouncyEffectProperties.minimumLevel)) : null;
	public static final SpellEffect FEATHER = featherEffectProperties.enabled ? create("feather_effect", new FeatherSpellEffect(SpellType.MOVEMENT, featherEffectProperties.weight, featherEffectProperties.manaCost, featherEffectProperties.coolDown, featherEffectProperties.minimumLevel)) : null;

	//-----Registry-----//
	public static void register() {
		COMPONENTS.keySet().forEach(item -> Registry.register(Arcanus.SPELL_COMPONENTS, COMPONENTS.get(item), item));
	}

	private static <T extends SpellComponent> T create(String name, T component) {
		COMPONENTS.put(component, Arcanus.id(name));

		return component;
	}
}
