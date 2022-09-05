package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.SpellBookItem;
import dev.cammiescorner.arcanuscontinuum.common.spells.healing_touch.HealingTouchSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.healing_touch.InvigoratingAuraSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.healing_touch.RejuvenationOrbSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.healing_touch.VampiricOrbSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.incinerate.FireballSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.incinerate.FlamePillarSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.incinerate.ImmolateSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.incinerate.IncinerateSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.magic_missile.ArcaneBarrageSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.magic_missile.BlinkSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.magic_missile.MagicMissileSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.magic_missile.PiercingArrowSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.mana_burst.ManaBurstSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.mana_burst.ManaSphereSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.mana_burst.ManaStrikeSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.spark.ChainLightningSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.spark.LightningBoltSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.spark.LightningSpeedSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.spark.SparkSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.withering_beam.CondemnedBonesSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.withering_beam.HellishSkullSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.withering_beam.WitherArmorSpell;
import dev.cammiescorner.arcanuscontinuum.common.spells.withering_beam.WitheringBeamSpell;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ArcanusSpells {
	//-----Spell Map-----//
	public static final LinkedHashMap<Spell, Identifier> SPELLS = new LinkedHashMap<>();
	public static final LinkedHashMap<Item, Identifier> SPELL_BOOKS = new LinkedHashMap<>();

	//-----Spells-----//
	public static final Spell EMPTY = create("empty", Spell.EMPTY);

	public static final Spell MAGIC_MISSILE = create("magic_missile", new MagicMissileSpell(null, Weight.VERY_LIGHT, 1, 20));
	public static final Spell PIERCING_ARROW = create("piercing_arrow", new PiercingArrowSpell(MAGIC_MISSILE, Weight.MEDIUM, 5, 40));
	public static final Spell ARCANE_BARRAGE = create("arcane_barrage", new ArcaneBarrageSpell(MAGIC_MISSILE, Weight.HEAVY, 20, 100));
	public static final Spell BLINK = create("blink", new BlinkSpell(MAGIC_MISSILE, Weight.MEDIUM, 15, 60));

	public static final Spell INCINERATE = create("incinerate", new IncinerateSpell(null, Weight.LIGHT, 0.75, 10));
	public static final Spell IMMOLATE = create("immolate", new ImmolateSpell(INCINERATE, Weight.VERY_LIGHT, 0.25, 1));
	public static final Spell FIREBALL = create("fireball", new FireballSpell(INCINERATE, Weight.MEDIUM, 5, 60));
	public static final Spell FLAME_PILLAR = create("flame_pillar", new FlamePillarSpell(INCINERATE, Weight.HEAVY, 15, 100));

	public static final Spell SPARK = create("spark", new SparkSpell(null, Weight.MEDIUM, 1, 15));
	public static final Spell LIGHTNING_BOLT = create("lightning_bolt", new LightningBoltSpell(SPARK, Weight.HEAVY, 15, 80));
	public static final Spell CHAIN_LIGHTNING = create("chain_lightning", new ChainLightningSpell(SPARK, Weight.LIGHT, 4, 30));
	public static final Spell LIGHTNING_SPEED = create("lightning_speed", new LightningSpeedSpell(SPARK, Weight.VERY_LIGHT, 10, 200));

	public static final Spell HEALING_TOUCH = create("healing_touch", new HealingTouchSpell(null, Weight.LIGHT, 5, 100));
	public static final Spell INVIGORATING_AURA = create("invigorating_aura", new InvigoratingAuraSpell(HEALING_TOUCH, Weight.MEDIUM, 10, 160));
	public static final Spell REJUVENATION_ORB = create("rejuvenation_orb", new RejuvenationOrbSpell(HEALING_TOUCH, Weight.HEAVY, 20, 300));
	public static final Spell VAMPIRIC_ORB = create("vampiric_orb", new VampiricOrbSpell(HEALING_TOUCH, Weight.HEAVY, 20, 200));

	public static final Spell WITHERING_BEAM = create("withering_beam", new WitheringBeamSpell(null, Weight.HEAVY, 2, 1));
	public static final Spell HELLISH_SKULL = create("hellish_skull", new HellishSkullSpell(WITHERING_BEAM, Weight.VERY_HEAVY, 12, 2));
	public static final Spell WITHER_ARMOUR = create("wither_armor", new WitherArmorSpell(WITHERING_BEAM, Weight.HEAVY, 25, 400));
	public static final Spell CONDEMNED_BONES = create("condemned_bones", new CondemnedBonesSpell(WITHERING_BEAM, Weight.HEAVY, 10, 100));

	public static final Spell MANA_BURST = create("mana_burst", new ManaBurstSpell(null, Weight.VERY_HEAVY, 20, 160));
	public static final Spell MANA_STRIKE = create("mana_strike", new ManaStrikeSpell(MANA_BURST, Weight.VERY_HEAVY, 50, 400));
	public static final Spell MANA_SPHERE = create("mana_sphere", new ManaSphereSpell(MANA_BURST, Weight.MEDIUM, 7, 60));
	public static final Spell MANA_BOLT = create("mana_bolt", new ManaBurstSpell(MANA_BURST, Weight.MEDIUM, 5, 40));

	//-----Registry-----//
	public static void register() {
		SPELLS.keySet().forEach(item -> Registry.register(Arcanus.SPELLS, SPELLS.get(item), item));
		SPELL_BOOKS.keySet().forEach(item -> Registry.register(Registry.ITEM, SPELL_BOOKS.get(item), item));
	}

	private static <T extends Spell> T create(String name, T spell) {
		SPELLS.put(spell, Arcanus.id(name));

		if(!name.equals("empty"))
			SPELL_BOOKS.put(new SpellBookItem(spell), Arcanus.id(name + "_spell_book"));

		return spell;
	}
}
