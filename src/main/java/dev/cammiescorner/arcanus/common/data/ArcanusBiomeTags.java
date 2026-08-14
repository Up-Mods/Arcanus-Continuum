package dev.cammiescorner.arcanus.common.data;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.util.ConventionsHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ArcanusBiomeTags {
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
