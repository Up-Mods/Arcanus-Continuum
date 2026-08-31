package dev.cammiescorner.arcanus.data;

import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ArcanusStructures {
	public static final ResourceKey<Structure> WIZARD_TOWER = ResourceKey.create(Registries.STRUCTURE, Arcanus.id("wizard_tower"));
}
