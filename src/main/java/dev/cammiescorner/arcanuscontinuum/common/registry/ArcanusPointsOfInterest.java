package dev.cammiescorner.arcanuscontinuum.common.registry;

import com.google.common.collect.ImmutableSet;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Set;

public class ArcanusPointsOfInterest {
	public static final RegistryKey<PointOfInterestType> MAGIC_DOOR = create("magic_door");

	public static void register() {
		PointOfInterestType pointOfInterestType = new PointOfInterestType(states(ArcanusBlocks.MAGIC_DOOR), 1, 1);

		Registry.register(Registries.POINT_OF_INTEREST_TYPE, MAGIC_DOOR, pointOfInterestType);
	}

	public static RegistryKey<PointOfInterestType> create(String id) {
		return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Arcanus.id(id));
	}

	public static Set<BlockState> states(Block block) {
		return ImmutableSet.copyOf(block.getStateManager().getStates());
	}
}
