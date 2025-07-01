package dev.cammiescorner.arcanus.api;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.components.SpellComponent;
import dev.cammiescorner.arcanus.api.spells.SpellComponentRite;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ArcanusRegistries {
	public static final ResourceKey<Registry<SpellComponent>> SPELL_COMPONENTS = ResourceKey.createRegistryKey(Arcanus.id("spell_components"));
	public static final ResourceKey<Registry<SpellComponentRite>> SPELL_COMPONENT_RITES = ResourceKey.createRegistryKey(Arcanus.id("spell_component_rites"));
}
