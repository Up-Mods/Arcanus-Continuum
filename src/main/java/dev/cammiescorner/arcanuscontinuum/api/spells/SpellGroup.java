package dev.cammiescorner.arcanuscontinuum.api.spells;

import com.google.common.collect.ImmutableList;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Stream;

public record SpellGroup(SpellShape shape, List<SpellEffect> effects) {
	public static SpellGroup fromNbt(NbtCompound tag) {
		SpellShape shape = (SpellShape) Arcanus.SPELL_COMPONENTS.get(new Identifier(tag.getString("Shape")));
		ImmutableList.Builder<SpellEffect> effects = ImmutableList.builder();
		NbtList nbtList = tag.getList("Effects", NbtElement.STRING_TYPE);

		for(int i = 0; i < nbtList.size(); i++)
			effects.add((SpellEffect) Arcanus.SPELL_COMPONENTS.get(new Identifier(nbtList.getString(i))));

		return new SpellGroup(shape, effects.build());
	}

	public NbtCompound toNbt() {
		NbtCompound tag = new NbtCompound();
		NbtList nbtList = new NbtList();

		for(SpellEffect effect : effects)
			nbtList.add(NbtString.of(Arcanus.SPELL_COMPONENTS.getId(effect).toString()));

		tag.putString("Shape", Arcanus.SPELL_COMPONENTS.getId(shape).toString());
		tag.put("Effects", nbtList);

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
