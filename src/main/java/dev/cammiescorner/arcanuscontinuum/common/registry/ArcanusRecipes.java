package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.recipes.SpellBindingRecipe;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.RegistryKeys;

public class ArcanusRecipes {

	public static final RegistryHandler<RecipeSerializer<?>> RECIPE_SERIALIZERS = RegistryHandler.create(RegistryKeys.RECIPE_SERIALIZER, Arcanus.MOD_ID);
	public static final RegistrySupplier<SpecialRecipeSerializer<SpellBindingRecipe>> SPELL_BINDING = RECIPE_SERIALIZERS.register("spell_binding_recipe", () -> new SpecialRecipeSerializer<>(SpellBindingRecipe::new));

}
