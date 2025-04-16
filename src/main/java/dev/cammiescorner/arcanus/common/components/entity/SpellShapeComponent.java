package dev.cammiescorner.arcanus.common.components.entity;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.SpellShape;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.core.HolderLookup;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SpellShapeComponent implements AutoSyncedComponent {
	private final Entity entity;
	private SpellShape shape = SpellShape.empty();

	public SpellShapeComponent(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		shape = (SpellShape) Arcanus.SPELL_COMPONENTS.get(ResourceLocation.parse(tag.getString("SpellShape")));
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		tag.putString("SpellShape", Arcanus.SPELL_COMPONENTS.getKey(shape).toString());
	}

	public SpellShape getSpellShape() {
		return shape;
	}

	public void setSpellShape(SpellShape shape) {
		this.shape = shape;
		ArcanusComponents.SPELL_SHAPE.sync(entity);
	}
}
