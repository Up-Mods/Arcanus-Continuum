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

public class KnownComponentsComponent implements AutoSyncedComponent {
	private final LivingEntity entity;
	private final Set<SpellComponent> knownComponents = new HashSet<>();

	public KnownComponentsComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		NbtList listTag = tag.getList("KnownSpellComponents", NbtType.STRING);

		for(int i = 0; i < listTag.size(); i++)
			Arcanus.SPELL_COMPONENTS.getOrEmpty(new Identifier(listTag.getString(i))).ifPresent(knownComponents::add);
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList listTag = new NbtList();

		knownComponents.forEach(componentId -> listTag.add(NbtString.of(Arcanus.SPELL_COMPONENTS.getId(componentId).toString())));
		tag.put("KnownSpells", listTag);
	}

	public Set<SpellComponent> getKnownComponents() {
		return knownComponents;
	}

	public boolean hasComponent(SpellComponent component) {
		return knownComponents.contains(component);
	}

	public boolean hasAllComponents(SpellComponent... components) {
		return knownComponents.containsAll(List.of(components));
	}

	public void addComponent(SpellComponent component) {
		knownComponents.add(component);
		ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.sync(entity);
	}

	public void addAllComponents(SpellComponent... components) {
		knownComponents.addAll(List.of(components));
		ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.sync(entity);
	}

	public void removeComponent(SpellComponent component) {
		knownComponents.remove(component);
		ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.sync(entity);
	}

	public void removeAllComponents(SpellComponent... components) {
		List.of(components).forEach(knownComponents::remove);
		ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.sync(entity);
	}
}
