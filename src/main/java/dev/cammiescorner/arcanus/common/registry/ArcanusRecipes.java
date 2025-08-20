package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.crafting.RiteRecipe;
import dev.cammiescorner.arcanus.common.crafting.ArcanusStaffRecipe;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.*;

public class ArcanusRecipes {
	public static final RegistryHandler<RecipeType<?>> RECIPE_TYPES = RegistryHandler.create(Registries.RECIPE_TYPE, Arcanus.MOD_ID);
	public static final RegistryHandler<RecipeSerializer<?>> RECIPE_SERIALIZERS = RegistryHandler.create(Registries.RECIPE_SERIALIZER, Arcanus.MOD_ID);

	public static final RegistrySupplier<RecipeType<RiteRecipe>> RITE_RECIPE_TYPE = recipeType("rite");
	public static final RegistrySupplier<RiteRecipe.Serializer> RITE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("rite", RiteRecipe.Serializer::new);
	public static final RegistrySupplier<RecipeSerializer<ArcanusStaffRecipe>> STAFF_SERIALIZER = RECIPE_SERIALIZERS.register("staff_recipe", () -> new SimpleCraftingRecipeSerializer<>(ArcanusStaffRecipe::new));

	private static <T extends Recipe<? extends RecipeInput>> RegistrySupplier<RecipeType<T>> recipeType(String name) {
		return RECIPE_TYPES.register(name, () -> new RecipeType<>() {
			private final String id = Util.makeDescriptionId(Registries.RECIPE_TYPE.location().toShortLanguageKey(), Arcanus.id(name));

			@Override
			public String toString() {
				return id;
			}
		});
	}
}
