package dev.cammiescorner.arcanus.api.spells;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

public enum ManaColor implements StringRepresentable {
	RED(ArcanusAttributes.RED_MANA, ChatFormatting.RED, Color.fromRGB(184, 28, 14), "RedMana"),
	GREEN(ArcanusAttributes.GREEN_MANA, ChatFormatting.GREEN, Color.fromRGB(54, 124, 38), "GreenMana"),
	BLUE(ArcanusAttributes.BLUE_MANA, ChatFormatting.BLUE, Color.fromRGB(6, 51, 141), "BlueMana"),
	WHITE(ArcanusAttributes.WHITE_MANA, ChatFormatting.WHITE, Color.fromRGB(255, 251, 213), "WhiteMana"),
	BLACK(ArcanusAttributes.BLACK_MANA, ChatFormatting.BLACK, Color.fromRGB(41, 29, 42), "BlackMana");

	public static final Codec<ManaColor> CODEC = StringRepresentable.fromValues(ManaColor::values);
	final Holder<Attribute> attribute;
	final ChatFormatting formatting;
	final Color color;
	final String serializedName;

	ManaColor(RegistrySupplier<Attribute> attributeSupplier, ChatFormatting formatting, Color color, String name) {
		this(attributeSupplier.holder(), formatting, color, name);
	}

	ManaColor(Holder<Attribute> attribute, ChatFormatting formatting, Color color, String name) {
		this.attribute = attribute;
		this.formatting = formatting;
		this.color = color;
		this.serializedName = name;
	}

	public Holder<Attribute> getAttribute() {
		return attribute;
	}

	public ChatFormatting getChatFormatting() {
		return formatting;
	}

	public Color getColor() {
		return color;
	}

	public double getMaxMana(LivingEntity entity) {
		return entity.getAttributeValue(attribute);
	}

	@Override
	public String getSerializedName() {
		return serializedName;
	}
}
