package dev.cammiescorner.arcanus.api.util;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.WeatheringCopper;
import org.joml.Vector2i;

import java.util.List;

public class XtraCodecs {
	public static final Codec<Vector2i> VEC2I_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.fieldOf("X").forGetter(Vector2i::x),
		Codec.INT.fieldOf("Y").forGetter(Vector2i::y)
	).apply(instance, Vector2i::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, WeatheringCopper.WeatherState> WEATHER_STATE_STREAM_CODEC = StreamCodec.of(FriendlyByteBuf::writeEnum, buffer -> buffer.readEnum(WeatheringCopper.WeatherState.class));

	public static <T> Codec<List<T>> singleElementOrList(Codec<T> elementCodec) {
		return Codec.either(elementCodec, ExtraCodecs.nonEmptyList(elementCodec.listOf())).xmap(either -> Either.unwrap(either.mapLeft(List::of)), list -> list.size() == 1 ? Either.left(list.getFirst()) : Either.right(list));
	}
}
