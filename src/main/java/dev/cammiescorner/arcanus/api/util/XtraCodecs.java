package dev.cammiescorner.arcanus.api.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.WeatheringCopper;
import org.joml.Vector2i;

public class XtraCodecs {
	public static final Codec<Vector2i> VEC2I_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.fieldOf("X").forGetter(Vector2i::x),
		Codec.INT.fieldOf("Y").forGetter(Vector2i::y)
	).apply(instance, Vector2i::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, Color> COLOR_STREAM_CODEC = StreamCodec.of((buffer, color) -> buffer.writeVarInt(color.asIntARGB()), buffer -> Color.fromARGB(buffer.readVarInt()));

	public static final StreamCodec<RegistryFriendlyByteBuf, WeatheringCopper.WeatherState> WEATHER_STATE_STREAM_CODEC = StreamCodec.of(FriendlyByteBuf::writeEnum, buffer -> buffer.readEnum(WeatheringCopper.WeatherState.class));
}
