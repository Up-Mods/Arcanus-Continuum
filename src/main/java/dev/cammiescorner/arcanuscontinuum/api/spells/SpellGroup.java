package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record SpellGroup(SpellShape shape, List<SpellEffect> effects, List<Vector2i> positions) {
	public static SpellGroup fromNbt(NbtCompound tag) {
		SpellShape shape = (SpellShape) Arcanus.SPELL_COMPONENTS.get(new Identifier(tag.getString("Shape")));
		List<SpellEffect> effects = new ArrayList<>();
		List<Vector2i> positions = new ArrayList<>();
		NbtList nbtEffects = tag.getList("Effects", NbtElement.STRING_TYPE);
		NbtList nbtPoses = tag.getList("Positions", NbtElement.COMPOUND_TYPE);

		for(int i = 0; i < nbtEffects.size(); i++)
			effects.add((SpellEffect) Arcanus.SPELL_COMPONENTS.get(new Identifier(nbtEffects.getString(i))));

		for(int i = 0; i < nbtPoses.size(); i++) {
			NbtCompound nbt = nbtPoses.getCompound(i);
			positions.add(new Vector2i(nbt.getInt("X"), nbt.getInt("Y")));
		}

		return new SpellGroup(shape, effects, positions);
	}

	public NbtCompound toNbt() {
		NbtCompound tag = new NbtCompound();
		NbtList effectsList = new NbtList();
		NbtList posesList = new NbtList();

		for(SpellEffect effect : effects)
			effectsList.add(NbtString.of(Arcanus.SPELL_COMPONENTS.getId(effect).toString()));

		for(Vector2i position : positions) {
			NbtCompound nbt = new NbtCompound();
			nbt.putInt("X", position.x());
			nbt.putInt("Y", position.y());
			posesList.add(nbt);
		}

		tag.putString("Shape", Arcanus.SPELL_COMPONENTS.getId(shape).toString());
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

		return Weight.values()[Math.round(cumulativeWeightIndex / ((float) effectCount + 1F))];
	}

	public double getManaCost() {
		double cumulativeManaCost = shape().getManaCost();

		for(SpellEffect effect : effects)
			cumulativeManaCost += effect.getManaCost();

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
