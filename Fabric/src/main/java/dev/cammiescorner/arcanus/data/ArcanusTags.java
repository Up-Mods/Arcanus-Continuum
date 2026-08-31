package dev.cammiescorner.arcanus.data;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.util.ConventionsHelper;
import eu.pb4.trinkets.impl.TrinketsMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;

public class ArcanusTags {
	public static class Blocks {
		public static final TagKey<Block> WARDING_NOT_ALLOWED = TagKey.create(Registries.BLOCK, Arcanus.id("warding_not_allowed"));
	}

	public static class Items {
		public static final TagKey<Item> STAVES = TagKey.create(Registries.ITEM, Arcanus.id("staves"));
		public static final TagKey<Item> STAFF_CAPS = TagKey.create(Registries.ITEM, Arcanus.id("staff_caps"));
		public static final TagKey<Item> STAFF_CORES = TagKey.create(Registries.ITEM, Arcanus.id("staff_cores"));
		public static final TagKey<Item> ARCANIST_ARMOR = TagKey.create(Registries.ITEM, Arcanus.id("arcanist_armor"));
		public static final TagKey<Item> ARTIFICER_ARMOR = TagKey.create(Registries.ITEM, Arcanus.id("artificer_armor"));

		public static final TagKey<Item> C_FEATHERS = ConventionsHelper.tag(Registries.ITEM, "feathers");

		public static final TagKey<Item> BRACELET_HAND = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TrinketsMain.NAMESPACE, "hand/bracelet"));
		public static final TagKey<Item> BRACELET_OFFHAND = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TrinketsMain.NAMESPACE, "offhand/bracelet"));
		public static final TagKey<Item> SPELL_BOOK = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TrinketsMain.NAMESPACE, "legs/spell_book"));

		public static final TagKey<Item> REPAIRS_ARCANIST_ARMOR = TagKey.create(Registries.ITEM, Arcanus.id("repairs_arcanist_armor"));
		public static final TagKey<Item> REPAIRS_ARTIFICER_ARMOR = TagKey.create(Registries.ITEM, Arcanus.id("repairs_artificer_armor"));
		public static final TagKey<Item> REPAIRS_ALCHEMIST_ARMOR = TagKey.create(Registries.ITEM, Arcanus.id("repairs_alchemist_armor"));
		public static final TagKey<Item> REPAIRS_CULTIST_CLERIC_ARMOR = TagKey.create(Registries.ITEM, Arcanus.id("repairs_cultist_cleric_armor"));
		public static final TagKey<Item> REPAIRS_CULTIST_KNIGHT_ARMOR = TagKey.create(Registries.ITEM, Arcanus.id("repairs_cultist_knight_armor"));
	}

	public static class Biomes {
		public static final TagKey<Biome> C_HAS_VILLAGE = ConventionsHelper.tag(Registries.BIOME, "has_structure/village");
		public static final TagKey<Biome> HAS_WIZARD_TOWER = TagKey.create(Registries.BIOME, Arcanus.id("has_structure/wizard_tower"));
		public static final TagKey<Biome> IS_POCKET_DIMENSION = TagKey.create(Registries.BIOME, Arcanus.id("is_pocket_dimension"));

		public static final TagKey<Biome> CAN_SPAWN_RED_BEANZ = TagKey.create(Registries.BIOME, Arcanus.id("can_spawn_red_beanz"));
		public static final TagKey<Biome> CAN_SPAWN_BLUE_BEANZ = TagKey.create(Registries.BIOME, Arcanus.id("can_spawn_blue_beanz"));
		public static final TagKey<Biome> CAN_SPAWN_BLACK_BEANZ = TagKey.create(Registries.BIOME, Arcanus.id("can_spawn_black_beanz"));
		public static final TagKey<Biome> CAN_SPAWN_WHITE_BEANZ = TagKey.create(Registries.BIOME, Arcanus.id("can_spawn_white_beanz"));
		public static final TagKey<Biome> CAN_SPAWN_GREEN_BEANZ = TagKey.create(Registries.BIOME, Arcanus.id("can_spawn_green_beanz"));

		public static final TagKey<Biome> SUITABLE_FOR_EBONY = TagKey.create(Registries.BIOME, Arcanus.id("suitable_for_ebony"));
	}

	public static class Dimensions {
		public static final TagKey<DimensionType> WARDING_NOT_ALLOWED = TagKey.create(Registries.DIMENSION_TYPE, Arcanus.id("warding_not_allowed"));
	}

	public static class Enchantments {
		public static final TagKey<Enchantment> MANA_POOL_COMPATIBLE_WITH = TagKey.create(Registries.ENCHANTMENT, Arcanus.id("arcana_pool_compatible_with"));
	}

	public static class Entities {
		public static final TagKey<EntityType<?>> DISPELLABLE = TagKey.create(Registries.ENTITY_TYPE, Arcanus.id("dispellable"));
		public static final TagKey<EntityType<?>> SPATIAL_RIFT_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Arcanus.id("spatial_rift_immune"));
		public static final TagKey<EntityType<?>> TEMPORAL_DILATION_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Arcanus.id("temporal_dilation_immune"));
		public static final TagKey<EntityType<?>> RUNE_TRIGGER_IGNORED = TagKey.create(Registries.ENTITY_TYPE, Arcanus.id("magic_rune_ignored"));

		public static final TagKey<EntityType<?>> C_IMMOVABLE = ConventionsHelper.tag(Registries.ENTITY_TYPE, "immovable");
	}
}
