package dev.cammiescorner.arcanus.common.block.properties;

import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ManaTypeProperty extends Property<ArcanaType> {
	protected ManaTypeProperty(String name) {
		super(name, ArcanaType.class);
	}

	@Override
	public Collection<ArcanaType> getPossibleValues() {
		return List.of(ArcanaType.values());
	}

	@Override
	public String getName(ArcanaType value) {
		return value.getSerializedName();
	}

	@Override
	public Optional<ArcanaType> getValue(String value) {
		return Optional.of(ArcanaType.getByName(value));
	}

	public static ManaTypeProperty create(String name) {
		return new ManaTypeProperty(name);
	}
}
