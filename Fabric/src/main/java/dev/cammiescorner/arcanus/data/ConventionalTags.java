package dev.cammiescorner.arcanus.data;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class ConventionalTags {
	public static class Blocks {
		public static final TagKey<Block> MOVEMENT_RESTRICTED = cTag(Registries.BLOCK, "movement_restricted");
	}

	private static <T> TagKey<T> cTag(ResourceKey<Registry<T>> registry, String name) {
		return TagKey.create(registry, Identifier.fromNamespaceAndPath("c", name));
	}

	public class Entities {
		public static final TagKey<EntityType<?>> BOSSES = cTag(Registries.ENTITY_TYPE, "bosses");
	}
}
