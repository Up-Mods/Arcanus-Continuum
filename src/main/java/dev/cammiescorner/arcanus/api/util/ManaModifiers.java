package dev.cammiescorner.arcanus.api.util;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.mana.ManaType;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.Map;

public class ManaModifiers {
	public static final Codec<ManaModifiers> CODEC = Codec.unboundedMap(ManaType.CODEC, Codec.DOUBLE).xmap(ManaModifiers::of, ManaModifiers::asMap);
	public static final StreamCodec<ByteBuf, ManaModifiers> STREAM_CODEC = StreamCodec.ofMember((modifiers, buf) -> {
		for (ManaType value : ManaType.values())
			buf.writeDouble(modifiers.modifier(value));
	}, buf -> {
		var modifiers = new double[ManaType.values().length];

		for(ManaType manaType : ManaType.values())
			modifiers[manaType.ordinal()] = buf.readDouble();

		return new ManaModifiers(modifiers);
	});

	private final double[] modifiers;

	@ApiStatus.Internal
	public ManaModifiers(double... modifiers) {
		Preconditions.checkArgument(modifiers.length == ManaType.values().length, "Array size must be exactly %s", ManaType.values().length);
		this.modifiers = modifiers;
	}

	public static ManaModifiers of(Map<ManaType, Double> map) {
		var modifiers = new double[ManaType.values().length];
		for(ManaType manaType : ManaType.values())
			modifiers[manaType.ordinal()] = map.getOrDefault(manaType, 1.0D);

		return new ManaModifiers(modifiers);
	}

	public static ManaModifiers empty() {
		var modifiers = new double[ManaType.values().length];
		Arrays.fill(modifiers, 1.0D);
		return new ManaModifiers(modifiers);
	}

	public Object2DoubleMap<ManaType> asMap() {
		Object2DoubleMap<ManaType> map = new Object2DoubleArrayMap<>();

		for(ManaType manaType : ManaType.values())
			map.put(manaType, modifiers[manaType.ordinal()]);

		return map;
	}

	public double modifier(ManaType manaType) {
		return modifiers[manaType.ordinal()];
	}
}
