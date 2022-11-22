package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadComponentsComponent implements AutoSyncedComponent {
	private final LivingEntity entity;
	private final Set<SpellComponent> readComponennts = new HashSet<>();

	public ReadComponentsComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		NbtList listTag = tag.getList("ReadSpellComponents", NbtType.STRING);

		for(int i = 0; i < listTag.size(); i++)
			Arcanus.SPELL_COMPONENTS.getOrEmpty(new Identifier(listTag.getString(i))).ifPresent(readComponennts::add);
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList listTag = new NbtList();

		readComponennts.forEach(componentId -> listTag.add(NbtString.of(Arcanus.SPELL_COMPONENTS.getId(componentId).toString())));
		tag.put("ReadSpellComponents", listTag);
	}

	public Set<SpellComponent> getReadComponennts() {
		return readComponennts;
	}

	public boolean hasComponent(SpellComponent component) {
		return readComponennts.contains(component);
	}

	public boolean hasAllComponents(SpellComponent... components) {
		return readComponennts.containsAll(List.of(components));
	}

	public void addComponent(SpellComponent component) {
		readComponennts.add(component);
		ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.sync(entity);
	}

	public void addAllComponents(SpellComponent... components) {
		readComponennts.addAll(List.of(components));
		ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.sync(entity);
	}

	public void removeComponent(SpellComponent component) {
		readComponennts.remove(component);
		ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.sync(entity);
	}

	public void removeAllComponents(SpellComponent... components) {
		List.of(components).forEach(readComponennts::remove);
		ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.sync(entity);
	}
}
