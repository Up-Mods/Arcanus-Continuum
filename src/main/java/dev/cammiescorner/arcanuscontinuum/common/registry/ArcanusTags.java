package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

public class ArcanusTags {
	public static final TagKey<Item> STAVES = TagKey.of(Registries.ITEM.getKey(), Arcanus.id("staves"));
}
