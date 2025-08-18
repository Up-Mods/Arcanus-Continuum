package dev.cammiescorner.arcanus.api.arcana;

import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.ChatFormatting;

import java.util.function.Supplier;

public record CompoundArcana(Supplier<? extends Arcana> arcana1, Supplier<? extends Arcana> arcana2, ChatFormatting formatting, Color color) implements Arcana {

}
