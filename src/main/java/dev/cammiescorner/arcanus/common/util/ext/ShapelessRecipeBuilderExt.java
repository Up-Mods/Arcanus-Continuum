package dev.cammiescorner.arcanus.common.util.ext;

import dev.cammiescorner.arcanus.common.compat.patchouli.ShapelessBookRecipeBuilder;
import net.minecraft.resources.ResourceLocation;

public interface ShapelessRecipeBuilderExt {

	default ShapelessBookRecipeBuilder asPatchouliBook(ResourceLocation bookId) {
		throw new UnsupportedOperationException();
	}
}
