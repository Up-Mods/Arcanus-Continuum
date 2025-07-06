package dev.cammiescorner.arcanus.api;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ArcanusRegistries {
	public static final ResourceKey<Registry<SpellComponent>> SPELL_COMPONENT = ResourceKey.createRegistryKey(Arcanus.id("spell_component"));
}
