package dev.cammiescorner.arcanus.api.spells;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class SpellComponent {
	public static final String DISABLED_TRANSLATION_KEY = Util.makeDescriptionId("arcanus.spell_component", Arcanus.id("disabled"));
	private static final Component DISABLED_TRANSLATED_NAME = Component.translatable(DISABLED_TRANSLATION_KEY).withStyle(ChatFormatting.OBFUSCATED);
	private final Supplier<Boolean> isEnabled;
	private final Supplier<Weight> weight;
	private final Supplier<Double> manaCost;
	private final Supplier<Integer> coolDown;
	private final Supplier<Integer> minLevel;
	private final Supplier<Boolean> procsOnce;
	private String translationKey;
	private ResourceLocation texture;

	public SpellComponent(Supplier<Boolean> isEnabled, Supplier<Weight> weight, Supplier<Double> manaCost, Supplier<Integer> coolDown, Supplier<Integer> minLevel, Supplier<Boolean> procsOnce) {
		this.isEnabled = isEnabled;
		this.weight = weight;
		this.manaCost = manaCost;
		this.coolDown = coolDown;
		this.minLevel = minLevel;
		this.procsOnce = procsOnce;
	}

	public boolean isEnabled() {
		return isEnabled.get();
	}

	public Weight getWeight() {
		return weight.get();
	}

	public double getManaCost() {
		return manaCost.get();
	}

	public int getCoolDown() {
		return coolDown.get();
	}

	public int getMinLevel() {
		return minLevel.get();
	}

	public boolean singleCastOnly() {
		return procsOnce.get();
	}

	public String getManaCostAsString() {
		return Arcanus.format(getManaCost());
	}

	public String getCoolDownAsString() {
		return Arcanus.format(getCoolDown() / 20d) + "s";
	}

	public ResourceLocation getTexture() {
		if(texture == null) {
			ResourceLocation id = ArcanusSpellComponents.REGISTRY.getKey(this);
			String extra = "";

			if(this instanceof SpellShape)
				extra = "shapes/";
			if(this instanceof SpellEffect)
				extra = "effects/";

			texture = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/spell_components/" + extra + id.getPath() + ".png");
		}

		return texture;
	}

	public String getTranslationKey() {
		if(translationKey == null)
			translationKey = Util.makeDescriptionId("arcanus.spell_component", ArcanusSpellComponents.REGISTRY.getKey(this));

		return translationKey;
	}

	public Component getName() {
		if(!isEnabled())
			return DISABLED_TRANSLATED_NAME;

		return Component.translatable(getTranslationKey());
	}
}
