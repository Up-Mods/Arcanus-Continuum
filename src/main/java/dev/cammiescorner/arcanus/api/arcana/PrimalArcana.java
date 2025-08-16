package dev.cammiescorner.arcanus.api.arcana;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
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

// TODO turn into registry stuff (kill me)
public enum PrimalArcana implements Arcana {
	IGNIS(ArcanusAttributes.IGNIS_ARCANA, ArcanusAttributes.IGNIS_ARCANA_REGEN, ChatFormatting.RED, Color.fromRGB(184, 28, 14), "ignis_arcana"),
	TERRA(ArcanusAttributes.TERRA_ARCANA, ArcanusAttributes.TERRA_ARCANA_REGEN, ChatFormatting.GREEN, Color.fromRGB(54, 124, 38), "terra_arcana"),
	AQUA(ArcanusAttributes.AQUA_ARCANA, ArcanusAttributes.AQUA_ARCANA_REGEN, ChatFormatting.BLUE, Color.fromRGB(6, 51, 141), "aqua_arcana"),
	AER(ArcanusAttributes.AER_ARCANA, ArcanusAttributes.AER_ARCANA_REGEN, ChatFormatting.WHITE, Color.fromRGB(255, 251, 213), "aer_arcana"),
	AETHER(ArcanusAttributes.AETHER_ARCANA, ArcanusAttributes.AETHER_ARCANA_REGEN, ChatFormatting.DARK_GRAY, Color.fromRGB(41, 29, 42), "aether_arcana");

	public static final Codec<PrimalArcana> CODEC = StringRepresentable.fromEnum(PrimalArcana::values);
	public static final StreamCodec<FriendlyByteBuf, PrimalArcana> STREAM_CODEC = StreamCodec.ofMember((type, buf) -> buf.writeEnum(type), buf -> buf.readEnum(PrimalArcana.class));
	final RegistrySupplier<Attribute> arcanaAttribute;
	final RegistrySupplier<Attribute> regenAttribute;
	final ChatFormatting formatting;
	final Color color;
	final String serializedName;

	PrimalArcana(RegistrySupplier<Attribute> arcanaAttribute, RegistrySupplier<Attribute> regenAttribute, ChatFormatting formatting, Color color, String name) {
		this.arcanaAttribute = arcanaAttribute;
		this.regenAttribute = regenAttribute;
		this.formatting = formatting;
		this.color = color;
		this.serializedName = name;
	}

	@Override
	public ChatFormatting formatting() {
		return formatting;
	}

	@Override
	public Color color() {
		return color;
	}

	@Override
	public String getSerializedName() {
		return serializedName;
	}

	public Holder<Attribute> getArcanaAttribute() {
		return arcanaAttribute.holder();
	}

	public Holder<Attribute> getRegenAttribute() {
		return regenAttribute.holder();
	}

	public double getMaxArcana(LivingEntity entity) {
		return entity.getAttributeValue(getRegenAttribute());
	}

	public static PrimalArcana getByName(String serializedName) {
		var optional = Arrays.stream(PrimalArcana.values()).filter(arcanaType -> arcanaType.getSerializedName().equals(serializedName)).findFirst();
		return optional.orElse(PrimalArcana.IGNIS);
	}
}
