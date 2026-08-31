package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.data.ArcanusTags;
import dev.cammiescorner.arcanus.data.ConventionalTags;
import dev.cammiescorner.arcanus.registry.ArcanusEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class ArcanusEntityTagsProvider extends IntrinsicHolderTagsProvider<EntityType<?>> {

	@SuppressWarnings("deprecation")
	public ArcanusEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Registries.ENTITY_TYPE, lookupProvider, entityType -> entityType.builtInRegistryHolder().key());
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		tag(ArcanusTags.Entities.C_IMMOVABLE)
			.add(ArcanusEntities.AOE)
			.add(ArcanusEntities.BEAM)
			.add(ArcanusEntities.MANA_SHIELD)
			.add(ArcanusEntities.PORTAL)
			.add(ArcanusEntities.SMITE);

		tag(ArcanusTags.Entities.DISPELLABLE)
			.add(ArcanusEntities.STOCKPILE_ORB)
			.add(ArcanusEntities.AOE)
			.add(ArcanusEntities.MAGIC_ORB)
			.add(ArcanusEntities.MAGIC_RUNE)
			.add(ArcanusEntities.MANA_SHIELD)
			.add(ArcanusEntities.NECRO_SKELETON)
			.add(ArcanusEntities.PORTAL)
			.add(ArcanusEntities.TEMPORAL_DILATION_FIELD);

		tag(ArcanusTags.Entities.SPATIAL_RIFT_IMMUNE)
			.forceAddTag(ConventionalTags.Entities.BOSSES)
			.addOptionalTag(ArcanusTags.Entities.C_IMMOVABLE)
			.add(EntityType.AREA_EFFECT_CLOUD)
			.add(EntityType.BLOCK_DISPLAY)
			.add(EntityType.END_CRYSTAL)
			.add(EntityType.EVOKER_FANGS)
			.add(EntityType.FISHING_BOBBER)
			.add(EntityType.GLOW_ITEM_FRAME)
			.add(EntityType.INTERACTION)
			.add(EntityType.ITEM_DISPLAY)
			.add(EntityType.ITEM_FRAME)
			.add(EntityType.LEASH_KNOT)
			.add(EntityType.LIGHTNING_BOLT)
			.add(EntityType.MARKER)
			.add(EntityType.PAINTING)
			.add(EntityType.TEXT_DISPLAY)
			.add(ArcanusEntities.PORTAL);

		tag(ArcanusTags.Entities.TEMPORAL_DILATION_IMMUNE)
			.add(ArcanusEntities.TEMPORAL_DILATION_FIELD);

		tag(ArcanusTags.Entities.RUNE_TRIGGER_IGNORED)
			.addOptionalTag(ArcanusTags.Entities.C_IMMOVABLE);
	}
}
