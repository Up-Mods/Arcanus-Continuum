package dev.cammiescorner.arcanus.common.block.properties;

import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ManaTypeProperty extends Property<PrimalArcana> {
	protected ManaTypeProperty(String name) {
		super(name, PrimalArcana.class);
	}

	@Override
	public Collection<PrimalArcana> getPossibleValues() {
		return List.of(PrimalArcana.values());
	}

	@Override
	public String getName(PrimalArcana value) {
		return value.getSerializedName();
	}

	@Override
	public Optional<PrimalArcana> getValue(String value) {
		return Optional.of(PrimalArcana.getByName(value));
	}

	public static ManaTypeProperty create(String name) {
		return new ManaTypeProperty(name);
	}
}
