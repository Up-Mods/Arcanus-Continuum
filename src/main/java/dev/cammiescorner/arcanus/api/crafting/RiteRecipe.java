package dev.cammiescorner.arcanus.api.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.spell.mana.ManaCost;
import dev.cammiescorner.arcanus.api.util.XtraCodecs;
import dev.cammiescorner.arcanus.common.registry.ArcanusRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

// TODO add the blocks in a similar way to ShapedRecipePattern
public record RiteRecipe(List<Ingredient> itemIngredients, ManaCost manaCost, List<RiteResult> results) implements Recipe<RiteRecipeInput> {

	@Override
	public boolean matches(RiteRecipeInput input, Level level) {
		return manaCost.test(input) && (itemIngredients.isEmpty() || input.getStackedContents().canCraft(this, null));
	}

	@Override
	public ItemStack assemble(RiteRecipeInput input, HolderLookup.Provider registries) {
		// TODO consume items and mana etc.

		// TODO make an RiteResult#apply() method

		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= itemIngredients.size();
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider registries) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isSpecial() {
		// must return false here to not ignore recipe unlocking / doLimitedCrafting
		return false;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ArcanusRecipes.RITE_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ArcanusRecipes.RITE_RECIPE_TYPE.get();
	}

	public static class Serializer implements RecipeSerializer<RiteRecipe> {

		public static final MapCodec<RiteRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(RiteRecipe::itemIngredients),
			ManaCost.CODEC.fieldOf("manaCost").forGetter(RiteRecipe::manaCost),

			XtraCodecs.singleElementOrList(RiteResult.CODEC).fieldOf("result").forGetter(RiteRecipe::results)
		).apply(instance, RiteRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, RiteRecipe> STREAM_CODEC = StreamCodec.composite(
			Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
			RiteRecipe::itemIngredients,

			ManaCost.STREAM_CODEC,
			RiteRecipe::manaCost,

			RiteResult.STREAM_CODEC.apply(ByteBufCodecs.list()),
			RiteRecipe::results,

			RiteRecipe::new
		);

		@Override
		public MapCodec<RiteRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, RiteRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
