package dev.cammiescorner.arcanus.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public class ConventionsHelper {

	public static final String COMMON_NAMESPACE = "c";

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(COMMON_NAMESPACE, path);
	}

	public static <T> TagKey<T> tag(ResourceKey<Registry<T>> registry, String path) {
		return TagKey.create(registry, id(path));
	}
}
