package dev.cammiescorner.arcanus.common.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ArcanaStorage(Arcana arcana, double amount) {
	public static final Codec<ArcanaStorage> CODEC = RecordCodecBuilder.create(arcanaStorage -> arcanaStorage.group(
			Arcana.CODEC.optionalFieldOf("arcana", ArcanusArcana.NIL.get()).forGetter(ArcanaStorage::arcana),
			Codec.DOUBLE.optionalFieldOf("arcana_mount", 0d).forGetter(ArcanaStorage::amount)
	).apply(arcanaStorage, ArcanaStorage::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, ArcanaStorage> STREAM_CODEC = StreamCodec.composite(
		Arcana.STREAM_CODEC, ArcanaStorage::arcana,
		ByteBufCodecs.DOUBLE, ArcanaStorage::amount,
		ArcanaStorage::new
	);
}
