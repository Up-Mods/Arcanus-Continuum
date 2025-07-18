package dev.cammiescorner.arcanus.api.spell.mana;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.Arrays;

public enum ManaType implements StringRepresentable {
	RED(ArcanusAttributes.RED_MANA, ArcanusAttributes.RED_MANA_REGEN, ChatFormatting.RED, Color.fromRGB(184, 28, 14), "red_mana", TranslationKeys.SPELL_BOOK_RED_MANA),
	GREEN(ArcanusAttributes.GREEN_MANA, ArcanusAttributes.GREEN_MANA_REGEN, ChatFormatting.GREEN, Color.fromRGB(54, 124, 38), "green_mana", TranslationKeys.SPELL_BOOK_GREEN_MANA),
	BLUE(ArcanusAttributes.BLUE_MANA, ArcanusAttributes.BLUE_MANA_REGEN, ChatFormatting.BLUE, Color.fromRGB(6, 51, 141), "blue_mana", TranslationKeys.SPELL_BOOK_BLUE_MANA),
	WHITE(ArcanusAttributes.WHITE_MANA, ArcanusAttributes.WHITE_MANA_REGEN, ChatFormatting.WHITE, Color.fromRGB(255, 251, 213), "white_mana", TranslationKeys.SPELL_BOOK_WHITE_MANA),
	BLACK(ArcanusAttributes.BLACK_MANA, ArcanusAttributes.BLACK_MANA_REGEN, ChatFormatting.DARK_GRAY, Color.fromRGB(41, 29, 42), "black_mana", TranslationKeys.SPELL_BOOK_BLACK_MANA);

	public static final Codec<ManaType> CODEC = StringRepresentable.fromEnum(ManaType::values);
	final Holder<Attribute> manaAttribute;
	final Holder<Attribute> regenAttribute;
	final ChatFormatting formatting;
	final Color color;
	final String serializedName;
	final String translationKey;

	ManaType(RegistrySupplier<Attribute> attributeSupplier, RegistrySupplier<Attribute> regenAttributeSupplier, ChatFormatting formatting, Color color, String name, String translationKey) {
		this(attributeSupplier.holder(), regenAttributeSupplier.holder(), formatting, color, name, translationKey);
	}

	ManaType(Holder<Attribute> manaAttribute, Holder<Attribute> regenAttribute, ChatFormatting formatting, Color color, String name, String translationKey) {
		this.manaAttribute = manaAttribute;
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

	public Holder<Attribute> getManaAttribute() {
		return manaAttribute;
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

	public double getMaxMana(LivingEntity entity) {
		return entity.getAttributeValue(manaAttribute);
	}

	public static ManaType getByName(String serializedName) {
		var optional = Arrays.stream(ManaType.values()).filter(manaType -> manaType.getSerializedName().equals(serializedName)).findFirst();
		return optional.orElse(ManaType.RED);
	}
}
