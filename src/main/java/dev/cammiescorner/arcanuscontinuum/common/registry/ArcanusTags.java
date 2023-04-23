package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class ArcanusTags {
	public static final TagKey<Item> STAVES = TagKey.of(Registry.ITEM.getKey(), Arcanus.id("staves"));
	public static final TagKey<Item> WIZARD_ARMOUR = TagKey.of(Registry.ITEM.getKey(), Arcanus.id("wizard_armor"));
}
