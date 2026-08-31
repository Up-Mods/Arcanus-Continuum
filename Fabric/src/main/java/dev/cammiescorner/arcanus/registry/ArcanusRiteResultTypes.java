package dev.cammiescorner.arcanus.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.crafting.ItemRiteResult;
import dev.cammiescorner.arcanus.api.crafting.RiteResult;
import dev.cammiescorner.arcanus.api.crafting.SpellComponentRiteResult;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.Registry;

public class ArcanusRiteResultTypes {

	public static final RegistryHandler<RiteResult.Type<?>> RITE_RESULTS = RegistryHandler.create(ArcanusRegistries.RITE_RESULT_TYPE, Arcanus.MOD_ID);
	public static final Registry<RiteResult.Type<?>> REGISTRY = RITE_RESULTS.createNewRegistry();

	public static final RegistrySupplier<RiteResult.Type<ItemRiteResult>> ITEM = RITE_RESULTS.register("item", () -> new RiteResult.Type<>(ItemRiteResult.CODEC, ItemRiteResult.STREAM_CODEC));
	public static final RegistrySupplier<RiteResult.Type<SpellComponentRiteResult>> SPELL_COMPONENT = RITE_RESULTS.register("spell_component", () -> new RiteResult.Type<>(SpellComponentRiteResult.CODEC, SpellComponentRiteResult.STREAM_CODEC));
}
