package dev.cammiescorner.arcanus.api.crafting;

import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.api.util.ArcanaProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface RiteRecipeInput extends RecipeInput, ArcanaProvider {
	@Override
	ItemStack getItem(int index);

	@Override
	double getMana(PrimalArcana type);

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

	Vec3 getOrigin();

	BlockPos getOriginBlockPos();

	Level getLevel();
}
