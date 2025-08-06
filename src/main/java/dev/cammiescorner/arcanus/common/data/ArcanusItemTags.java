package dev.cammiescorner.arcanus.common.data;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.util.ConventionsHelper;
import dev.emi.trinkets.TrinketsMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ArcanusItemTags {
	public static final TagKey<Item> COPPER_CURSE_IMMUNE = TagKey.create(Registries.ITEM, Arcanus.id("copper_curse_immune"));
	public static final TagKey<Item> STAVES = TagKey.create(Registries.ITEM, Arcanus.id("staves"));
	public static final TagKey<Item> ARCANIST_ARMOR = TagKey.create(Registries.ITEM, Arcanus.id("arcanist_armor"));

	public static final TagKey<Item> C_FEATHERS = ConventionsHelper.tag(Registries.ITEM, "feathers");

	public static final TagKey<Item> BRACELET_HAND = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TrinketsMain.MOD_ID, "hand/bracelet"));
	public static final TagKey<Item> BRACELET_OFFHAND = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TrinketsMain.MOD_ID, "offhand/bracelet"));
	public static final TagKey<Item> SPELL_BOOK = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TrinketsMain.MOD_ID, "legs/spell_book"));
}
