package dev.cammiescorner.arcanus.common.components.entity;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.spells.components.SpellComponent;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.LivingEntity;
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
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		knownComponents.clear();
		knownComponents.addAll(CODEC.parse(registryLookup.createSerializationContext(NbtOps.INSTANCE), tag.get("KnownSpellComponents")).getOrThrow());
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		tag.put("KnownSpellComponents", CODEC.encodeStart(registryLookup.createSerializationContext(NbtOps.INSTANCE), knownComponents).getOrThrow());
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
