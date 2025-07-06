package dev.cammiescorner.arcanus.api.spell.mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.util.ManaProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;
import java.util.function.Predicate;

public record ManaCost(double redMana, double greenMana, double blueMana, double whiteMana,
					   double blackMana) implements Predicate<ManaProvider> {
	public static final Codec<ManaCost> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.DOUBLE.optionalFieldOf("red_mana", 0d).forGetter(ManaCost::redMana),
		Codec.DOUBLE.optionalFieldOf("green_mana", 0d).forGetter(ManaCost::greenMana),
		Codec.DOUBLE.optionalFieldOf("blue_mana", 0d).forGetter(ManaCost::blueMana),
		Codec.DOUBLE.optionalFieldOf("white_mana", 0d).forGetter(ManaCost::whiteMana),
		Codec.DOUBLE.optionalFieldOf("black_mana", 0d).forGetter(ManaCost::blackMana)
	).apply(instance, ManaCost::new));

	public static final StreamCodec<ByteBuf, ManaCost> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.DOUBLE,
		ManaCost::redMana,

		ByteBufCodecs.DOUBLE,
		ManaCost::greenMana,

		ByteBufCodecs.DOUBLE,
		ManaCost::blueMana,

		ByteBufCodecs.DOUBLE,
		ManaCost::whiteMana,

		ByteBufCodecs.DOUBLE,
		ManaCost::blackMana,

		ManaCost::new
	);

	public Map<ManaType, Double> manaCosts() {
		return Arcanus.constructManaMap(redMana, greenMana, blueMana, whiteMana, blackMana);
	}

	@Override
	public boolean test(ManaProvider manaProvider) {
		return manaProvider.getMana(ManaType.RED) >= redMana
			&& manaProvider.getMana(ManaType.GREEN) >= greenMana
			&& manaProvider.getMana(ManaType.BLUE) >= blueMana
			&& manaProvider.getMana(ManaType.WHITE) >= whiteMana
			&& manaProvider.getMana(ManaType.BLACK) >= blackMana;
	}
}
