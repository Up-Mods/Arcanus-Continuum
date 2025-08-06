package dev.cammiescorner.arcanus.api.util;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.Map;

public class ArcanaModifiers {
	public static final Codec<ArcanaModifiers> CODEC = Codec.unboundedMap(ArcanaType.CODEC, Codec.DOUBLE).xmap(ArcanaModifiers::of, ArcanaModifiers::asMap);
	public static final StreamCodec<ByteBuf, ArcanaModifiers> STREAM_CODEC = StreamCodec.ofMember((modifiers, buf) -> {
		for (ArcanaType value : ArcanaType.values())
			buf.writeDouble(modifiers.modifier(value));
	}, buf -> {
		var modifiers = new double[ArcanaType.values().length];

		for(ArcanaType arcanaType : ArcanaType.values())
			modifiers[arcanaType.ordinal()] = buf.readDouble();

		return new ArcanaModifiers(modifiers);
	});

	private final double[] modifiers;

	@ApiStatus.Internal
	public ArcanaModifiers(double... modifiers) {
		Preconditions.checkArgument(modifiers.length == ArcanaType.values().length, "Array size must be exactly %s", ArcanaType.values().length);
		this.modifiers = modifiers;
	}

	public static ArcanaModifiers of(Map<ArcanaType, Double> map) {
		var modifiers = new double[ArcanaType.values().length];
		for(ArcanaType arcanaType : ArcanaType.values())
			modifiers[arcanaType.ordinal()] = map.getOrDefault(arcanaType, 1.0D);

		return new ArcanaModifiers(modifiers);
	}

	public static ArcanaModifiers empty() {
		var modifiers = new double[ArcanaType.values().length];
		Arrays.fill(modifiers, 1.0D);
		return new ArcanaModifiers(modifiers);
	}

	public Object2DoubleMap<ArcanaType> asMap() {
		Object2DoubleMap<ArcanaType> map = new Object2DoubleArrayMap<>();

		for(ArcanaType arcanaType : ArcanaType.values())
			map.put(arcanaType, modifiers[arcanaType.ordinal()]);

		return map;
	}

	public double modifier(ArcanaType arcanaType) {
		return modifiers[arcanaType.ordinal()];
	}
}
