package dev.cammiescorner.arcanus.client.gui.util;

import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import org.joml.Vector2i;

public record Action(SpellComponent component, Vector2i position, Runnable Do, Runnable undo) {
}
