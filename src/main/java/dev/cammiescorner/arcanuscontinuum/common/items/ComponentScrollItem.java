package dev.cammiescorner.arcanuscontinuum.common.items;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import net.minecraft.item.Item;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ComponentScrollItem extends Item {
	public final SpellComponent component;

	public ComponentScrollItem(SpellComponent component) {
		super(new QuiltItemSettings().group(Arcanus.ITEM_GROUP).maxCount(16));
		this.component = component;
	}
}
