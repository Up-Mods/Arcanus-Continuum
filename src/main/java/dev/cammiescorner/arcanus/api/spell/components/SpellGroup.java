package dev.cammiescorner.arcanus.api.spell.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.spell.Weight;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.common.util.XtraCodecs;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		SpellShape shape = (SpellShape) ArcanusSpellComponents.REGISTRY.get(ResourceLocation.parse(tag.getString("Shape")));
		List<SpellEffect> effects = new ArrayList<>();
		List<Vector2i> positions = new ArrayList<>();
		ListTag nbtEffects = tag.getList("Effects", Tag.TAG_STRING);
		ListTag nbtPoses = tag.getList("Positions", Tag.TAG_COMPOUND);

		for(int i = 0; i < nbtEffects.size(); i++) {
			String nbtId = nbtEffects.getString(i);

			if(ArcanusSpellComponents.REGISTRY.get(ResourceLocation.parse(nbtId)) instanceof SpellEffect effect)
				effects.add(effect);
		}

		for(int i = 0; i < nbtPoses.size(); i++) {
			CompoundTag nbt = nbtPoses.getCompound(i);
			positions.add(new Vector2i(nbt.getInt("X"), nbt.getInt("Y")));
		}

		if(positions.size() != effects.size() + 1)
			return new SpellGroup(SpellShape.empty(), List.of(), List.of());

		return new SpellGroup(shape, effects, positions);
	}

	public CompoundTag toNbt() {
		CompoundTag tag = new CompoundTag();
		ListTag effectsList = new ListTag();
		ListTag posesList = new ListTag();

		for(SpellEffect effect : effects)
			effectsList.add(StringTag.valueOf(ArcanusSpellComponents.REGISTRY.getKey(effect).toString()));

		for(Vector2i position : positions) {
			CompoundTag nbt = new CompoundTag();
			nbt.putInt("X", position.x());
			nbt.putInt("Y", position.y());
			posesList.add(nbt);
		}

		tag.putString("Shape", ArcanusSpellComponents.REGISTRY.getKey(shape).toString());
		tag.put("Effects", effectsList);
		tag.put("Positions", posesList);

		return tag;
	}

	public Weight getWeight() {
		return shape.getWeight();
	}

	public Map<PrimalArcana, Double> getArcanaCost() {
		Map<PrimalArcana, Double> cumulativeArcanaCost = new HashMap<>(shape().getArcanaCost());

		for(SpellEffect effect : effects)
			for(PrimalArcana primalArcana : effect.getArcanaCost().keySet())
				cumulativeArcanaCost.put(primalArcana, effect.getArcanaCost().get(primalArcana) + cumulativeArcanaCost.get(primalArcana));

		return cumulativeArcanaCost;
	}

	public Stream<SpellComponent> getAllComponents() {
		return Stream.concat(Stream.of(shape), effects.stream());
	}

	public boolean isEmpty() {
		return ArcanusSpellComponents.EMPTY.is(shape);
	}
}
