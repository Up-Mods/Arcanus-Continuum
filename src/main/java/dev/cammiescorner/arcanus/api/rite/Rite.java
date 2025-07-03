package dev.cammiescorner.arcanus.api.rite;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.spell.mana.ManaCost;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

// TODO add the blocks in a similar way to ShapedRecipePattern
public abstract class Rite {
	private final List<Ingredient> ingredients;
	private final ManaCost manaCost;

	public Rite(List<Ingredient> ingredients, ManaCost manaCost) {
		this.ingredients = ingredients;
		this.manaCost = manaCost;
	}

	public List<Ingredient> ingredients() {
		return ingredients;
	}

	public ManaCost manaCost() {
		return manaCost;
	}

	public abstract Codec<? extends Rite> codec();
}
