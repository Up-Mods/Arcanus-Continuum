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
	/** DO NOT DELETE OR DISABLE. WILL BREAK THE ENTIRE MOD. **/
	public static final RegistrySupplier<SpellComponent> EMPTY = SPELL_COMPONENTS.register("empty", () -> new SpellShape(true, Weight.NONE, 0, 0, 0) {
		@Override
		public void cast(@Nullable LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
			castNext(caster, castFrom, castSource, world, stack, spellGroups, groupIndex, potency);
		}
	});

	//-----Spell Forms-----//
	public static final RegistrySupplier<SpellShape> SELF = SPELL_COMPONENTS.register("self_shape", () -> new SelfSpellShape(selfShapeProperties.enabled, selfShapeProperties.weight, selfShapeProperties.manaCost, selfShapeProperties.manaMultiplier, selfShapeProperties.coolDown, selfShapeProperties.minimumLevel));
	public static final RegistrySupplier<SpellShape> TOUCH = SPELL_COMPONENTS.register("touch_shape", () -> new TouchSpellShape(touchShapeProperties.enabled, touchShapeProperties.weight, touchShapeProperties.manaCost, touchShapeProperties.manaMultiplier, touchShapeProperties.coolDown, touchShapeProperties.minimumLevel));
	public static final RegistrySupplier<SpellShape> PROJECTILE = SPELL_COMPONENTS.register("projectile_shape", () -> new ProjectileSpellShape(projectileShapeProperties.enabled, projectileShapeProperties.weight, projectileShapeProperties.manaCost, projectileShapeProperties.manaMultiplier, projectileShapeProperties.coolDown, projectileShapeProperties.minimumLevel));
	public static final RegistrySupplier<SpellShape> LOB = SPELL_COMPONENTS.register("lob_shape", () -> new ProjectileSpellShape(lobShapeProperties.enabled, lobShapeProperties.weight, lobShapeProperties.manaCost, lobShapeProperties.manaMultiplier, lobShapeProperties.coolDown, lobShapeProperties.minimumLevel));
	public static final RegistrySupplier<SpellShape> BOLT = SPELL_COMPONENTS.register("bolt_shape", () -> new BoltSpellShape(boltShapeProperties.enabled, boltShapeProperties.weight, boltShapeProperties.manaCost, boltShapeProperties.manaMultiplier, boltShapeProperties.coolDown, boltShapeProperties.minimumLevel));
	public static final RegistrySupplier<SpellShape> BEAM = SPELL_COMPONENTS.register("beam_shape", () -> new BeamSpellShape(beamShapeProperties.enabled, beamShapeProperties.weight, beamShapeProperties.manaCost, beamShapeProperties.manaMultiplier, beamShapeProperties.coolDown, beamShapeProperties.minimumLevel));
	public static final RegistrySupplier<SpellShape> RUNE = SPELL_COMPONENTS.register("rune_shape", () -> new RuneSpellShape(runeShapeProperties.enabled, runeShapeProperties.weight, runeShapeProperties.manaCost, runeShapeProperties.manaMultiplier, runeShapeProperties.coolDown, runeShapeProperties.minimumLevel));
	public static final RegistrySupplier<SpellShape> EXPLOSION = SPELL_COMPONENTS.register("explosion_shape", () -> new ExplosionSpellShape(explosionShapeProperties.enabled, explosionShapeProperties.weight, explosionShapeProperties.manaCost, explosionShapeProperties.manaMultiplier, explosionShapeProperties.coolDown, explosionShapeProperties.minimumLevel));
	public static final RegistrySupplier<SpellShape> AOE = SPELL_COMPONENTS.register("aoe_shape", () -> new AreaOfEffectSpellShape(aoeShapeProperties.enabled, aoeShapeProperties.weight, aoeShapeProperties.manaCost, aoeShapeProperties.manaMultiplier, aoeShapeProperties.coolDown, aoeShapeProperties.minimumLevel));
	public static final RegistrySupplier<SpellShape> SMITE = SPELL_COMPONENTS.register("smite_shape", () -> new SmiteSpellShape(smiteShapeProperties.enabled, smiteShapeProperties.weight, smiteShapeProperties.manaCost, smiteShapeProperties.manaMultiplier, smiteShapeProperties.coolDown, smiteShapeProperties.minimumLevel));
	public static final RegistrySupplier<SpellShape> GUARDIAN_ORB = SPELL_COMPONENTS.register("guardian_orb_shape", () -> new GuardianOrbSpellShape(guardianOrbShapeProperties.enabled, guardianOrbShapeProperties.weight, guardianOrbShapeProperties.manaCost, guardianOrbShapeProperties.manaMultiplier, guardianOrbShapeProperties.coolDown, guardianOrbShapeProperties.minimumLevel));

	//-----Spell Effects-----//
	public static final RegistrySupplier<SpellEffect> DAMAGE = SPELL_COMPONENTS.register("damage_effect", () -> new DamageSpellEffect(damageEffectProperties.enabled, SpellType.ATTACK, damageEffectProperties.weight, damageEffectProperties.manaCost, damageEffectProperties.coolDown, damageEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> FIRE = SPELL_COMPONENTS.register("fire_effect", () -> new FireSpellEffect(damageEffectProperties.enabled, SpellType.ATTACK, fireEffectProperties.weight, fireEffectProperties.manaCost, fireEffectProperties.coolDown, fireEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> ELECTRIC = SPELL_COMPONENTS.register("electric_effect", () -> new ElectricSpellEffect(electricEffectProperties.enabled, SpellType.ATTACK, electricEffectProperties.weight, electricEffectProperties.manaCost, electricEffectProperties.coolDown, electricEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> ICE = SPELL_COMPONENTS.register("ice_effect", () -> new IceSpellEffect(iceEffectProperties.enabled, SpellType.ATTACK, iceEffectProperties.weight, iceEffectProperties.manaCost, iceEffectProperties.coolDown, iceEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> VULNERABILITY = SPELL_COMPONENTS.register("vulnerability_effect", () -> new VulnerabilitySpellEffect(vulnerabilityEffectProperties.enabled, SpellType.ATTACK, vulnerabilityEffectProperties.weight, vulnerabilityEffectProperties.manaCost, vulnerabilityEffectProperties.coolDown, vulnerabilityEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> MANA_LOCK = SPELL_COMPONENTS.register("mana_lock_effect", () -> new ManaLockSpellEffect(manaLockEffectProperties.enabled, SpellType.ATTACK, manaLockEffectProperties.weight, manaLockEffectProperties.manaCost, manaLockEffectProperties.coolDown, manaLockEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> WITHERING = SPELL_COMPONENTS.register("withering_effect", () -> new WitheringSpellEffect(witheringEffectProperties.enabled, SpellType.ATTACK, witheringEffectProperties.weight, witheringEffectProperties.manaCost, witheringEffectProperties.coolDown, witheringEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> NECROMANCY = SPELL_COMPONENTS.register("necromancy_effect", () -> new NecromancySpellEffect(necromancyEffectProperties.enabled, SpellType.ATTACK, necromancyEffectProperties.weight, necromancyEffectProperties.manaCost, necromancyEffectProperties.coolDown, necromancyEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> MANA_SPLIT = SPELL_COMPONENTS.register("mana_split_effect", () -> new ManaSplitSpellEffect(manaSplitEffectProperties.enabled, SpellType.ATTACK, manaSplitEffectProperties.weight, manaSplitEffectProperties.manaCost, manaSplitEffectProperties.coolDown, manaSplitEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> COPPER_CURSE = SPELL_COMPONENTS.register("copper_curse_effect", () -> new CopperCurseSpellEffect(copperCurseEffectProperties.enabled, SpellType.ATTACK, copperCurseEffectProperties.weight, copperCurseEffectProperties.manaCost, copperCurseEffectProperties.coolDown, copperCurseEffectProperties.minimumLevel));

	public static final RegistrySupplier<SpellEffect> HEAL = SPELL_COMPONENTS.register("heal_effect", () -> new HealSpellEffect(healEffectProperties.enabled, SpellType.SUPPORT, healEffectProperties.weight, healEffectProperties.manaCost, healEffectProperties.coolDown, healEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> DISPEL = SPELL_COMPONENTS.register("dispel_effect", () -> new DispelSpellEffect(dispelEffectProperties.enabled, SpellType.SUPPORT, dispelEffectProperties.weight, dispelEffectProperties.manaCost, dispelEffectProperties.coolDown, dispelEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> REGENERATE = SPELL_COMPONENTS.register("regenerate_effect", () -> new RegenerateSpellEffect(regenerateEffectProperties.enabled, SpellType.SUPPORT, regenerateEffectProperties.weight, regenerateEffectProperties.manaCost, regenerateEffectProperties.coolDown, regenerateEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> FORTIFY = SPELL_COMPONENTS.register("fortify_effect", () -> new FortifySpellEffect(fortifyEffectProperties.enabled, SpellType.SUPPORT, fortifyEffectProperties.weight, fortifyEffectProperties.manaCost, fortifyEffectProperties.coolDown, fortifyEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> MANA_SHIELD = SPELL_COMPONENTS.register("mana_shield_effect", () -> new ManaShieldSpellEffect(manaShieldEffectProperties.enabled, SpellType.SUPPORT, manaShieldEffectProperties.weight, manaShieldEffectProperties.manaCost, manaShieldEffectProperties.coolDown, manaShieldEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> TEMPORAL_DILATION = SPELL_COMPONENTS.register("temporal_dilation_effect", () -> new TemporalDilationSpellEffect(temporalDilationEffectProperties.enabled, SpellType.SUPPORT, temporalDilationEffectProperties.weight, temporalDilationEffectProperties.manaCost, temporalDilationEffectProperties.coolDown, temporalDilationEffectProperties.minimumLevel));

	public static final RegistrySupplier<SpellEffect> PUSH = SPELL_COMPONENTS.register("push_effect", () -> new PushSpellEffect(pushEffectProperties.enabled, SpellType.UTILITY, pushEffectProperties.weight, pushEffectProperties.manaCost, pushEffectProperties.coolDown, pushEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> PULL = SPELL_COMPONENTS.register("pull_effect", () -> new PullSpellEffect(pullEffectProperties.enabled, SpellType.UTILITY, pullEffectProperties.weight, pullEffectProperties.manaCost, pullEffectProperties.coolDown, pullEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> POWER = SPELL_COMPONENTS.register("power_effect", () -> new PowerSpellEffect(powerEffectProperties.enabled, SpellType.UTILITY, powerEffectProperties.weight, powerEffectProperties.manaCost, powerEffectProperties.coolDown, powerEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> ANONYMITY = SPELL_COMPONENTS.register("anonymity_effect", () -> new AnonymitySpellEffect(anonymityEffectProperties.enabled, SpellType.UTILITY, anonymityEffectProperties.weight, anonymityEffectProperties.manaCost, anonymityEffectProperties.coolDown, anonymityEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> MINE = SPELL_COMPONENTS.register("mine_effect", () -> new MineSpellEffect(mineEffectProperties.enabled, SpellType.UTILITY, mineEffectProperties.weight, mineEffectProperties.manaCost, mineEffectProperties.coolDown, mineEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> GROWTH = SPELL_COMPONENTS.register("growth_effect", () -> new GrowthSpellEffect(growthEffectProperties.enabled, SpellType.UTILITY, growthEffectProperties.weight, growthEffectProperties.manaCost, growthEffectProperties.coolDown, growthEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> SHRINK = ArcanusCompat.PEHKUI.isEnabled() ? SPELL_COMPONENTS.register("shrink_effect", () -> new SizeChangeSpellEffect(shrinkEffectProperties.enabled, SpellType.UTILITY, shrinkEffectProperties.weight, shrinkEffectProperties.manaCost, shrinkEffectProperties.coolDown, shrinkEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> ENLARGE = ArcanusCompat.PEHKUI.isEnabled() ? SPELL_COMPONENTS.register("enlarge_effect", () -> new SizeChangeSpellEffect(enlargeEffectProperties.enabled, SpellType.UTILITY, enlargeEffectProperties.weight, enlargeEffectProperties.manaCost, enlargeEffectProperties.coolDown, enlargeEffectProperties.minimumLevel)) : null;
	public static final RegistrySupplier<SpellEffect> SPATIAL_POCKET = SPELL_COMPONENTS.register("spatial_pocket_effect", () -> new SpatialPocketSpellEffect(spatialPocketEffectProperties.enabled, SpellType.UTILITY, spatialPocketEffectProperties.weight, spatialPocketEffectProperties.manaCost, spatialPocketEffectProperties.coolDown, spatialPocketEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> WARDING = SPELL_COMPONENTS.register("warding_effect", () -> new WardingSpellEffect(wardingEffectProperties.enabled, SpellType.UTILITY, wardingEffectProperties.weight, wardingEffectProperties.manaCost, wardingEffectProperties.coolDown, wardingEffectProperties.minimumLevel));

	public static final RegistrySupplier<SpellEffect> BUILD = SPELL_COMPONENTS.register("build_effect", () -> new BuildSpellEffect(buildEffectProperties.enabled, SpellType.MOVEMENT, buildEffectProperties.weight, buildEffectProperties.manaCost, buildEffectProperties.coolDown, buildEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> LEVITATE = SPELL_COMPONENTS.register("levitate_effect", () -> new LevitateSpellEffect(levitateEffectProperties.enabled, SpellType.MOVEMENT, levitateEffectProperties.weight, levitateEffectProperties.manaCost, levitateEffectProperties.coolDown, levitateEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> SPEED = SPELL_COMPONENTS.register("speed_effect", () -> new SpeedSpellEffect(speedEffectProperties.enabled, SpellType.MOVEMENT, speedEffectProperties.weight, speedEffectProperties.manaCost, speedEffectProperties.coolDown, speedEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> TELEPORT = SPELL_COMPONENTS.register("teleport_effect", () -> new TeleportSpellEffect(temporalDilationEffectProperties.enabled, SpellType.MOVEMENT, teleportEffectProperties.weight, teleportEffectProperties.manaCost, teleportEffectProperties.coolDown, teleportEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> EXCHANGE = SPELL_COMPONENTS.register("exchange_effect", () -> new ExchangeSpellEffect(exchangeEffectProperties.enabled, SpellType.MOVEMENT, exchangeEffectProperties.weight, exchangeEffectProperties.manaCost, exchangeEffectProperties.coolDown, exchangeEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> BOUNCY = SPELL_COMPONENTS.register("bouncy_effect", () -> new BouncySpellEffect(bouncyEffectProperties.enabled, SpellType.MOVEMENT, bouncyEffectProperties.weight, bouncyEffectProperties.manaCost, bouncyEffectProperties.coolDown, bouncyEffectProperties.minimumLevel));
	public static final RegistrySupplier<SpellEffect> FEATHER = SPELL_COMPONENTS.register("feather_effect", () -> new FeatherSpellEffect(featherEffectProperties.enabled, SpellType.MOVEMENT, featherEffectProperties.weight, featherEffectProperties.manaCost, featherEffectProperties.coolDown, featherEffectProperties.minimumLevel));
}
