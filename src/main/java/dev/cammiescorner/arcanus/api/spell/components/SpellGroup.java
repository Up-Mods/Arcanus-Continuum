package dev.cammiescorner.arcanus.api.spell.components;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.api.spell.Weight;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanus.common.util.XtraCodecs;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.joml.Vector2i;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public record SpellGroup(SpellShape shape, List<SpellEffect> effects, List<Vector2i> positions) {
	public static final Codec<SpellGroup> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		SpellShape.CODEC.optionalFieldOf("Shape", SpellShape.empty()).forGetter(SpellGroup::shape),
		Codec.list(SpellEffect.CODEC).optionalFieldOf("Effects", List.of()).forGetter(SpellGroup::effects),
		Codec.list(XtraCodecs.VEC2I_CODEC).optionalFieldOf("Positions", List.of()).forGetter(SpellGroup::positions)
	).apply(instance, SpellGroup::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, SpellGroup> STREAM_CODEC = StreamCodec.composite(
		SpellComponent.STREAM_CODEC.map(spellComponent -> (SpellShape) spellComponent, Function.identity()), SpellGroup::shape,
		SpellComponent.STREAM_CODEC.map(spellComponent -> (SpellEffect) spellComponent, Function.identity()).apply(ByteBufCodecs.list()), SpellGroup::effects,
		XtraCodecs.VEC2I_STREAM_CODEC.apply(ByteBufCodecs.list()), SpellGroup::positions,
		SpellGroup::new
	);

	public static SpellGroup fromNbt(CompoundTag tag) {
		return SpellGroup.CODEC.decode(NbtOps.INSTANCE, tag).result().orElseGet(() -> Pair.of(new SpellGroup(SpellShape.empty(), List.of(), List.of()), tag)).getFirst();
	}

	public CompoundTag toNbt() {
		CompoundTag tag = new CompoundTag();

		SpellGroup.CODEC.encode(this, NbtOps.INSTANCE, tag);

		return tag;
	}

	public Weight getWeight() {
		return shape.getWeight();
	}

	public Object2DoubleArrayMap<PrimalArcana> getArcanaCost() {
		Object2DoubleArrayMap<PrimalArcana> cumulativeArcanaCost = new Object2DoubleArrayMap<>(shape().getArcanaCost());

		for(SpellEffect effect : effects)
			for(PrimalArcana primalArcana : effect.getArcanaCost().keySet())
				cumulativeArcanaCost.put(primalArcana, effect.getArcanaCost().getDouble(primalArcana) + cumulativeArcanaCost.get(primalArcana));

		return cumulativeArcanaCost;
	}

	public Stream<SpellComponent> getAllComponents() {
		return Stream.concat(Stream.of(shape), effects.stream());
	}

	public boolean isEmpty() {
		return ArcanusSpellComponents.EMPTY.is(shape);
	}
}
