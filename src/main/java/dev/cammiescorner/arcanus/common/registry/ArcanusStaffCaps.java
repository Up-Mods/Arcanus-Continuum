package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.staff.StaffCap;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.Registry;

public class ArcanusStaffCaps {
	public static final RegistryHandler<StaffCap> STAFF_CAPS = RegistryHandler.create(ArcanusRegistries.STAFF_CAP, Arcanus.MOD_ID);
	public static final Registry<StaffCap> REGISTRY = STAFF_CAPS.createNewRegistry(true, Arcanus.id("iron_cap"));

	public static final RegistrySupplier<StaffCap> IRON_CAP = STAFF_CAPS.register("iron_cap", () -> new StaffCap(-0.1));
}
