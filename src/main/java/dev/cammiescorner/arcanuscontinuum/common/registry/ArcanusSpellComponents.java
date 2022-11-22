package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.ComponentScrollItem;
import dev.cammiescorner.arcanuscontinuum.common.spellcomponents.effects.DamageSpellEffect;
import dev.cammiescorner.arcanuscontinuum.common.spellcomponents.effects.HealSpellEffect;
import dev.cammiescorner.arcanuscontinuum.common.spellcomponents.shapes.SelfSpellShape;
import dev.cammiescorner.arcanuscontinuum.common.spellcomponents.shapes.TouchSpellShape;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ArcanusSpellComponents {
	//-----Spell Map-----//
	public static final LinkedHashMap<SpellComponent, Identifier> COMPONENTS = new LinkedHashMap<>();
	public static final LinkedHashMap<Item, Identifier> COMPONENT_SCROLLS = new LinkedHashMap<>();

	//-----Empty Spell-----//
	public static final SpellComponent EMPTY = create("empty", SpellComponent.EMPTY);

	//-----Spell Forms-----//
	public static final SpellShape SELF = create("self_shape", new SelfSpellShape(Weight.VERY_LIGHT, 0, 1, 1));
	public static final SpellShape TOUCH = create("touch_shape", new TouchSpellShape(Weight.VERY_LIGHT, 0.5, 5, 1));

	//-----Spell Effects-----//
	public static final SpellEffect DAMAGE = create("damage_effect", new DamageSpellEffect(ParticleTypes.DAMAGE_INDICATOR, Weight.NONE, 3, 5, 1));
	public static final SpellEffect HEAL = create("heal_effect", new HealSpellEffect(ParticleTypes.HEART, Weight.NONE, 5, 5, 1));

	//-----Registry-----//
	public static void register() {
		COMPONENTS.keySet().forEach(item -> Registry.register(Arcanus.SPELL_COMPONENTS, COMPONENTS.get(item), item));
		COMPONENT_SCROLLS.keySet().forEach(item -> Registry.register(Registry.ITEM, COMPONENT_SCROLLS.get(item), item));
	}

	private static <T extends SpellComponent> T create(String name, T component) {
		COMPONENTS.put(component, Arcanus.id(name));

		if(!name.equals("empty"))
			COMPONENT_SCROLLS.put(new ComponentScrollItem(component), Arcanus.id(name + "_scroll"));

		return component;
	}
}
