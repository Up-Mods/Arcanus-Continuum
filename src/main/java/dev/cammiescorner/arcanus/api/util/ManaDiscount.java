package dev.cammiescorner.arcanus.api.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.spell.mana.ManaType;

public record ManaDiscount(ManaType manaType, double discount) {
	public static final Codec<ManaDiscount> CODEC = RecordCodecBuilder.create(manaDiscountInstance -> manaDiscountInstance.group(
		ManaType.CODEC.fieldOf("mana_type").forGetter(ManaDiscount::manaType),
		Codec.DOUBLE.fieldOf("discount").forGetter(ManaDiscount::discount)
	).apply(manaDiscountInstance, ManaDiscount::new));
}
