package dev.cammiescorner.arcanus.api.util;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.Map;

public class ArcanaModifiers {
	public static final Codec<ArcanaModifiers> CODEC = Codec.unboundedMap(PrimalArcana.CODEC, Codec.DOUBLE).xmap(ArcanaModifiers::of, ArcanaModifiers::asMap);
	public static final StreamCodec<ByteBuf, ArcanaModifiers> STREAM_CODEC = StreamCodec.ofMember((modifiers, buf) -> {
		for (PrimalArcana value : PrimalArcana.values())
			buf.writeDouble(modifiers.modifier(value));
	}, buf -> {
		var modifiers = new double[PrimalArcana.values().length];

		for(PrimalArcana primalArcana : PrimalArcana.values())
			modifiers[primalArcana.ordinal()] = buf.readDouble();

		return new ArcanaModifiers(modifiers);
	});

	private final double[] modifiers;

	@ApiStatus.Internal
	public ArcanaModifiers(double... modifiers) {
		Preconditions.checkArgument(modifiers.length == PrimalArcana.values().length, "Array size must be exactly %s", PrimalArcana.values().length);
		this.modifiers = modifiers;
	}

	public static ArcanaModifiers of(Map<PrimalArcana, Double> map) {
		var modifiers = new double[PrimalArcana.values().length];
		for(PrimalArcana primalArcana : PrimalArcana.values())
			modifiers[primalArcana.ordinal()] = map.getOrDefault(primalArcana, 1.0D);

		return new ArcanaModifiers(modifiers);
	}

	public static ArcanaModifiers empty() {
		var modifiers = new double[PrimalArcana.values().length];
		Arrays.fill(modifiers, 1.0D);
		return new ArcanaModifiers(modifiers);
	}

	public Object2DoubleMap<PrimalArcana> asMap() {
		Object2DoubleMap<PrimalArcana> map = new Object2DoubleArrayMap<>();

		for(PrimalArcana primalArcana : PrimalArcana.values())
			map.put(primalArcana, modifiers[primalArcana.ordinal()]);

		return map;
	}

	public double modifier(PrimalArcana primalArcana) {
		return modifiers[primalArcana.ordinal()];
	}
}
