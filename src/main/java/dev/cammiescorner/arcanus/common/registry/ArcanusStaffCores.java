package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.staff.StaffCore;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.Registry;

public class ArcanusStaffCores {
	public static final RegistryHandler<StaffCore> STAFF_CORES = RegistryHandler.create(ArcanusRegistries.STAFF_CORE, Arcanus.MOD_ID);
	public static final Registry<StaffCore> REGISTRY = STAFF_CORES.createNewRegistry(true, Arcanus.id("oak_core"));

	public static final RegistrySupplier<StaffCore> OAK_CORE = STAFF_CORES.register("oak_core", StaffCore::new);
}
