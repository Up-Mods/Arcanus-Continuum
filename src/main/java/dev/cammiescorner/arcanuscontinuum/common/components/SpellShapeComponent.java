package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class SpellShapeComponent implements AutoSyncedComponent {
	private final Entity entity;
	private SpellShape shape = SpellShape.EMPTY;

	public SpellShapeComponent(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		shape = (SpellShape) Arcanus.SPELL_COMPONENTS.get(new Identifier(tag.getString("SpellShape")));
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putString("SpellShape", Arcanus.SPELL_COMPONENTS.getId(shape).toString());
	}

	public SpellShape getSpellShape() {
		return shape;
	}

	public void setSpellShape(SpellShape shape) {
		this.shape = shape;
		ArcanusComponents.SPELL_SHAPE.sync(entity);
	}
}
