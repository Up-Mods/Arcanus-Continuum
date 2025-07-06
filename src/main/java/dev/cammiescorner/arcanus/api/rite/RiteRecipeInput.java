package dev.cammiescorner.arcanus.api.rite;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.api.util.ManaProvider;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;

public interface RiteRecipeInput extends RecipeInput, ManaProvider {

	@Override
	ItemStack getItem(int index);

	@Override
	double getMana(ManaType type);

	/**
	 * @return how many {@linkplain ItemStack} slots this recipe input has, for use in {@linkplain RiteRecipeInput#getItem(int)}
	 */
	@Override
	int size();

	@Override
	boolean isEmpty();

	/**
	 * @see RiteRecipe#matches(RiteRecipeInput, Level)
	 */
	StackedContents getStackedContents();
}
