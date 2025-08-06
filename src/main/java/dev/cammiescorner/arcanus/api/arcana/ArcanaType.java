package dev.cammiescorner.arcanus.api.arcana;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.Arrays;

public enum ArcanaType implements StringRepresentable {
	IGNIS(ArcanusAttributes.IGNIS_ARCANA, ArcanusAttributes.IGNIS_ARCANA_REGEN, ChatFormatting.RED, Color.fromRGB(184, 28, 14), "ignis_arcana", TranslationKeys.SPELL_BOOK_IGNIS_ARCANA),
	TERRA(ArcanusAttributes.TERRA_ARCANA, ArcanusAttributes.TERRA_ARCANA_REGEN, ChatFormatting.GREEN, Color.fromRGB(54, 124, 38), "terra_arcana", TranslationKeys.SPELL_BOOK_TERRA_ARCANA),
	AQUA(ArcanusAttributes.AQUA_ARCANA, ArcanusAttributes.AQUA_ARCANA_REGEN, ChatFormatting.BLUE, Color.fromRGB(6, 51, 141), "aqua_arcana", TranslationKeys.SPELL_BOOK_AQUA_ARCANA),
	AER(ArcanusAttributes.AER_ARCANA, ArcanusAttributes.AER_ARCANA_REGEN, ChatFormatting.WHITE, Color.fromRGB(255, 251, 213), "aer_arcana", TranslationKeys.SPELL_BOOK_AER_ARCANA),
	AETHER(ArcanusAttributes.AETHER_ARCANA, ArcanusAttributes.AETHER_ARCANA_REGEN, ChatFormatting.DARK_GRAY, Color.fromRGB(41, 29, 42), "aether_arcana", TranslationKeys.SPELL_BOOK_AETHER_ARCANA);

	public static final Codec<ArcanaType> CODEC = StringRepresentable.fromEnum(ArcanaType::values);
	public static final StreamCodec<FriendlyByteBuf, ArcanaType> STREAM_CODEC = StreamCodec.ofMember((type, buf) -> buf.writeEnum(type), buf -> buf.readEnum(ArcanaType.class));
	final Holder<Attribute> arcanaAttribute;
	final Holder<Attribute> regenAttribute;
	final ChatFormatting formatting;
	final Color color;
	final String serializedName;
	final String translationKey;

	ArcanaType(RegistrySupplier<Attribute> attributeSupplier, RegistrySupplier<Attribute> regenAttributeSupplier, ChatFormatting formatting, Color color, String name, String translationKey) {
		this(attributeSupplier.holder(), regenAttributeSupplier.holder(), formatting, color, name, translationKey);
	}

	ArcanaType(Holder<Attribute> arcanaAttribute, Holder<Attribute> regenAttribute, ChatFormatting formatting, Color color, String name, String translationKey) {
		this.arcanaAttribute = arcanaAttribute;
		this.regenAttribute = regenAttribute;
		this.formatting = formatting;
		this.color = color;
		this.serializedName = name;
		this.translationKey = translationKey;
	}

	@Override
	public String getSerializedName() {
		return serializedName;
	}

	public Holder<Attribute> getArcanaAttribute() {
		return arcanaAttribute;
	}

	public Holder<Attribute> getRegenAttribute() {
		return regenAttribute;
	}

	public ChatFormatting getChatFormatting() {
		return formatting;
	}

	public Color getColor() {
		return color;
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public double getMaxArcana(LivingEntity entity) {
		return entity.getAttributeValue(arcanaAttribute);
	}

	public static ArcanaType getByName(String serializedName) {
		var optional = Arrays.stream(ArcanaType.values()).filter(arcanaType -> arcanaType.getSerializedName().equals(serializedName)).findFirst();
		return optional.orElse(ArcanaType.IGNIS);
	}
}
