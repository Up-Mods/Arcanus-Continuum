package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;
import org.quiltmc.qsl.poi.api.PointOfInterestHelper;

public class ArcanusPointsOfInterest {
	public static final RegistryKey<PointOfInterestType> MAGIC_DOOR = create("magic_door");

	public static void register() {
		PointOfInterestHelper.register(MAGIC_DOOR.getValue(), 0, 1, ArcanusBlocks.MAGIC_DOOR);
	}

	public static RegistryKey<PointOfInterestType> create(String id) {
		return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Arcanus.id(id));
	}
}
