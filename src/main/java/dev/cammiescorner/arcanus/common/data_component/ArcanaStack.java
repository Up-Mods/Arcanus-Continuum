package dev.cammiescorner.arcanus.common.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ArcanaStack(Arcana arcana, double amount) {
	public static final ArcanaStack EMPTY = new ArcanaStack(ArcanusArcana.NIL.get());
	public static final Codec<ArcanaStack> CODEC = RecordCodecBuilder.create(arcanaStorage -> arcanaStorage.group(
		Arcana.CODEC.optionalFieldOf("arcana", ArcanusArcana.NIL.get()).forGetter(ArcanaStack::arcana),
		Codec.DOUBLE.optionalFieldOf("arcana_amount", 0d).forGetter(ArcanaStack::amount)
	).apply(arcanaStorage, ArcanaStack::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, ArcanaStack> STREAM_CODEC = StreamCodec.composite(
		Arcana.STREAM_CODEC, ArcanaStack::arcana,
		ByteBufCodecs.DOUBLE, ArcanaStack::amount,
		ArcanaStack::new
	);

	public ArcanaStack(Arcana arcana) {
		this(arcana, 0);
	}

	public boolean isEmpty() {
		return sameArcana(EMPTY) || amount() <= 0;
	}

	public boolean sameArcana(ArcanaStack otherStack) {
		return arcana() == otherStack.arcana();
	}
}
