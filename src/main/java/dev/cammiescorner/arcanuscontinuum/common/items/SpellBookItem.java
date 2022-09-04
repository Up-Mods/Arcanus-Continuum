package dev.cammiescorner.arcanuscontinuum.common.items;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import net.minecraft.item.Item;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class SpellBookItem extends Item {
	private final Spell spell;

	public SpellBookItem(Spell spell) {
		super(new QuiltItemSettings().group(Arcanus.ITEM_GROUP).maxCount(1));
		this.spell = spell;
	}

	public Spell getSpell() {
		return spell;
	}
}
