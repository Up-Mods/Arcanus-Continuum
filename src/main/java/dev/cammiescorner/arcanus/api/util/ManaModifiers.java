package dev.cammiescorner.arcanus.api.util;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.mana.ManaType;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;

import java.util.Arrays;
import java.util.Map;

public class ManaModifiers {
	public static final Codec<ManaModifiers> CODEC = Codec.unboundedMap(ManaType.CODEC, Codec.DOUBLE).xmap(ManaModifiers::new, ManaModifiers::asMap);

	private final double[] discounts = new double[ManaType.values().length];

	public ManaModifiers(Map<ManaType, Double> map) {
		Arrays.fill(discounts, 1d);

		for(ManaType manaType : ManaType.values())
			discounts[manaType.ordinal()] = map.getOrDefault(manaType, 1d);
	}

	public static ManaModifiers empty() {
		return new ManaModifiers(Map.of());
	}

	public Object2DoubleMap<ManaType> asMap() {
		Object2DoubleMap<ManaType> map = new Object2DoubleArrayMap<>();

		for(ManaType manaType : ManaType.values())
			map.put(manaType, discounts[manaType.ordinal()]);

		return map;
	}

	public double modifier(ManaType manaType) {
		return discounts[manaType.ordinal()];
	}
}
