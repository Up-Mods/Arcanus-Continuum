package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public class ArcanusDataComponents {
	public static final RegistryHandler<DataComponentType<?>> DATA_COMPONENTS = RegistryHandler.create(Registries.DATA_COMPONENT_TYPE, Arcanus.MOD_ID);

	// TODO add data component for attached spell books
}
