package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.common.components.KnownComponentsComponent;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Spell {
	private final List<SpellGroup> groups;
	private final String name;

	public Spell(List<SpellGroup> groups, String name) {
		this.groups = groups;
		this.name = name;
	}

	public Spell() {
		this(List.of(new SpellGroup(SpellShape.EMPTY, List.of(), List.of())), "Blank");
	}

	public static Spell fromNbt(NbtCompound nbt) {
		List<SpellGroup> groups = new ArrayList<>();
		NbtList nbtList = nbt.getList("ComponentGroups", NbtElement.COMPOUND_TYPE);

		for(int i = 0; i < nbtList.size(); i++)
			groups.add(SpellGroup.fromNbt(nbtList.getCompound(i)));

		return new Spell(groups, nbt.getString("Name"));
	}

	public NbtCompound toNbt() {
		NbtCompound nbt = new NbtCompound();
		NbtList nbtList = new NbtList();

		for(SpellGroup group : groups)
			nbtList.add(group.toNbt());

		nbt.put("ComponentGroups", nbtList);
		nbt.putString("Name", name != null ? name : "Empty");

		return nbt;
	}

	public List<SpellGroup> getComponentGroups() {
		return groups;
	}

	public String getName() {
		return name;
	}

	public Weight getWeight() {
		List<SpellGroup> groups = getComponentGroups();
		int averageWeightIndex = 0;

		if(!groups.isEmpty()) {
			for(SpellGroup component : groups)
				averageWeightIndex += component.getAverageWeight().ordinal();

			averageWeightIndex = Math.round(averageWeightIndex / (float) groups.size());
		}

		return Weight.values()[averageWeightIndex];
	}

	public double getManaCost() {
		List<SpellGroup> groups = getComponentGroups();
		double averageManaCost = 0;

		if(!groups.isEmpty()) {
			for(SpellGroup group : groups)
				averageManaCost += group.getAverageManaCost();

			averageManaCost /= groups.size();
		}

		return averageManaCost;
	}

	public int getCoolDown() {
		List<SpellGroup> groups = getComponentGroups();
		int averageCoolDown = 0;

		if(!groups.isEmpty()) {
			for(SpellGroup group : groups)
				averageCoolDown += group.getAverageCoolDown();

			averageCoolDown /= groups.size();
		}

		return averageCoolDown;
	}

	public Stream<SpellComponent> components() {
		return groups.stream().flatMap(SpellGroup::getAllComponents);
	}

	public void cast(LivingEntity caster, ServerWorld world, StaffItem staff, ItemStack stack) {
		List<SpellGroup> groups = getComponentGroups();
		if (groups.isEmpty())
			return;

		// Add all components to the caster's known components
		KnownComponentsComponent knownComponents = ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.get(caster);
		knownComponents.addAllComponents(components().toList());

		// start casting the spell
		SpellGroup firstGroup = groups.get(0);
		firstGroup.shape().cast(caster, caster.getPos(), caster, world, staff, stack, firstGroup.effects(), groups, 0);
	}
}
