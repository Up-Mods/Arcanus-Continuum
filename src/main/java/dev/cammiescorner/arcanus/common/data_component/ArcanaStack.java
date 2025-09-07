package dev.cammiescorner.arcanus.common.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ArcanaStack(Arcana arcana, double amount, double maxAmount) {
	public static final ArcanaStack EMPTY = new ArcanaStack(ArcanusArcana.NIL.get(), 0);
	public static final Codec<ArcanaStack> CODEC = RecordCodecBuilder.create(arcanaStorage -> arcanaStorage.group(
		Arcana.CODEC.optionalFieldOf("arcana", ArcanusArcana.NIL.get()).forGetter(ArcanaStack::arcana),
		Codec.DOUBLE.optionalFieldOf("arcana_amount", 0d).forGetter(ArcanaStack::amount),
		Codec.DOUBLE.optionalFieldOf("max_arcana_amount", 64d).forGetter(ArcanaStack::maxAmount)
	).apply(arcanaStorage, ArcanaStack::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, ArcanaStack> STREAM_CODEC = StreamCodec.composite(
		Arcana.STREAM_CODEC, ArcanaStack::arcana,
		ByteBufCodecs.DOUBLE, ArcanaStack::amount,
		ByteBufCodecs.DOUBLE, ArcanaStack::maxAmount,
		ArcanaStack::new
	);

	public ArcanaStack(Arcana arcana, double amount) {
		this(arcana, amount, 64);
	}

	@Override
	public double amount() {
		return Math.clamp(amount, 0, maxAmount);
	}

	public boolean isEmpty() {
		return this == EMPTY;
	}
}
