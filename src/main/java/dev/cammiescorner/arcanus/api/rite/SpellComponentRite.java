package dev.cammiescorner.arcanus.api.rite;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.api.spell.mana.ManaCost;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class SpellComponentRite extends Rite {
	public static final Codec<SpellComponentRite> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Ingredient.CODEC.listOf().optionalFieldOf("ingredients", List.of()).forGetter(SpellComponentRite::ingredients),
		ManaCost.CODEC.optionalFieldOf("mana_cost", new ManaCost(0, 0, 0, 0, 0)).forGetter(SpellComponentRite::manaCost),
		SpellComponent.CODEC.fieldOf("spell_component").forGetter(SpellComponentRite::spellComponent)
	).apply(instance, SpellComponentRite::new));
	private final SpellComponent spellComponent;

	public SpellComponentRite(List<Ingredient> ingredients, ManaCost manaCost, SpellComponent spellComponent) {
		super(ingredients, manaCost);
		this.spellComponent = spellComponent;
	}

	@Override
	public Codec<? extends Rite> codec() {
		return CODEC;
	}

	public SpellComponent spellComponent() {
		return spellComponent;
	}
}
