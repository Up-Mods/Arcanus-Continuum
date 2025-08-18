/***
 * Defines the base class and methods for Spell Effects, Groups, and Shapes.
 *
 * isEnabled - Gets whether a component is enabled from the config.
 *
 * getArcanaCost - gets the arcana cost
 *
 * singleCastOnly - returns if a component can only proc once when spell is cast.
 *
 * getArcanaCostAsString - makes the arcana cost printable in the console. Testing Purposes only.
 *
 * getTexture - Gets the texture and returns it as a variable.
 *
 * getTranslationKey - Used to return the id of individual objects when making lang files.
 *
 * getName - Used to return a human-readable name for an object.
 */


package dev.cammiescorner.arcanus.api.spell.components;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import dev.upcraft.sparkweave.api.registry.RegistryHelper;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class SpellComponent {
	public static final Codec<SpellComponent> CODEC = Codec.lazyInitialized(() -> RegistryHelper.getBuiltinRegistry(ArcanusRegistries.SPELL_COMPONENT).byNameCodec());
	public static final StreamCodec<RegistryFriendlyByteBuf, SpellComponent> STREAM_CODEC = ByteBufCodecs.registry(ArcanusRegistries.SPELL_COMPONENT);
	public static final String DISABLED_TRANSLATION_KEY = Util.makeDescriptionId("arcanus.spell_component", Arcanus.id("disabled"));
	private static final MutableComponent DISABLED_TRANSLATED_NAME = Component.translatable(DISABLED_TRANSLATION_KEY).withStyle(ChatFormatting.OBFUSCATED);
	private final Supplier<Boolean> isEnabled;
	private final Supplier<Object2DoubleArrayMap<PrimalArcana>> arcanaCost;
	private final Supplier<Boolean> procsOnce;
	private String translationKey;
	private ResourceLocation texture;

	public SpellComponent(Supplier<Boolean> isEnabled, Supplier<Object2DoubleArrayMap<PrimalArcana>> arcanaCost, Supplier<Boolean> procsOnce) {
		this.isEnabled = isEnabled;
		this.arcanaCost = arcanaCost;
		this.procsOnce = procsOnce;
	}

	public boolean isEnabled() {
		return isEnabled.get();
	}

	public Object2DoubleMap<PrimalArcana> getArcanaCost() {
		return arcanaCost.get().clone();
	}

	public boolean singleCastOnly() {
		return procsOnce.get();
	}

	public String getArcanaCostAsString(PrimalArcana primalArcana) {
		return Arcanus.format(getArcanaCost().getDouble(primalArcana));
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

	public MutableComponent getName() {
		if(!isEnabled())
			return DISABLED_TRANSLATED_NAME;

		return Component.translatable(getTranslationKey());
	}
}
