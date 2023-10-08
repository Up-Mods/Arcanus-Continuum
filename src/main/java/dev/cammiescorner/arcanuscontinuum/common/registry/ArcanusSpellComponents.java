package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.*;
import dev.cammiescorner.arcanuscontinuum.common.compat.ArcanusCompat;
import dev.cammiescorner.arcanuscontinuum.common.spell_components.effects.*;
import dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.cammiescorner.arcanuscontinuum.common.compat.ArcanusConfig.*;

public class ArcanusSpellComponents {
	public static final RegistryHandler<SpellComponent> SPELL_COMPONENTS = RegistryHandler.create(Arcanus.SPELL_COMPONENTS_REGISTRY_KEY, Arcanus.MOD_ID);

	//-----Empty Spell-----//
	public static final RegistrySupplier<SpellComponent> EMPTY = SPELL_COMPONENTS.register("empty", () -> new SpellShape(Weight.NONE, 0, 0, 0) {
		@Override
		public void cast(@Nullable LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
			castNext(caster, castFrom, castSource, world, stack, spellGroups, groupIndex, potency);
		}
	});

	//FIXME handle disabled components differently, this is a mess
	//-----Spell Forms-----//
	public static final RegistrySupplier<SpellShape> SELF = selfShapeProperties.enabled ? SPELL_COMPONENTS.register("self_shape", () -> new SelfSpellShape(selfShapeProperties.weight, selfShapeProperties.manaCost, selfShapeProperties.manaMultiplier, selfShapeProperties.coolDown, selfShapeProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellShape> TOUCH = touchShapeProperties.enabled ? SPELL_COMPONENTS.register("touch_shape", () -> new TouchSpellShape(touchShapeProperties.weight, touchShapeProperties.manaCost, touchShapeProperties.manaMultiplier, touchShapeProperties.coolDown, touchShapeProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellShape> PROJECTILE = projectileShapeProperties.enabled ? SPELL_COMPONENTS.register("projectile_shape", () -> new ProjectileSpellShape(projectileShapeProperties.weight, projectileShapeProperties.manaCost, projectileShapeProperties.manaMultiplier, projectileShapeProperties.coolDown, projectileShapeProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellShape> LOB = lobShapeProperties.enabled ? SPELL_COMPONENTS.register("lob_shape", () -> new ProjectileSpellShape(lobShapeProperties.weight, lobShapeProperties.manaCost, lobShapeProperties.manaMultiplier, lobShapeProperties.coolDown, lobShapeProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellShape> BOLT = boltShapeProperties.enabled ? SPELL_COMPONENTS.register("bolt_shape", () -> new BoltSpellShape(boltShapeProperties.weight, boltShapeProperties.manaCost, boltShapeProperties.manaMultiplier, boltShapeProperties.coolDown, boltShapeProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellShape> BEAM = beamShapeProperties.enabled ? SPELL_COMPONENTS.register("beam_shape", () -> new BeamSpellShape(beamShapeProperties.weight, beamShapeProperties.manaCost, beamShapeProperties.manaMultiplier, beamShapeProperties.coolDown, beamShapeProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellShape> RUNE = runeShapeProperties.enabled ? SPELL_COMPONENTS.register("rune_shape", () -> new RuneSpellShape(runeShapeProperties.weight, runeShapeProperties.manaCost, runeShapeProperties.manaMultiplier, runeShapeProperties.coolDown, runeShapeProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellShape> EXPLOSION = explosionShapeProperties.enabled ? SPELL_COMPONENTS.register("explosion_shape", () -> new ExplosionSpellShape(explosionShapeProperties.weight, explosionShapeProperties.manaCost, explosionShapeProperties.manaMultiplier, explosionShapeProperties.coolDown, explosionShapeProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellShape> AOE = aoeShapeProperties.enabled ? SPELL_COMPONENTS.register("aoe_shape", () -> new AreaOfEffectSpellShape(aoeShapeProperties.weight, aoeShapeProperties.manaCost, aoeShapeProperties.manaMultiplier, aoeShapeProperties.coolDown, aoeShapeProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellShape> SMITE = smiteShapeProperties.enabled ? SPELL_COMPONENTS.register("smite_shape", () -> new SmiteSpellShape(smiteShapeProperties.weight, smiteShapeProperties.manaCost, smiteShapeProperties.manaMultiplier, smiteShapeProperties.coolDown, smiteShapeProperties.minimumLevel)) : null;

	//-----Spell Effects-----//
	public static final RegistrySupplier<SpellEffect> DAMAGE = damageEffectProperties.enabled ? SPELL_COMPONENTS.register("damage_effect", () -> new DamageSpellEffect(SpellType.ATTACK, damageEffectProperties.weight, damageEffectProperties.manaCost, damageEffectProperties.coolDown, damageEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> FIRE = fireEffectProperties.enabled ? SPELL_COMPONENTS.register("fire_effect", () -> new FireSpellEffect(SpellType.ATTACK, fireEffectProperties.weight, fireEffectProperties.manaCost, fireEffectProperties.coolDown, fireEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> ELECTRIC = electricEffectProperties.enabled ? SPELL_COMPONENTS.register("electric_effect", () -> new ElectricSpellEffect(SpellType.ATTACK, electricEffectProperties.weight, electricEffectProperties.manaCost, electricEffectProperties.coolDown, electricEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> ICE = iceEffectProperties.enabled ? SPELL_COMPONENTS.register("ice_effect", () -> new IceSpellEffect(SpellType.ATTACK, iceEffectProperties.weight, iceEffectProperties.manaCost, iceEffectProperties.coolDown, iceEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> VULNERABILITY = vulnerabilityEffectProperties.enabled ? SPELL_COMPONENTS.register("vulnerability_effect", () -> new VulnerabilitySpellEffect(SpellType.ATTACK, vulnerabilityEffectProperties.weight, vulnerabilityEffectProperties.manaCost, vulnerabilityEffectProperties.coolDown, vulnerabilityEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> MANA_LOCK = manaLockEffectProperties.enabled ? SPELL_COMPONENTS.register("mana_lock_effect", () -> new ManaLockSpellEffect(SpellType.ATTACK, manaLockEffectProperties.weight, manaLockEffectProperties.manaCost, manaLockEffectProperties.coolDown, manaLockEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> WITHERING = witheringEffectProperties.enabled ? SPELL_COMPONENTS.register("withering_effect", () -> new WitheringSpellEffect(SpellType.ATTACK, witheringEffectProperties.weight, witheringEffectProperties.manaCost, witheringEffectProperties.coolDown, witheringEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> NECROMANCY = necromancyEffectProperties.enabled ? SPELL_COMPONENTS.register("necromancy_effect", () -> new NecromancySpellEffect(SpellType.ATTACK, necromancyEffectProperties.weight, necromancyEffectProperties.manaCost, necromancyEffectProperties.coolDown, necromancyEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> MANA_SPLIT = manaSplitEffectProperties.enabled ? SPELL_COMPONENTS.register("mana_split_effect", () -> new ManaSplitSpellEffect(SpellType.ATTACK, manaSplitEffectProperties.weight, manaSplitEffectProperties.manaCost, manaSplitEffectProperties.coolDown, manaSplitEffectProperties.minimumLevel)) : null;

	public static final RegistrySupplier<SpellEffect> HEAL = healEffectProperties.enabled ? SPELL_COMPONENTS.register("heal_effect", () -> new HealSpellEffect(SpellType.HEAL, healEffectProperties.weight, healEffectProperties.manaCost, healEffectProperties.coolDown, healEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> DISPEL = dispelEffectProperties.enabled ? SPELL_COMPONENTS.register("dispel_effect", () -> new DispelSpellEffect(SpellType.HEAL, dispelEffectProperties.weight, dispelEffectProperties.manaCost, dispelEffectProperties.coolDown, dispelEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> REGENERATE = regenerateEffectProperties.enabled ? SPELL_COMPONENTS.register("regenerate_effect", () -> new RegenerateSpellEffect(SpellType.HEAL, regenerateEffectProperties.weight, regenerateEffectProperties.manaCost, regenerateEffectProperties.coolDown, regenerateEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> FORTIFY = fortifyEffectProperties.enabled ? SPELL_COMPONENTS.register("fortify_effect", () -> new FortifySpellEffect(SpellType.HEAL, fortifyEffectProperties.weight, fortifyEffectProperties.manaCost, fortifyEffectProperties.coolDown, fortifyEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> MANA_SHIELD = manaShieldEffectProperties.enabled ? SPELL_COMPONENTS.register("mana_shield_effect", () -> new ManaShieldSpellEffect(SpellType.HEAL, manaShieldEffectProperties.weight, manaShieldEffectProperties.manaCost, manaShieldEffectProperties.coolDown, manaShieldEffectProperties.minimumLevel)) : null;

	public static final RegistrySupplier<SpellEffect> PUSH = pushEffectProperties.enabled ? SPELL_COMPONENTS.register("push_effect", () -> new PushSpellEffect(SpellType.UTILITY, pushEffectProperties.weight, pushEffectProperties.manaCost, pushEffectProperties.coolDown, pushEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> PULL = pullEffectProperties.enabled ? SPELL_COMPONENTS.register("pull_effect", () -> new PullSpellEffect(SpellType.UTILITY, pullEffectProperties.weight, pullEffectProperties.manaCost, pullEffectProperties.coolDown, pullEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> POWER = powerEffectProperties.enabled ? SPELL_COMPONENTS.register("power_effect", () -> new PowerSpellEffect(SpellType.UTILITY, powerEffectProperties.weight, powerEffectProperties.manaCost, powerEffectProperties.coolDown, powerEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> ANONYMITY = anonymityEffectProperties.enabled ? SPELL_COMPONENTS.register("anonymity_effect", () -> new AnonymitySpellEffect(SpellType.UTILITY, anonymityEffectProperties.weight, anonymityEffectProperties.manaCost, anonymityEffectProperties.coolDown, anonymityEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> MINE = mineEffectProperties.enabled ? SPELL_COMPONENTS.register("mine_effect", () -> new MineSpellEffect(SpellType.UTILITY, mineEffectProperties.weight, mineEffectProperties.manaCost, mineEffectProperties.coolDown, mineEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> GROWTH = growthEffectProperties.enabled ? SPELL_COMPONENTS.register("growth_effect", () -> new GrowthSpellEffect(SpellType.UTILITY, growthEffectProperties.weight, growthEffectProperties.manaCost, growthEffectProperties.coolDown, growthEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> SHRINK = ArcanusCompat.PEHKUI.isEnabled() && shrinkEffectProperties.enabled ? SPELL_COMPONENTS.register("shrink_effect", () -> new SizeChangeSpellEffect(SpellType.UTILITY, shrinkEffectProperties.weight, shrinkEffectProperties.manaCost, shrinkEffectProperties.coolDown, shrinkEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> ENLARGE = ArcanusCompat.PEHKUI.isEnabled() && enlargeEffectProperties.enabled ? SPELL_COMPONENTS.register("enlarge_effect", () -> new SizeChangeSpellEffect(SpellType.UTILITY, enlargeEffectProperties.weight, enlargeEffectProperties.manaCost, enlargeEffectProperties.coolDown, enlargeEffectProperties.minimumLevel)) : null;

	public static final RegistrySupplier<SpellEffect> BUILD = buildEffectProperties.enabled ? SPELL_COMPONENTS.register("build_effect", () -> new BuildSpellEffect(SpellType.MOVEMENT, buildEffectProperties.weight, buildEffectProperties.manaCost, buildEffectProperties.coolDown, buildEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> LEVITATE = levitateEffectProperties.enabled ? SPELL_COMPONENTS.register("levitate_effect", () -> new LevitateSpellEffect(SpellType.MOVEMENT, levitateEffectProperties.weight, levitateEffectProperties.manaCost, levitateEffectProperties.coolDown, levitateEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> SPEED = speedEffectProperties.enabled ? SPELL_COMPONENTS.register("speed_effect", () -> new SpeedSpellEffect(SpellType.MOVEMENT, speedEffectProperties.weight, speedEffectProperties.manaCost, speedEffectProperties.coolDown, speedEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> TELEPORT = teleportEffectProperties.enabled ? SPELL_COMPONENTS.register("teleport_effect", () -> new TeleportSpellEffect(SpellType.MOVEMENT, teleportEffectProperties.weight, teleportEffectProperties.manaCost, teleportEffectProperties.coolDown, teleportEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> EXCHANGE = exchangeEffectProperties.enabled ? SPELL_COMPONENTS.register("exchange_effect", () -> new ExchangeSpellEffect(SpellType.MOVEMENT, exchangeEffectProperties.weight, exchangeEffectProperties.manaCost, exchangeEffectProperties.coolDown, exchangeEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> BOUNCY = bouncyEffectProperties.enabled ? SPELL_COMPONENTS.register("bouncy_effect", () -> new BouncySpellEffect(SpellType.MOVEMENT, bouncyEffectProperties.weight, bouncyEffectProperties.manaCost, bouncyEffectProperties.coolDown, bouncyEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> FEATHER = featherEffectProperties.enabled ? SPELL_COMPONENTS.register("feather_effect", () -> new FeatherSpellEffect(SpellType.MOVEMENT, featherEffectProperties.weight, featherEffectProperties.manaCost, featherEffectProperties.coolDown, featherEffectProperties.minimumLevel)) : null;
}
