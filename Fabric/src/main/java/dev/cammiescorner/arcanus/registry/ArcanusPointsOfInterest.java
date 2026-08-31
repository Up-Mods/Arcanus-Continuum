package dev.cammiescorner.arcanus.registry;

import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class ArcanusPointsOfInterest {
	public static final ResourceKey<PoiType> MAGIC_DOOR = create("magic_door");
	public static final ResourceKey<PoiType> PEDESTAL = create("pedestal");
	public static final ResourceKey<PoiType> CHALK = create("chalk");
	public static final ResourceKey<PoiType> JAR = create("jar");

	public static void register() {
		PointOfInterestHelper.register(MAGIC_DOOR.identifier(), 0, 1, ArcanusBlocks.MAGIC_DOOR.get());
		PointOfInterestHelper.register(PEDESTAL.identifier(), 0, 1, ArcanusBlocks.PEDESTAL.get());
		PointOfInterestHelper.register(JAR.identifier(), 0, 1, ArcanusBlocks.WARDED_JAR.get());
	}

	public static ResourceKey<PoiType> create(String id) {
		return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, Arcanus.id(id));
	}
}
