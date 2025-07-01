package dev.cammiescorner.arcanus.api.spells.components;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.spells.Weight;
import dev.cammiescorner.arcanus.api.spells.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import dev.upcraft.sparkweave.api.registry.RegistryHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.function.Supplier;

public class SpellComponent {
	public static final Codec<SpellComponent> CODEC = Codec.lazyInitialized(() -> RegistryHelper.getBuiltinRegistry(ArcanusRegistries.SPELL_COMPONENTS).byNameCodec());
	public static final String DISABLED_TRANSLATION_KEY = Util.makeDescriptionId("arcanus.spell_component", Arcanus.id("disabled"));
	private static final Component DISABLED_TRANSLATED_NAME = Component.translatable(DISABLED_TRANSLATION_KEY).withStyle(ChatFormatting.OBFUSCATED);
	private final Supplier<Boolean> isEnabled;
	private final Supplier<Weight> weight;
	private final Supplier<Map<ManaType, Double>> manaCost;
	private final Supplier<Integer> coolDown;
	private final Supplier<Boolean> procsOnce;
	private String translationKey;
	private ResourceLocation texture;

	public SpellComponent(Supplier<Boolean> isEnabled, Supplier<Weight> weight, Supplier<Map<ManaType, Double>> manaCost, Supplier<Integer> coolDown, Supplier<Boolean> procsOnce) {
		this.isEnabled = isEnabled;
		this.weight = weight;
		this.manaCost = manaCost;
		this.coolDown = coolDown;
		this.procsOnce = procsOnce;
	}

	public boolean isEnabled() {
		return isEnabled.get();
	}

	public Weight getWeight() {
		return weight.get();
	}

	public Map<ManaType, Double> getManaCost() {
		return Map.copyOf(manaCost.get());
	}

	public int getCoolDown() {
		return coolDown.get();
	}

	public boolean singleCastOnly() {
		return procsOnce.get();
	}

	public String getManaCostAsString(ManaType manaType) {
		return Arcanus.format(getManaCost().get(manaType));
	}

	public String getCoolDownAsString() {
		return Arcanus.format(getCoolDown() / 20d) + "s";
	}

	public ResourceLocation getTexture(Player player) {
		if(!ArcanusComponents.knowsSpellComponents(player, this))
			return Arcanus.id("unknown_component.png").withPrefix("textures/spell_components/");

		if(texture == null) {
			ResourceLocation id = ArcanusSpellComponents.REGISTRY.getKey(this);
			String extra = "";

			if(this instanceof SpellShape)
				extra = "shapes/";
			if(this instanceof SpellEffect)
				extra = "effects/";

			texture = id.withPrefix("textures/spell_components/" + extra).withSuffix(".png");//ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "" + extra + id.getPath() + ".png");
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
