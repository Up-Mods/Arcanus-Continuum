package dev.cammiescorner.arcanus.api.spell;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record SpellAspects(double potency, double arcanaCost, double coolDown) {
	public static final Codec<SpellAspects> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.DOUBLE.optionalFieldOf("Potency", 1d).forGetter(SpellAspects::potency),
		Codec.DOUBLE.optionalFieldOf("ManaCost", 1d).forGetter(SpellAspects::arcanaCost),
		Codec.DOUBLE.optionalFieldOf("CoolDown", 1d).forGetter(SpellAspects::coolDown)
	).apply(instance, SpellAspects::new));
	public static final StreamCodec<ByteBuf, SpellAspects> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.DOUBLE, SpellAspects::potency,
		ByteBufCodecs.DOUBLE, SpellAspects::arcanaCost,
		ByteBufCodecs.DOUBLE, SpellAspects::coolDown,
		SpellAspects::new
	);
}
