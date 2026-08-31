package dev.cammiescorner.arcanus.component.entity;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.registry.ArcanusSpellComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.ArrayList;
import java.util.List;

public class KnownSpellComponentsComponent implements AutoSyncedComponent {
	private static final Codec<List<SpellComponent>> CODEC = SpellComponent.CODEC.listOf();
	private final List<SpellComponent> knownComponents = new ArrayList<>();
	private final LivingEntity entity;

	public KnownSpellComponentsComponent(LivingEntity entity) {
		this.entity = entity;
		knownComponents.add(ArcanusSpellComponents.EMPTY.get());
	}

	@Override
	public void readData(ValueInput readView) {
		knownComponents.clear();
		knownComponents.addAll(readView.read("KnownSpellComponents", CODEC).orElse(List.of()));
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.store("KnownSpellComponents", CODEC, knownComponents);
	}

	public List<SpellComponent> getKnownComponents() {
		return List.copyOf(knownComponents);
	}

	public void learnSpellComponent(SpellComponent component) {
		if(knownComponents.contains(component))
			return;

		knownComponents.add(component);
		ArcanusComponents.KNOWN_SPELL_COMPONENTS_COMPONENT.sync(entity);
	}

	public void forgetSpellComponent(SpellComponent component) {
		if(!knownComponents.contains(component))
			return;

		knownComponents.remove(component);
		ArcanusComponents.KNOWN_SPELL_COMPONENTS_COMPONENT.sync(entity);
	}
}
