package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;

public class ArcanusPointsOfInterest {
	public static void register() {

	}

	public static RegistryKey<PointOfInterestType> create(String id) {
		return RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE.getKey(), Arcanus.id(id));
	}
}
