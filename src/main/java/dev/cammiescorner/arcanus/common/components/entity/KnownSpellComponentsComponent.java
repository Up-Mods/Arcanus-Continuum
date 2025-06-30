package dev.cammiescorner.arcanus.common.components.entity;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.spells.SpellComponent;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.ArrayList;
import java.util.List;

public class KnownSpellComponentsComponent implements AutoSyncedComponent {
	private static final Codec<List<SpellComponent>> CODEC = SpellComponent.CODEC.listOf();
	private final LivingEntity entity;
	private final List<SpellComponent> knownComponents = new ArrayList<>();

	public KnownSpellComponentsComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		knownComponents.clear();
		knownComponents.addAll(CODEC.parse(NbtOps.INSTANCE, tag.get("KnownSpellComponents")).getOrThrow());
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		tag.put("KnownSpellComponents", CODEC.encode(knownComponents, NbtOps.INSTANCE, tag).getOrThrow());
	}

	public List<SpellComponent> getKnownComponents() {
		return List.copyOf(knownComponents);
	}

	public void learnSpellComponent(SpellComponent component) {
		if(knownComponents.contains(component)) {
			// TODO add error message
			return;
		}

		knownComponents.add(component);
		ArcanusComponents.KNOWN_SPELL_COMPONENTS_COMPONENT.sync(entity);
	}

	public void forgetSpellComponent(SpellComponent component) {
		if(!knownComponents.contains(component)) {
			// TODO add error message
			return;
		}

		knownComponents.remove(component);
		ArcanusComponents.KNOWN_SPELL_COMPONENTS_COMPONENT.sync(entity);
	}
}
