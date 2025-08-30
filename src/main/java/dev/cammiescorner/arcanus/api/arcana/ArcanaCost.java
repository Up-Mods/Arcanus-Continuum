package dev.cammiescorner.arcanus.api.arcana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.util.PrimalArcanaProvider;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Predicate;

public record ArcanaCost(double ignisArcana, double terraArcana, double aquaArcana, double aerArcana, double aetherArcana) implements Predicate<PrimalArcanaProvider> {
	public static final Codec<ArcanaCost> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.DOUBLE.optionalFieldOf("ignis_arcana", 0d).forGetter(ArcanaCost::ignisArcana),
		Codec.DOUBLE.optionalFieldOf("terra_arcana", 0d).forGetter(ArcanaCost::terraArcana),
		Codec.DOUBLE.optionalFieldOf("aqua_arcana", 0d).forGetter(ArcanaCost::aquaArcana),
		Codec.DOUBLE.optionalFieldOf("aer_arcana", 0d).forGetter(ArcanaCost::aerArcana),
		Codec.DOUBLE.optionalFieldOf("aether_arcana", 0d).forGetter(ArcanaCost::aetherArcana)
	).apply(instance, ArcanaCost::new));

	public static final StreamCodec<ByteBuf, ArcanaCost> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.DOUBLE,
		ArcanaCost::ignisArcana,

		ByteBufCodecs.DOUBLE,
		ArcanaCost::terraArcana,

		ByteBufCodecs.DOUBLE,
		ArcanaCost::aquaArcana,

		ByteBufCodecs.DOUBLE,
		ArcanaCost::aerArcana,

		ByteBufCodecs.DOUBLE,
		ArcanaCost::aetherArcana,

		ArcanaCost::new
	);

	public Object2DoubleArrayMap<PrimalArcana> arcanaCosts() {
		return Arcanus.constructArcanaMap(ignisArcana, terraArcana, aquaArcana, aerArcana, aetherArcana);
	}

	@Override
	public boolean test(PrimalArcanaProvider primalArcanaProvider) {
		return primalArcanaProvider.getArcana(ArcanusArcana.IGNIS) >= ignisArcana
			&& primalArcanaProvider.getArcana(ArcanusArcana.TERRA) >= terraArcana
			&& primalArcanaProvider.getArcana(ArcanusArcana.AQUA) >= aquaArcana
			&& primalArcanaProvider.getArcana(ArcanusArcana.AER) >= aerArcana
			&& primalArcanaProvider.getArcana(ArcanusArcana.AETHER) >= aetherArcana;
	}
}
