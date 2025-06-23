package dev.cammiescorner.arcanus.common.items;

import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;

public class SpellBookItem extends Item {
	public SpellBookItem() {
		super(new Properties().stacksTo(1).component(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell())));
	}

	// TODO move a lot of the staff's stuff to here lmao
}
