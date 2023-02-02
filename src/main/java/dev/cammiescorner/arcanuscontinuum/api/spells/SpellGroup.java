package dev.cammiescorner.arcanuscontinuum.api.spells;

import com.google.common.collect.ImmutableList;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;

import java.util.List;
import java.util.stream.Stream;

public record SpellGroup(SpellShape shape, List<SpellEffect> effects, List<Vector2i> positions) {
	public static SpellGroup fromNbt(NbtCompound tag) {
		SpellShape shape = (SpellShape) Arcanus.SPELL_COMPONENTS.get(new Identifier(tag.getString("Shape")));
		ImmutableList.Builder<SpellEffect> effects = ImmutableList.builder();
		ImmutableList.Builder<Vector2i> poses = ImmutableList.builder();
		NbtList nbtEffects = tag.getList("Effects", NbtElement.STRING_TYPE);
		NbtList nbtPoses = tag.getList("Positions", NbtElement.COMPOUND_TYPE);

		for(int i = 0; i < nbtEffects.size(); i++)
			effects.add((SpellEffect) Arcanus.SPELL_COMPONENTS.get(new Identifier(nbtEffects.getString(i))));

		for(int i = 0; i < nbtPoses.size(); i++) {
			NbtCompound nbt = nbtPoses.getCompound(i);
			poses.add(new Vector2i(nbt.getInt("X"), nbt.getInt("Y")));
		}

		return new SpellGroup(shape, effects.build(), poses.build());
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

		for(SpellEffect effect : effects)
			cumulativeWeightIndex += effect.getWeight().ordinal();

		return Weight.values()[Math.round(cumulativeWeightIndex / ((float) effects.size() + 1F))];
	}

	public double getAverageManaCost() {
		double cumulativeManaCost = shape().getManaCost();

		for(SpellEffect effect : effects)
			cumulativeManaCost += effect.getManaCost();

		return cumulativeManaCost / (effects.size() + 1D);
	}

	public int getAverageCoolDown() {
		int cumulativeCoolDown = shape().getCoolDown();

		for(SpellEffect effect : effects)
			cumulativeCoolDown += effect.getCoolDown();

		return Math.round(cumulativeCoolDown / ((float) effects.size() + 1F));
	}

	public Stream<SpellComponent> getAllComponents() {
		return Stream.concat(Stream.of(shape), effects.stream());
	}
}
