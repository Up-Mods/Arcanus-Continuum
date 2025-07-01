package dev.cammiescorner.arcanus.api.spells.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.spells.Weight;
import dev.cammiescorner.arcanus.api.spells.mana.ManaType;
import dev.cammiescorner.arcanus.api.util.XtraCodecs;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public record SpellGroup(SpellShape shape, List<SpellEffect> effects, List<Vector2i> positions) {
	public static final Codec<SpellGroup> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		SpellShape.CODEC.optionalFieldOf("Shape", SpellShape.empty()).forGetter(SpellGroup::shape),
		Codec.list(SpellEffect.CODEC).optionalFieldOf("Effects", List.of()).forGetter(SpellGroup::effects),
		Codec.list(XtraCodecs.VEC2I_CODEC).optionalFieldOf("Positions", List.of()).forGetter(SpellGroup::positions)
	).apply(instance, SpellGroup::new));

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

	public Weight getAverageWeight() {
		int cumulativeWeightIndex = shape.getWeight().ordinal();
		int effectCount = 0;

		for(int i = 0; i < effects.size(); i++) {
			SpellEffect effect = effects().get(i);

			if(effect.getWeight() != Weight.NONE) {
				cumulativeWeightIndex += effect.getWeight().ordinal();
				effectCount++;
			}
		}

		return Weight.values()[Math.round(cumulativeWeightIndex / ((float) effectCount + 1f))];
	}

	public Map<ManaType, Double> getManaCost() {
		Map<ManaType, Double> cumulativeManaCost = new HashMap<>(shape().getManaCost());

		for(SpellEffect effect : effects)
			for(ManaType manaType : effect.getManaCost().keySet())
				cumulativeManaCost.put(manaType, effect.getManaCost().get(manaType) + cumulativeManaCost.get(manaType));

		return cumulativeManaCost;
	}

	public int getCoolDown() {
		int cumulativeCoolDown = shape().getCoolDown();

		for(SpellEffect effect : effects)
			cumulativeCoolDown += effect.getCoolDown();

		return cumulativeCoolDown;
	}

	public Stream<SpellComponent> getAllComponents() {
		return Stream.concat(Stream.of(shape), effects.stream());
	}

	public boolean isEmpty() {
		return ArcanusSpellComponents.EMPTY.is(shape);
	}
}
