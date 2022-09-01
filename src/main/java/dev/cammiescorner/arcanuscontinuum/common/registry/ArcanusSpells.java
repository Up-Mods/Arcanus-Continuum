package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.SpellBookItem;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.LinkedHashMap;

public class ArcanusSpells {
	//-----Spell Map-----//
	public static final LinkedHashMap<Spell, Identifier> SPELLS = new LinkedHashMap<>();

	//-----Spells-----//
	public static final Spell EMPTY = create("empty", new Spell(Weight.VERY_LIGHT, 0, 0) { @Override public void cast(LivingEntity caster, World world, StaffItem staff) { } });

	//-----Registry-----//
	public static void register() {
		SPELLS.keySet().forEach(item -> Registry.register(Arcanus.SPELLS, SPELLS.get(item), item));
	}

	private static <T extends Spell> T create(String name, T spell) {
		SPELLS.put(spell, Arcanus.id(name));

		if(!name.equals("empty"))
			ArcanusItems.ITEMS.put(new SpellBookItem(spell), Arcanus.id("spell_book_" + name));

		return spell;
	}
}
