package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.Registry;

public class ArcanusCompoundArcana {
	public static final RegistryHandler<Arcana> ARCANA = RegistryHandler.create(ArcanusRegistries.ARCANA, Arcanus.MOD_ID);
	public static final Registry<Arcana> REGISTRY = ARCANA.createNewRegistry(true, Arcanus.id("empty"));

	/**
	 * DO NOT DELETE OR DISABLE. WILL BREAK THE ENTIRE MOD.
	 **/
	public static final RegistrySupplier<Arcana> EMPTY = ARCANA.register("empty", () -> null);
}
