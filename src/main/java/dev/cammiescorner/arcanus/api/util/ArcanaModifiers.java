package dev.cammiescorner.arcanus.api.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.common.util.XtraCodecs;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;
import java.util.stream.Collectors;

public class ArcanaModifiers {
	public static final Codec<ArcanaModifiers> CODEC = Codec.unboundedMap(Arcana.CODEC, Codec.DOUBLE).xmap(map -> {
		Object2DoubleArrayMap<PrimalArcana> arrayMap = new Object2DoubleArrayMap<>();

		map.forEach((arcana, aDouble) -> arrayMap.put((PrimalArcana) arcana, aDouble));

		return new ArcanaModifiers(arrayMap);
	}, ArcanaModifiers::asMap);
	public static final StreamCodec<RegistryFriendlyByteBuf, ArcanaModifiers> STREAM_CODEC = XtraCodecs.STUPID_FUCKING_STREAM_CODEC.apply(ByteBufCodecs.list()).map(pairs -> {
		Object2DoubleArrayMap<PrimalArcana> map = new Object2DoubleArrayMap<>();

		for(Pair<Arcana, Double> pair : pairs) {
			if(pair.getFirst() instanceof PrimalArcana primalArcana)
				map.put(primalArcana, pair.getSecond());
		}

		return new ArcanaModifiers(map);
	}, modifiers -> modifiers.modifiers.object2DoubleEntrySet().stream().map(entry -> new Pair<Arcana, Double>(entry.getKey(), entry.getDoubleValue())).toList());

	private final Object2DoubleArrayMap<PrimalArcana> modifiers;

	@ApiStatus.Internal
	public ArcanaModifiers(Object2DoubleArrayMap<PrimalArcana> modifiers) {
		this.modifiers = modifiers;
	}

	public static ArcanaModifiers empty() {
		return new ArcanaModifiers(new Object2DoubleArrayMap<>());
	}

	public Map<Arcana, Double> asMap() {
		return modifiers.object2DoubleEntrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Object2DoubleMap.Entry::getDoubleValue));
	}

	public double modifier(PrimalArcana primalArcana) {
		return modifiers.getDouble(primalArcana);
	}
}
