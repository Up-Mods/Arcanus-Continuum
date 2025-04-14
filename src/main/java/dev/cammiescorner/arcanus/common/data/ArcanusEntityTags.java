package dev.cammiescorner.arcanus.common.data;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.util.ConventionsHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ArcanusEntityTags {
	public static final TagKey<EntityType<?>> DISPELLABLE = TagKey.create(Registries.ENTITY_TYPE, Arcanus.id("dispellable"));
	public static final TagKey<EntityType<?>> SPATIAL_RIFT_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Arcanus.id("spatial_rift_immune"));
	public static final TagKey<EntityType<?>> TEMPORAL_DILATION_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Arcanus.id("temporal_dilation_immune"));
	public static final TagKey<EntityType<?>> RUNE_TRIGGER_IGNORED = TagKey.create(Registries.ENTITY_TYPE, Arcanus.id("magic_rune_ignored"));

	public static final TagKey<EntityType<?>> C_IMMOVABLE = ConventionsHelper.tag(Registries.ENTITY_TYPE, "immovable");
}
