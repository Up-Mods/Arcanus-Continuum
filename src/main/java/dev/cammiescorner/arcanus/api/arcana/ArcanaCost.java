package dev.cammiescorner.arcanus.api.arcana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.util.ArcanaProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;
import java.util.function.Predicate;

public record ArcanaCost(double ignisArcana, double terraArcana, double aquaArcana, double aerArcana, double aetherArcana) implements Predicate<ArcanaProvider> {
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

	public Map<PrimalArcana, Double> arcanaCosts() {
		return Arcanus.constructArcanaMap(ignisArcana, terraArcana, aquaArcana, aerArcana, aetherArcana);
	}

	@Override
	public boolean test(ArcanaProvider arcanaProvider) {
		return arcanaProvider.getMana(PrimalArcana.IGNIS) >= ignisArcana
			&& arcanaProvider.getMana(PrimalArcana.TERRA) >= terraArcana
			&& arcanaProvider.getMana(PrimalArcana.AQUA) >= aquaArcana
			&& arcanaProvider.getMana(PrimalArcana.AER) >= aerArcana
			&& arcanaProvider.getMana(PrimalArcana.AETHER) >= aetherArcana;
	}
}
