package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

public class SpellComponent {
	private final Weight weight;
	private final double manaCost;
	private final int coolDown;
	private final int minLevel;
	private String knownTranslationKey;
	private String unknownTranslationKey;

	public SpellComponent(Weight weight, double manaCost, int coolDown, int minLevel) {
		this.weight = weight;
		this.manaCost = manaCost;
		this.coolDown = coolDown;
		this.minLevel = minLevel;
	}

	public Weight getWeight() {
		return weight;
	}

	public double getManaCost() {
		return manaCost;
	}

	public int getCoolDown() {
		return coolDown;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public Identifier getTexture() {
		Identifier id = Arcanus.SPELL_COMPONENTS.getId(this);
		String extra = "";

		if(this instanceof SpellShape)
			extra = "shapes/";
		if(this instanceof SpellEffect)
			extra = "effects/";

		return new Identifier(id.getNamespace(), "textures/spell_components/" + extra + id.getPath() + ".png");
	}

	public String getTranslationKey(@Nullable PlayerEntity player) {
		Identifier id = Arcanus.SPELL_COMPONENTS.getId(this);

		if(unknownTranslationKey == null) {
			unknownTranslationKey = Util.createTranslationKey("spell_component", id) + ".unknown";
			knownTranslationKey = Util.createTranslationKey("spell_component", id);
		}

		return player != null && ArcanusComponents.hasComponent(player, this) ? knownTranslationKey : unknownTranslationKey;
	}
}
