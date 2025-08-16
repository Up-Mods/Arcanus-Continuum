package dev.cammiescorner.arcanus.api.arcana;

import dev.cammiescorner.arcanus.common.registry.ArcanusCompoundArcana;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.ChatFormatting;

import java.util.function.Supplier;

public record CompoundArcana(Supplier<Arcana> arcana1, Supplier<Arcana> arcana2, ChatFormatting formatting, Color color) implements Arcana {
	@Override
	public String getSerializedName() {
		return ArcanusCompoundArcana.REGISTRY.getKey(this).toString();
	}
}
