package dev.cammiescorner.arcanus.common.data;

import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class ArcanusLootTables {

	public static final ResourceKey<LootTable> COMPENDIUM_ARCANUS = ResourceKey.create(Registries.LOOT_TABLE, Arcanus.id("compendium_arcanus"));
	public static final ResourceKey<LootTable> WIZARD_TOWER_CHEST = ResourceKey.create(Registries.LOOT_TABLE, Arcanus.id("chests/wizard_tower"));
	public static final ResourceKey<LootTable> WIZARD_TOWER_BOOKSHELF = ResourceKey.create(Registries.LOOT_TABLE, Arcanus.id("bookshelves/wizard_tower"));
}
