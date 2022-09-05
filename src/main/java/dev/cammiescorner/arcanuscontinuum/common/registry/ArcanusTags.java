package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class ArcanusTags {
	public static final TagKey<Item> STAVES = TagKey.of(Registry.ITEM_KEY, Arcanus.id("staves"));
}
