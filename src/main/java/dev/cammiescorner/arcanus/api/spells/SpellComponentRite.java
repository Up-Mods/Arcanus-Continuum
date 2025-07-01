package dev.cammiescorner.arcanus.api.spells;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.spells.components.SpellComponent;
import dev.cammiescorner.arcanus.api.spells.mana.ManaCost;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

// TODO add the blocks in a similar way to ShapedRecipePattern
public record SpellComponentRite(List<Ingredient> ingredients, ManaCost manaCost, SpellComponent spellComponent) {
	public static final Codec<SpellComponentRite> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Ingredient.CODEC.listOf().optionalFieldOf("ingredients", List.of()).forGetter(SpellComponentRite::ingredients),
		ManaCost.CODEC.optionalFieldOf("mana_cost", new ManaCost(0, 0, 0, 0, 0)).forGetter(SpellComponentRite::manaCost),
		SpellComponent.CODEC.fieldOf("spell_component").forGetter(SpellComponentRite::spellComponent)
	).apply(instance, SpellComponentRite::new));
}
