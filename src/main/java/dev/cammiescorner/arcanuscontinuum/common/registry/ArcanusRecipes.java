package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.recipes.SpellBindingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class ArcanusRecipes {
	public static final SpecialRecipeSerializer<SpellBindingRecipe> SPELL_BINDING = new SpecialRecipeSerializer<>(SpellBindingRecipe::new);

	public static void register() {
		Registry.register(Registry.RECIPE_SERIALIZER, Arcanus.id("spell_binding_recipe"), SPELL_BINDING);
	}
}
