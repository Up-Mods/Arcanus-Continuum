package dev.cammiescorner.arcanus.common.block.properties;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ManaTypeProperty extends Property<ManaType> {
	protected ManaTypeProperty(String name) {
		super(name, ManaType.class);
	}

	@Override
	public Collection<ManaType> getPossibleValues() {
		return List.of(ManaType.values());
	}

	@Override
	public String getName(ManaType value) {
		return value.getSerializedName();
	}

	@Override
	public Optional<ManaType> getValue(String value) {
		return Optional.of(ManaType.getByName(value));
	}

	public static ManaTypeProperty create(String name) {
		return new ManaTypeProperty(name);
	}
}
