package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Spell {
	private final NbtCompound nbt;

	public Spell(NbtCompound nbt) {
		this.nbt = nbt;
	}

	public Spell() {
		this(new NbtCompound());
	}

	public NbtCompound getNbtCompound() {
		return nbt;
	}

	public List<SpellComponent> getComponents() {
		List<SpellComponent> components = new ArrayList<>();
		NbtList nbtList = nbt.getList("Components", NbtElement.STRING_TYPE);

		if(nbtList.isEmpty())
			nbtList.add(NbtString.of("arcanus:empty"));

		for(int i = 0; i < nbtList.size(); i++)
			components.add(Arcanus.SPELL_COMPONENTS.get(new Identifier(nbtList.getString(i))));

		return components;
	}

	public String getName() {
		if(!nbt.contains("Name"))
			nbt.putString("Name", "None");

		return nbt.getString("Name");
	}

	public Weight getWeight() {
		List<SpellComponent> components = getComponents();
		int averageWeightIndex = 0;

		if(!components.isEmpty()) {
			for(SpellComponent component : components)
				averageWeightIndex += component.getWeight().ordinal();

			averageWeightIndex = Math.round(averageWeightIndex / (float) components.size());
		}

		return Weight.values()[averageWeightIndex];
	}

	public double getManaCost() {
		List<SpellComponent> components = getComponents();
		double averageManaCost = 0;

		if(!components.isEmpty())
			for(SpellComponent component : components)
				averageManaCost += component.getManaCost();

		return averageManaCost;
	}

	public int getCoolDown() {
		List<SpellComponent> components = getComponents();
		int averageCoolDown = 0;

		if(!components.isEmpty())
			for(SpellComponent component : components)
				averageCoolDown += component.getCoolDown();

		return averageCoolDown;
	}

	public void cast(LivingEntity caster, World world, StaffItem staff) {
		List<SpellComponent> nextComponents = getComponents();

		if(getComponents().get(0) instanceof SpellShape form) {
			List<SpellEffect> effects = new ArrayList<>();

			for(int i = 1; i < nextComponents.size(); i++) {
				SpellComponent nextComponent = getComponents().get(i);

				if(nextComponent instanceof SpellEffect effectComponent)
					effects.add(effectComponent);
				else
					break;
			}

			form.cast(caster, caster.getEyePos(), world, staff, effects, nextComponents);
		}
	}
}
